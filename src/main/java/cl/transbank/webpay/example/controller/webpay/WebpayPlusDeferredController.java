package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;

import cl.transbank.webpay.webpayplus.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class WebpayPlusDeferredController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusDeferredController.class);
    private WebpayPlus.Transaction tx;
    public WebpayPlusDeferredController(){
        tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/webpayplusdeferred", method = RequestMethod.GET)
    public ModelAndView webpayplus(HttpServletRequest request) {
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = request.getRequestURL().append("-end").toString();

        Map<String, Object> details = new HashMap<>();

        Map<String, Object> req = new HashMap<>();
        req.put("buyOrder", buyOrder);
        req.put("sessionId", sessionId);
        req.put("amount", amount);
        req.put("returnUrl", returnUrl);

        details.put("req", toJson(req));

        try {
            final WebpayPlusTransactionCreateResponse response = tx.create(buyOrder, sessionId, amount, returnUrl);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());

            details.put("resp", toJson(response));
        }
        catch (TransactionCreateException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus", "details", details);
    }

    @RequestMapping(value = "webpayplusdeferred-end", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusTransactionCommitResponse response = tx.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-end", "-refund"));

            details.put("resp", toJson(response));
        }
        catch (TransactionCommitException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView( "webpayplusdeferred-commit", "details", details);
    }

    @RequestMapping(value = "webpayplusdeferred-refund", method = RequestMethod.POST)
    public ModelAndView webpayplusRefund(@RequestParam("token_ws") String tokenWs,
                                         @RequestParam("amount") double amount,
                                         HttpServletRequest request) {

        log.info(String.format("token_ws : %s | amount : %s", tokenWs, amount));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusTransactionRefundResponse response = tx.refund(tokenWs, amount);
            details.put("response", response);

            details.put("resp", toJson(response));
        }
        catch (TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus-refund", "details", details);
    }

    @RequestMapping(value= "/webpayplusdeferred-status", method = RequestMethod.POST)
    public ModelAndView webpayplusStatus(@RequestParam("token_ws") String token){

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", token);
        try {
            final WebpayPlusTransactionStatusResponse response = tx.status(token);
            details.put("resp", toJson(response));
        }
        catch (TransactionStatusException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("webpayplus-status", "details", details);
    }

    @RequestMapping(value = "/webpayplusdeferred-capture", method = RequestMethod.POST)
    public ModelAndView webpayplusDeferredCapture(@RequestParam("token_ws") String tokenWs,
                                                  @RequestParam("buy_order") String buyOrder,
                                                  @RequestParam("authorization_code") String authorizationCode,
                                                  @RequestParam("capture_amount") double amount,
                                                  HttpServletRequest request) {
        Map<String, Object> details = new HashMap<>();
        Map<String, Object> req = new HashMap<>();
        req.put("buy_order", buyOrder);
        req.put("token_ws", tokenWs);
        req.put("authorization_code", authorizationCode);
        req.put("capture_amount", amount);

        details.put("req", toJson(req));
        try {
            final WebpayPlusTransactionCaptureResponse response = tx.capture(tokenWs, buyOrder, authorizationCode, amount);

            details.put("resp", toJson(response));
        }
        catch (TransactionCaptureException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("webpayplusdeferred-end", "details", details);
    }
}
