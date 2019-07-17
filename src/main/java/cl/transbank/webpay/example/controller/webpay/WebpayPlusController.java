package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.CaptureTransactionException;
import cl.transbank.webpay.exception.CommitTransactionException;
import cl.transbank.webpay.exception.CreateTransactionException;
import cl.transbank.webpay.exception.RefundTransactionException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.CaptureWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.RefundWebpayPlusTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

@Controller
public class WebpayPlusController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/webpayplus", method = RequestMethod.GET)
    public ModelAndView webpayplus(HttpServletRequest request) {
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = request.getRequestURL().append("-end").toString();

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("sessionId", sessionId);
        details.put("amount", amount);
        details.put("returnUrl", returnUrl);

        try {
            final CreateWebpayPlusTransactionResponse response = WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (CreateTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus", "details", details);
    }

    @RequestMapping(value = {"/webpayplus-end"}, method = RequestMethod.POST)
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final CommitWebpayPlusTransactionResponse response = WebpayPlus.Transaction.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-end", "-refund"));
        } catch (CommitTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus-end", "details", details);
    }

    @RequestMapping(value = "/webpayplus-refund", method = RequestMethod.POST)
    public ModelAndView webpayplusRefund(@RequestParam("token_ws") String tokenWs,
                                         @RequestParam("amount") double amount,
                                         HttpServletRequest request) {
        prepareLoger(Level.ALL);

        log.info(String.format("token_ws : %s | amount : %s", tokenWs, amount));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final RefundWebpayPlusTransactionResponse response = WebpayPlus.Transaction.refund(tokenWs, amount);
            log.info(response.toString());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (RefundTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus-refund", "details", details);
    }

    @RequestMapping(value = {"/webpayplus-refund-form", "/webpayplusdeferred-refund-form"}, method = RequestMethod.GET)
    public ModelAndView webpayplusRefundForm(HttpServletRequest request) {
        String refundEndpoint = request.getRequestURL().toString().endsWith("webpayplusdeferred-refund-form") ?
                "/webpayplusdeferred-refund" : "/webpayplus-refund";

        Map<String, Object> details = new HashMap<>();
        details.put("refund-endpoint", refundEndpoint);
        return new ModelAndView("webpayplus-refund-form", "details", details);
    }

    @RequestMapping(value = "/webpayplusdeferred-capture", method = RequestMethod.POST)
    public ModelAndView webpayplusDeferredCapture(@RequestParam("token_ws") String tokenWs,
                                                  @RequestParam("buy_order") String buyOrder,
                                                  @RequestParam("authorization_code") String authorizationCode,
                                                  @RequestParam("capture_amount") double amount,
                                                  HttpServletRequest request) {
        try {
            final CaptureWebpayPlusTransactionResponse response = WebpayPlus.DeferredTransaction.capture(tokenWs, buyOrder, authorizationCode, amount);
            log.info(response.toString());

            Map<String, Object> details = new HashMap<>();
            details.put("response", response);
            details.put("token_ws", tokenWs);
            details.put("buy_order", buyOrder);
            details.put("authorization_code", authorizationCode);
            details.put("capture_amount", String.valueOf(amount));
            return new ModelAndView("webpayplusdeferred-end", "details", details);
        } catch (CaptureTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
    }
}
