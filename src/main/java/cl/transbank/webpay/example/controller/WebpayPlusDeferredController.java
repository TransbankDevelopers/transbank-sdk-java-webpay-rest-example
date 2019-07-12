package cl.transbank.webpay.example.controller;

import cl.transbank.webpay.Options;
import cl.transbank.webpay.exception.CommitTransactionException;
import cl.transbank.webpay.exception.CreateTransactionException;
import cl.transbank.webpay.exception.RefundTransactionException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

@Controller
public class WebpayPlusDeferredController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusDeferredController.class);

    @RequestMapping(value = "/webpayplusdeferred", method = RequestMethod.GET)
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
            final CreateWebpayPlusTransactionResponse response = WebpayPlus.DeferredTransaction.create(buyOrder, sessionId, amount, returnUrl);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (CreateTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus", "details", details);
    }

    @RequestMapping(value = "webpayplusdeferred-end", method = RequestMethod.POST)
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final CommitWebpayPlusTransactionResponse response = WebpayPlus.DeferredTransaction.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-end", "-refund"));
        } catch (CommitTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView( "webpayplusdeferred-commit", "details", details);
    }

    @RequestMapping(value = "webpayplusdeferred-refund", method = RequestMethod.POST)
    public ModelAndView webpayplusRefund(@RequestParam("token_ws") String tokenWs,
                                         @RequestParam("amount") double amount,
                                         HttpServletRequest request) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        log.info(String.format("token_ws : %s | amount : %s", tokenWs, amount));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final RefundWebpayPlusTransactionResponse response = WebpayPlus.DeferredTransaction.refund(tokenWs, amount);
            log.info(response.toString());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (RefundTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplus-refund", "details", details);
    }
}
