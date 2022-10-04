package cl.transbank.webpay.example.controller.transaccioncompleta;

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
import cl.transbank.webpay.transaccioncompleta.MallFullTransaction;
import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
import cl.transbank.webpay.transaccioncompleta.responses.*;
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
@RequestMapping(value = "/transaccion_completa_mall")
public class MallFullTransactionController extends BaseController {
    private MallFullTransaction tx;
    public MallFullTransactionController(){
        tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public ModelAndView form(
            HttpServletRequest request
    ) {
        double amount = 10000;
        Map<String, Object> details = new HashMap<>();
        details.put("create-endpoint", "/transaccion_completa_mall/create");
        details.put("amount", amount);
        return new ModelAndView("transaccion_completa_mall/form", "details", details);
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
        String childCommerceCode = IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_CHILD1;
        Map<String, Object> details = new HashMap<>();
        try {
            MallFullTransactionCreateResponse response = tx.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(amount, childCommerceCode, childBuyOrder), (short) cvv);
            details.put("resp", toJson(response));
            details.put("token", response.getToken());
            details.put("child_buy_order", childBuyOrder);
            details.put("child_commerce_code", childCommerceCode);
            details.put("commit-endpoint", "/transaccion_completa_mall/commit");
            details.put("installments-endpoint", "/transaccion_completa_mall/installments");
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall/create", "details", details);
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
            addDetailModel(response, token, response.getBuyOrder(), detail.getCommerceCode(), detail.getBuyOrder(), detail.getAuthorizationCode(), detail.getAmount(), details);
            details.put("refund-endpoint", "/transaccion_completa_mall/refund");
            details.put("status-endpoint", "/transaccion_completa_mall/status");
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall/commit", "details", details);
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
            details.put("commit-endpoint", "/transaccion_completa_mall/commit");
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }

        return new ModelAndView("transaccion_completa_mall/installments", "details", details);
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

        return new ModelAndView("transaccion_completa_mall/status", "details", details);
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
        return new ModelAndView("transaccion_completa_mall/refund", "details", details);
    }
    private void addDetailModel(Object response, String tokenWs, String buyOrder, String childCommerceCode, String childBuyOrder, String authorizationCode, double amount, Map<String, Object> details){
        details.put("response", response);
        details.put("buy_order", buyOrder);
        details.put("token_ws", tokenWs);
        details.put("child_buy_order", childBuyOrder);
        details.put("child_commerce_code", childCommerceCode);
        details.put("authorization_code", authorizationCode);
        details.put("amount", amount);
        details.put("resp", toJson(response));
    }

}

