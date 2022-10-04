package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.responses.DeferredCaptureHistoryResponse;
import cl.transbank.webpay.responses.IncreaseAmountResponse;
import cl.transbank.webpay.responses.IncreaseAuthorizationDateResponse;
import cl.transbank.webpay.responses.ReversePreAuthorizedAmountResponse;
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

@Log4j2
@Controller
public class WebpayPlusMallDeferredController extends BaseController {
    private WebpayPlus.MallTransaction tx;
    public WebpayPlusMallDeferredController(){
        tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {

        log.info("Webpay Plus Mall MallTransaction.create");
        String buyOrder = "buyOrder_" + getRandomNumber();
        String buyOrderMallOne = "childBuyOrder_" + getRandomNumber();
        String buyOrderMallTwo = "childBuyOrder_" + getRandomNumber();
        String sessionId = "sessionId_" + getRandomNumber();
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().toString().replace("create","commit");

        String mallOneCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED_CHILD1;
        String mallTwoCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED_CHILD2;
        final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        Map<String, Object> details = new HashMap<>();

        try {
            final WebpayPlusMallTransactionCreateResponse response = tx.create(
                    buyOrder, sessionId, returnUrl, mallDetails);

            details.put("url", response.getUrl());
            details.put("token", response.getToken());
            details.put("resp", toJson(response));

            details.put("childCommerceCode1", mallOneCommerceCode);
            details.put("childBuyOrder1", buyOrderMallOne);
            details.put("amount1", amountMallOne);

            details.put("childCommerceCode2", mallTwoCommerceCode);
            details.put("childBuyOrder2", buyOrderMallTwo);
            details.put("amount2", amountMallTwo);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("webpay_plus_mall_deferred/create", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/commit", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusMallTransactionCommitResponse response = tx.commit(tokenWs);
            WebpayPlusMallTransactionStatusResponse.Detail detail = response.getDetails().get(0);
            addDetailModelDeferred(response, detail.getCommerceCode(), tokenWs, detail.getBuyOrder(), detail.getAuthorizationCode(), detail.getAmount(), details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/commit", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token_ws") String token,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam("amount") double amount) {

        Map<String, Object> details = new HashMap<>();
        try {
            final WebpayPlusMallTransactionRefundResponse response = tx.refund(token, childBuyOrder, childCommerceCode, amount);
            details.put("response", response);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("webpay_plus_mall_deferred/refund", "details", details);
    }
    @RequestMapping(value = "/webpay_plus_mall_deferred/status", method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token_ws") String token){
        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", token);

        try {
            final WebpayPlusMallTransactionStatusResponse response = tx.status(token);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/status", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/capture", method = RequestMethod.POST)
    public ModelAndView capture(@RequestParam("token_ws") String tokenWs,
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            final WebpayPlusMallTransactionCaptureResponse response = tx.capture(childCommerceCode, tokenWs, childBuyOrder, authorizationCode, amount);
            details.put("response", response);
            details.put("token_ws", tokenWs);
            details.put("child_commerce_code", childCommerceCode);
            details.put("child_buy_order", childBuyOrder);
            details.put("authorization_code", authorizationCode);
            details.put("amount", amount);
            details.put("refund-endpoint", "/webpay_plus_mall_deferred/refund");
            details.put("status-endpoint", "/webpay_plus_mall_deferred/status");
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/capture", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/increase_amount", method = RequestMethod.POST)
    public ModelAndView increaseAmount(@RequestParam("token_ws") String tokenWs,
                                       @RequestParam("child_commerce_code") String childCommerceCode,
                                       @RequestParam("child_buy_order") String childBuyOrder,
                                       @RequestParam("authorization_code") String authorizationCode,
                                       @RequestParam("amount") double amount,
                                       HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAmountResponse response = tx.increaseAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, childCommerceCode, tokenWs, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/increase-amount", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/reverse", method = RequestMethod.POST)
    public ModelAndView reverseAmount(@RequestParam("token_ws") String tokenWs,
                                      @RequestParam("child_commerce_code") String childCommerceCode,
                                      @RequestParam("child_buy_order") String childBuyOrder,
                                      @RequestParam("authorization_code") String authorizationCode,
                                      @RequestParam("amount") double amount,
                                      HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            ReversePreAuthorizedAmountResponse response = tx.reversePreAuthorizedAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, childCommerceCode, tokenWs, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_deferred/reverse-amount", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/increase_date", method = RequestMethod.POST)
    public ModelAndView increaseDate(@RequestParam("token_ws") String tokenWs,
                                     @RequestParam("child_commerce_code") String childCommerceCode,
                                     @RequestParam("child_buy_order") String childBuyOrder,
                                     @RequestParam("authorization_code") String authorizationCode,
                                     @RequestParam("amount") double amount,
                                     HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAuthorizationDateResponse response = tx.increaseAuthorizationDate(tokenWs, childCommerceCode, childBuyOrder, authorizationCode);
            addDetailModelDeferred(response, childCommerceCode, tokenWs, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/increase-date", "details", details);
    }

    @RequestMapping(value = "/webpay_plus_mall_deferred/history", method = RequestMethod.POST)
    public ModelAndView history(@RequestParam("token_ws") String tokenWs,
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            List<DeferredCaptureHistoryResponse> response = tx.deferredCaptureHistory(tokenWs, childCommerceCode, childBuyOrder);
            addDetailModelDeferred(response, childCommerceCode, tokenWs, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("webpay_plus_mall_deferred/history", "details", details);
    }

    private void addDetailModelDeferred(Object response, String childCommerceCode, String tokenWs, String buyOrder, String authorizationCode, double amount, Map<String, Object> details){
        details.put("response", response);
        details.put("child_commerce_code", childCommerceCode);
        details.put("child_buy_order", buyOrder);
        details.put("token_ws", tokenWs);
        details.put("authorization_code", authorizationCode);
        details.put("amount", amount);
        details.put("capture-endpoint", "/webpay_plus_mall_deferred/capture");
        details.put("increase-endpoint", "/webpay_plus_mall_deferred/increase_amount");
        details.put("increase-date-endpoint", "/webpay_plus_mall_deferred/increase_date");
        details.put("reverse-endpoint", "/webpay_plus_mall_deferred/reverse");
        details.put("history-endpoint", "/webpay_plus_mall_deferred/history");
        details.put("resp", toJson(response));
    }




}
