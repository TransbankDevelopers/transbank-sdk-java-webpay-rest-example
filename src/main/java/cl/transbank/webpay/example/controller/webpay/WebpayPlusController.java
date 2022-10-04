package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Log4j2
@Controller
public class WebpayPlusController extends BaseController {
    private WebpayPlus.Transaction tx;

    public WebpayPlusController(){
        tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/webpay_plus/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {

        String buyOrder = "buyOrder_" + getRandomNumber();
        String sessionId = "sessionId_" + getRandomNumber();
        double amount = 1000;
        String returnUrl = request.getRequestURL().toString().replace("create","commit");

        Map<String, Object> details = new HashMap<>();

        details.put("buyOrder", buyOrder);
        details.put("sessionId", sessionId);
        details.put("amount", amount);
        details.put("returnUrl", returnUrl);

        try {
            WebpayPlusTransactionCreateResponse response = tx.create(buyOrder, sessionId, amount, returnUrl);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());

            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("webpay_plus/create", "details", details);
    }

    @RequestMapping(value = {"/webpay_plus/commit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusTransactionCommitResponse response = tx.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            details.put("amount", response.getAmount());
            details.put("response", response);
            details.put("refund-endpoint", "/webpay_plus/refund");
            details.put("status-endpoint", "/webpay_plus/status");

            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus/commit", "details", details);
    }

    @RequestMapping(value = "/webpay_plus/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token_ws") String tokenWs,
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
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("webpay_plus/refund", "details", details);
    }
    @RequestMapping(value= "/webpay_plus/status", method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token_ws") String token){

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", token);
        try {
            final WebpayPlusTransactionStatusResponse response = tx.status(token);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus/status", "details", details);
    }


}
