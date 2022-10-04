package cl.transbank.webpay.example.controller.oneclick;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.responses.*;
import cl.transbank.webpay.oneclick.responses.*;
import lombok.extern.log4j.Log4j2;
import cl.transbank.webpay.example.controller.BaseController;
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
@RequestMapping("/oneclick_mall_deferred")
public class OneclickMallDeferredController extends BaseController {

    private Oneclick.MallInscription inscription;
    private Oneclick.MallTransaction tx;
    private String username;
    public OneclickMallDeferredController(){
        tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
        inscription = new Oneclick.MallInscription(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {

        username = "User-" + getRandomNumber();
        String email = "user." + getRandomNumber() + "@example.cl";
        String returnUrl = request.getRequestURL().toString().replace("start","finish");
        Map<String, Object> details = new HashMap<>();

        try {
            final OneclickMallInscriptionStartResponse response = inscription.start(username, email, returnUrl);
            details.put("token", response.getToken());
            details.put("url", response.getUrlWebpay());
            details.put("resp", toJson(response));
            details.put("username", username);
            details.put("email", email);
            details.put("returnUrl", returnUrl);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/start", "details", details);
    }

    @RequestMapping(value = "/finish", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView finish(@RequestParam("TBK_TOKEN") String token) {
        Map<String, Object> details = new HashMap<>();
        try {
            final OneclickMallInscriptionFinishResponse response = inscription.finish(token);
            details.put("token", token);
            details.put("response", response);
            details.put("tbk_user", response.getTbkUser());
            details.put("username", username);
            details.put("amount1", 1000);
            details.put("installments1", 0);
            details.put("amount2", 1000);
            details.put("installments2", 0);
            details.put("authorize-endpoint", "/oneclick_mall_deferred/authorize");
            details.put("delete-endpoint", "/oneclick_mall_deferred/delete");

            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("oneclick_mall_deferred/finish", "details", details);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(
            @RequestParam("username") String username,
            @RequestParam("tbk_user") String tbkUser
    ){
        Map<String, Object> details = new HashMap<>();
        try {
            inscription.delete(tbkUser, username);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("oneclick_mall_deferred/delete", "details", details);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(@RequestParam("username") String username,
                                  @RequestParam("tbk_user") String tbkUser,
                                  @RequestParam("amount1") double childAmount1,
                                  @RequestParam("installments1") int installments1,
                                  @RequestParam("amount2") double childAmount2,
                                  @RequestParam("installments2") int installments2
    ) {
        Map<String, Object> details = new HashMap<>();

        String buyOrder = "buyOrder_" + getRandomNumber();
        String childBuyOrder1 = "childBuyOrder1_" + getRandomNumber();
        String childBuyOrder2 = "childBuyOrder2_" + getRandomNumber();
        String childCommerceCode1 = IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED_CHILD1;
        String childCommerceCode2 = IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED_CHILD2;

        MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(childAmount1, childCommerceCode1, childBuyOrder1, (byte) installments1)
                .add(childAmount2, childCommerceCode2, childBuyOrder2, (byte) installments2);

        try {

            final OneclickMallTransactionAuthorizeResponse response = tx.authorize(username, tbkUser, buyOrder, mallDetails);
            OneclickMallTransactionAuthorizeResponse.Detail detail = response.getDetails().get(0);
            addDetailModelDeferred(response, detail.getCommerceCode(), response.getBuyOrder(), detail.getBuyOrder(), detail.getAuthorizationCode(), detail.getAmount(), details);
            details.put("refund-endpoint", "/oneclick_mall_deferred/refund");
            details.put("status-endpoint", "/oneclick_mall_deferred/status");
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("oneclick_mall_deferred/authorize", "details", details);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("buy_order") String buyOrder,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam("amount") double amount) {

        Map<String, Object> details = new HashMap<>();

        try {
            final OneclickMallTransactionRefundResponse response = tx.refund(buyOrder, childCommerceCode, childBuyOrder, amount);
            details.put("response", response);
            details.put("buyOrder", buyOrder);
            details.put("childCommerceCode", childCommerceCode);
            details.put("childBuyOrder", childBuyOrder);
            details.put("amount", amount);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("oneclick_mall_deferred/refund", "details", details);
    }
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("buy_order") String buyOrder){
        Map<String, Object> details = new HashMap<>();
        try {
            final OneclickMallTransactionStatusResponse response = tx.status(buyOrder);
            details.put("response", response);
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/status", "details", details);
    }

    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    public ModelAndView capture(
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("buy_order") String buyOrder,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            final OneclickMallTransactionCaptureResponse response = tx.capture(childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, childCommerceCode, buyOrder, childBuyOrder, authorizationCode, amount, details);
            details.put("refund-endpoint", "/oneclick_mall_deferred/refund");
            details.put("status-endpoint", "/oneclick_mall_deferred/status");
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/capture", "details", details);
    }

    @RequestMapping(value = "/increase_amount", method = RequestMethod.POST)
    public ModelAndView increaseAmount(
                                       @RequestParam("child_commerce_code") String childCommerceCode,
                                       @RequestParam("buy_order") String buyOrder,
                                       @RequestParam("child_buy_order") String childBuyOrder,
                                       @RequestParam("authorization_code") String authorizationCode,
                                       @RequestParam("amount") double amount,
                                       HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAmountResponse response = tx.increaseAmount(childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, childCommerceCode, buyOrder, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/increase-amount", "details", details);
    }

    @RequestMapping(value = "/reverse", method = RequestMethod.POST)
    public ModelAndView reverseAmount(
                                      @RequestParam("child_commerce_code") String childCommerceCode,
                                      @RequestParam("buy_order") String buyOrder,
                                      @RequestParam("child_buy_order") String childBuyOrder,
                                      @RequestParam("authorization_code") String authorizationCode,
                                      @RequestParam("amount") double amount,
                                      HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            ReversePreAuthorizedAmountResponse response = tx.reversePreAuthorizedAmount(childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, childCommerceCode, buyOrder, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/reverse-amount", "details", details);
    }

    @RequestMapping(value = "/increase_date", method = RequestMethod.POST)
    public ModelAndView increaseDate(
                                     @RequestParam("child_commerce_code") String childCommerceCode,
                                     @RequestParam("buy_order") String buyOrder,
                                     @RequestParam("child_buy_order") String childBuyOrder,
                                     @RequestParam("authorization_code") String authorizationCode,
                                     @RequestParam("amount") double amount,
                                     HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAuthorizationDateResponse response = tx.increaseAuthorizationDate(childCommerceCode, childBuyOrder, authorizationCode);
            addDetailModelDeferred(response, childCommerceCode, buyOrder, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/increase-date", "details", details);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public ModelAndView history(
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("buy_order") String buyOrder,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            List<DeferredCaptureHistoryResponse> response = tx.deferredCaptureHistory(childCommerceCode, childBuyOrder, authorizationCode);
            addDetailModelDeferred(response, childCommerceCode, buyOrder, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("oneclick_mall_deferred/history", "details", details);
    }

    private void addDetailModelDeferred(Object response, String childCommerceCode, String buyOrder, String childBuyOrder, String authorizationCode, double amount, Map<String, Object> details){
        details.put("response", response);
        details.put("child_commerce_code", childCommerceCode);
        details.put("buy_order", buyOrder);
        details.put("child_buy_order", childBuyOrder);
        details.put("authorization_code", authorizationCode);
        details.put("amount", amount);
        details.put("capture-endpoint", "/oneclick_mall_deferred/capture");
        details.put("increase-endpoint", "/oneclick_mall_deferred/increase_amount");
        details.put("increase-date-endpoint", "/oneclick_mall_deferred/increase_date");
        details.put("reverse-endpoint", "/oneclick_mall_deferred/reverse");
        details.put("history-endpoint", "/oneclick_mall_deferred/history");
        details.put("resp", toJson(response));
    }

}
