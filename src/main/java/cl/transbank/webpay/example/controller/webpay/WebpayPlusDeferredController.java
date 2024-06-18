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
import java.util.List;
import java.util.Map;
import java.util.Random;

@Log4j2
@Controller
public class WebpayPlusDeferredController extends BaseController {
    private WebpayPlus.Transaction tx;
    public WebpayPlusDeferredController(){
        tx = new WebpayPlus.Transaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/webpay_plus_deferred/create", method = RequestMethod.GET)
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
            String token = response.getToken();
            request.getSession().setAttribute("TBK_TOKEN", token);
            request.getSession().setAttribute("buyOrder", buyOrder);
            request.getSession().setAttribute("sessionId", sessionId);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("webpay_plus_deferred/create", "details", details);
    }

    @RequestMapping(value = {"/webpay_plus_deferred/commit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView commit(HttpServletRequest request) {
        String tokenWs = request.getParameter("token_ws");
        Map<String, Object> details = new HashMap<>();

        if (tokenWs == null) {
            String token = (String) request.getSession().getAttribute("TBK_TOKEN");
            String buyOrder = (String) request.getSession().getAttribute("buyOrder");
            String sessionId = (String) request.getSession().getAttribute("sessionId");
            details.put("tbkToken", token);
            details.put("buyOrder", buyOrder);
            details.put("sessionId", sessionId);
            return new ModelAndView("webpay_plus_deferred/aborted", "details", details);
        }
        log.info(String.format("token_ws : %s", tokenWs));

        try {
            final WebpayPlusTransactionCommitResponse response = tx.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            addDetailModelDeferred(response, tokenWs, response.getBuyOrder(), response.getAuthorizationCode(), response.getAmount(), details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_deferred/commit", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_deferred/refund", method = RequestMethod.POST)
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

        return new ModelAndView("webpay_plus_deferred/refund", "details", details);
    }
    @RequestMapping(value= "/webpay_plus_deferred/status", method = RequestMethod.POST)
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
        return new ModelAndView("webpay_plus_deferred/status", "details", details);
    }
    @RequestMapping(value = "/webpay_plus_deferred/capture", method = RequestMethod.POST)
    public ModelAndView capture(@RequestParam("token_ws") String tokenWs,
                                                  @RequestParam("buy_order") String buyOrder,
                                                  @RequestParam("authorization_code") String authorizationCode,
                                                  @RequestParam("amount") double amount,
                                                  HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            final WebpayPlusTransactionCaptureResponse response = tx.capture(tokenWs, buyOrder, authorizationCode, amount);
            details.put("response", response);
            details.put("buy_order", buyOrder);
            details.put("token_ws", tokenWs);
            details.put("authorization_code", authorizationCode);
            details.put("amount", amount);
            details.put("refund-endpoint", "/webpay_plus_deferred/refund");
            details.put("status-endpoint", "/webpay_plus_deferred/status");
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_deferred/capture", "details", details);
    }

    private void addDetailModelDeferred(Object response, String tokenWs, String buyOrder, String authorizationCode, double amount, Map<String, Object> details){
        details.put("response", response);
        details.put("buy_order", buyOrder);
        details.put("token_ws", tokenWs);
        details.put("authorization_code", authorizationCode);
        details.put("amount", amount);
        details.put("capture-endpoint", "/webpay_plus_deferred/capture");
        details.put("resp", toJson(response));
    }

}
