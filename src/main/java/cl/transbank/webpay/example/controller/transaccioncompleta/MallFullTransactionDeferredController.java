package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.transaccioncompleta.MallFullTransaction;
import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
import cl.transbank.webpay.responses.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import cl.transbank.webpay.example.controller.BaseController;
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
@RequestMapping(value = "/transaccion_completa_mall_deferred")
public class MallFullTransactionDeferredController extends BaseController {
    private MallFullTransaction tx;
    public MallFullTransactionDeferredController(){
        tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelAndView form(
            HttpServletRequest request
    ) {
        double amount = 10000;
        Map<String, Object> details = new HashMap<>();
        details.put("create-endpoint", "/transaccion_completa_mall_deferred/create");
        details.put("amount", amount);
        return new ModelAndView("transaccion_completa_mall_deferred/form", "details", details);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(
            @RequestParam("card_number") String cardNumber,
            @RequestParam("cvv") int cvv,
            @RequestParam("year") String year,
            @RequestParam("month") String month,
            @RequestParam("amount") double amount,
            HttpServletRequest request
    ) {
        String buyOrder = "buyOrder_" + getRandomNumber();
        String childBuyOrder = "childBuyOrder_" + getRandomNumber();
        String sessionId = "sessionId_" + getRandomNumber();
        String cardExpirationDate= year + "/" + month;
        String childCommerceCode = IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED_CHILD1;
        Map<String, Object> details = new HashMap<>();
        try {
            MallFullTransactionCreateResponse response = tx.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(amount, childCommerceCode, childBuyOrder), (short) cvv);
            details.put("resp", toJson(response));
            details.put("token", response.getToken());
            details.put("child_buy_order", childBuyOrder);
            details.put("child_commerce_code", childCommerceCode);
            details.put("commit-endpoint", "/transaccion_completa_mall_deferred/commit");
            details.put("installments-endpoint", "/transaccion_completa_mall_deferred/installments");
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall_deferred/create", "details", details);
    }

    @RequestMapping(value = {"/commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token") String token, HttpServletRequest request,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam(value = "id_query_installments", required = false) Long idQueryInstallments,
                               @RequestParam(value = "grace_period", required = false) String gracePeriodCheck,
                               @RequestParam(value = "deferred_period_index", required = false) Byte deferredPeriodIndex
    ) {
        Boolean gracePeriod = gracePeriodCheck != null ? true : false;
        Map<String, Object> details = new HashMap<>();
        try {
            MallTransactionCommitDetails detailss = MallTransactionCommitDetails.build().add(childCommerceCode, childBuyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod);
            final MallFullTransactionCommitResponse response = tx.commit(token, detailss);
            MallFullTransactionStatusResponse.Detail detail = response.getDetails().get(0);
            addDetailModelDeferred(response, token, response.getBuyOrder(), detail.getCommerceCode(), detail.getBuyOrder(), detail.getAuthorizationCode(), detail.getAmount(), details);
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall_deferred/commit", "details", details);
    }
    @RequestMapping(value = "/installments", method = RequestMethod.POST)
    public ModelAndView installments(@RequestParam("token") String token,
                                     @RequestParam("child_commerce_code") String childCommerceCode,
                                     @RequestParam("child_buy_order") String childBuyOrder,
                                     @RequestParam("installments") byte installments) {
        Map<String, Object> details = new HashMap<>();
        try {
            MallFullTransactionInstallmentsDetails iDetails = MallFullTransactionInstallmentsDetails.build().add(childCommerceCode, childBuyOrder, installments);
            final MallFullTransactionInstallmentsResponse response = tx.installments(token, iDetails);
            MallFullTransactionInstallmentResponse detail = response.getResponseList().get(0);
            details.put("response", response);
            details.put("token", token);
            details.put("child_buy_order", childBuyOrder);
            details.put("child_commerce_code", childCommerceCode);
            details.put("id_query_installments", detail.getIdQueryInstallments());
            details.put("resp", toJson(response));
            details.put("commit-endpoint", "/transaccion_completa_mall_deferred/commit");
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall_deferred/installments", "details", details);
    }
    @RequestMapping(value = {"/status"}, method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        Map<String, Object> details = new HashMap<>();
        try {
            final MallFullTransactionStatusResponse response = tx.status(tokenWs);
            details.put("response", response);
            details.put("resp", toJson(response));
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall_deferred/status", "details", details);
    }
    @RequestMapping(value = {"/refund"}, method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token_ws") String tokenWs,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam("amount") double amount,
                               HttpServletRequest request
    ) {
        Map<String, Object> details = new HashMap<>();
        try {
            final MallFullTransactionRefundResponse response = tx.refund(tokenWs ,childBuyOrder, childCommerceCode, amount);
            details.put("response", response);
            details.put("resp", toJson(response));
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/refund", "details", details);
    }


    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    public ModelAndView capture(@RequestParam("token_ws") String tokenWs,
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("buy_order") String buyOrder,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            final MallFullTransactionCaptureResponse response = tx.capture(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, tokenWs, buyOrder, childCommerceCode, childBuyOrder, authorizationCode, amount, details);
            details.put("refund-endpoint", "/transaccion_completa_mall_deferred/refund");
            details.put("status-endpoint", "/transaccion_completa_mall_deferred/status");
            details.put("resp", toJson(response));
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/capture", "details", details);
    }

    @RequestMapping(value = "/increase_amount", method = RequestMethod.POST)
    public ModelAndView increaseAmount(@RequestParam("token_ws") String tokenWs,
                                       @RequestParam("child_commerce_code") String childCommerceCode,
                                       @RequestParam("buy_order") String buyOrder,
                                       @RequestParam("child_buy_order") String childBuyOrder,
                                       @RequestParam("authorization_code") String authorizationCode,
                                       @RequestParam("amount") double amount,
                                       HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAmountResponse response = tx.increaseAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, tokenWs, buyOrder, childCommerceCode, childBuyOrder, authorizationCode, response.getTotalAmount(), details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/increase-amount", "details", details);
    }

    @RequestMapping(value = "/reverse", method = RequestMethod.POST)
    public ModelAndView reverseAmount(@RequestParam("token_ws") String tokenWs,
                                      @RequestParam("child_commerce_code") String childCommerceCode,
                                      @RequestParam("buy_order") String buyOrder,
                                      @RequestParam("child_buy_order") String childBuyOrder,
                                      @RequestParam("authorization_code") String authorizationCode,
                                      @RequestParam("amount") double amount,
                                      HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            ReversePreAuthorizedAmountResponse response = tx.reversePreAuthorizedAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);
            addDetailModelDeferred(response, tokenWs, buyOrder, childCommerceCode, childBuyOrder, authorizationCode, response.getTotalAmount(), details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/reverse-amount", "details", details);
    }

    @RequestMapping(value = "/increase_date", method = RequestMethod.POST)
    public ModelAndView increaseDate(@RequestParam("token_ws") String tokenWs,
                                     @RequestParam("child_commerce_code") String childCommerceCode,
                                     @RequestParam("buy_order") String buyOrder,
                                     @RequestParam("child_buy_order") String childBuyOrder,
                                     @RequestParam("authorization_code") String authorizationCode,
                                     @RequestParam("amount") double amount,
                                     HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            IncreaseAuthorizationDateResponse response = tx.increaseAuthorizationDate(tokenWs, childCommerceCode, childBuyOrder, authorizationCode);
            addDetailModelDeferred(response, tokenWs, buyOrder, childCommerceCode, childBuyOrder, authorizationCode, response.getTotalAmount(), details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/increase-date", "details", details);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public ModelAndView history(@RequestParam("token_ws") String tokenWs,
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("buy_order") String buyOrder,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("authorization_code") String authorizationCode,
                                @RequestParam("amount") double amount,
                                HttpServletRequest request) {

        Map<String, Object> details = new HashMap<>();
        try {
            List<DeferredCaptureHistoryResponse> response = tx.deferredCaptureHistory(tokenWs, childCommerceCode, childBuyOrder);
            addDetailModelDeferred(response, tokenWs, buyOrder, childCommerceCode, childBuyOrder, authorizationCode, amount, details);
        }
        catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("transaccion_completa_mall_deferred/history", "details", details);
    }

    private void addDetailModelDeferred(Object response, String tokenWs, String buyOrder, String childCommerceCode, String childBuyOrder, String authorizationCode, double amount, Map<String, Object> details){
        details.put("response", response);
        details.put("buy_order", buyOrder);
        details.put("token_ws", tokenWs);
        details.put("child_buy_order", childBuyOrder);
        details.put("child_commerce_code", childCommerceCode);
        details.put("authorization_code", authorizationCode);
        details.put("amount", amount);
        details.put("capture-endpoint", "/transaccion_completa_mall_deferred/capture");
        details.put("increase-endpoint", "/transaccion_completa_mall_deferred/increase_amount");
        details.put("increase-date-endpoint", "/transaccion_completa_mall_deferred/increase_date");
        details.put("reverse-endpoint", "/transaccion_completa_mall_deferred/reverse");
        details.put("history-endpoint", "/transaccion_completa_mall_deferred/history");
        details.put("resp", toJson(response));
    }

}

