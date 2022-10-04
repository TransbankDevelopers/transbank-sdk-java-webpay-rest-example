package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Log4j2
@Controller
@RequestMapping(value = "/mallfulltransaction")
public class MallFullTransaction2Controller extends BaseController {
    private MallFullTransaction tx;
    public MallFullTransaction2Controller(){
        tx = new MallFullTransaction(new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView fullTransactionCreate(HttpServletRequest request) {

        log.info("Transaccion completa Mall FullTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String cardNumber= "4051885600446623";
        String cardExpirationDate= "23/03";

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("sessionId", sessionId);
        details.put("cardNumber", cardNumber);
        details.put("cardExpirationDate", cardExpirationDate);
        details.put("commerceCode", IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED_CHILD1);

        try {
            MallFullTransactionCreateResponse response = tx.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(1000, IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED_CHILD1, buyOrder), (short) 123);
            details.put("token", response.getToken());
        } catch (TransactionCreateException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-create", "details", details);
    }

    @RequestMapping(value = "/installments", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token") String token,
                               @RequestParam("installmentsNumber") byte installmentsNumber,
                               @RequestParam("buyOrder") String buyOrder,
                               @RequestParam("commerceCode") String commerceCode) {

        cleanModel();
        addRequest("token", token);
        addRequest("installmentsNumber", installmentsNumber);
        addRequest("buyOrder", buyOrder);
        addRequest("commerceCode", commerceCode);
        System.out.println("Installments Number:"+installmentsNumber);

        MallFullTransactionInstallmentsDetails installmentsDetails = MallFullTransactionInstallmentsDetails.build().add(commerceCode, buyOrder, installmentsNumber);

        try {
            final MallFullTransactionInstallmentsResponse response = tx.installments(token,installmentsDetails);
            addModel("response", response);
            addModel("token", token);
            addModel("buyOrder", buyOrder);
            addModel("commerceCode", commerceCode);
        } catch ( IOException | TransactionInstallmentException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-installment", "model", getModel());
    }

    @RequestMapping(value = {"/commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token") String tokenWs, HttpServletRequest request,
                               @RequestParam("idQueryInstallments") Long idQueryInstallments,
                               @RequestParam("buyOrder") String buyOrder,
                               @RequestParam("commerceCode") String commerceCode) {
        log.info(String.format("token_ws : %s", tokenWs));

        byte deferredPeriodIndex= 1;
        Boolean gracePeriod = false;

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);
        details.put("deferred_period_index", deferredPeriodIndex);
        details.put("grace_period", gracePeriod);
        details.put("buyOrder", buyOrder);
        details.put("commerceCode", commerceCode);

        MallTransactionCommitDetails details2 = MallTransactionCommitDetails.build().add(commerceCode,buyOrder,null, null,gracePeriod);

        try {
            final MallFullTransactionCommitResponse resp = tx.commit(tokenWs,details2);


            log.debug(String.format("response : %s", resp));
            details.put("response", resp);

            MallFullTransactionStatusResponse.Detail detail = resp.getDetails().get(0);
            String childCommerceCode = detail.getCommerceCode();
            String childBuyOrder = detail.getBuyOrder();
            String authorizationCode = detail.getAuthorizationCode();


            IncreaseAmountResponse r1 = tx.increaseAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, 1000);
            log.info(toJson(r1));
            ReversePreAuthorizedAmountResponse r2 = tx.reversePreAuthorizedAmount(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, 1000);
            log.info(toJson(r2));
            IncreaseAuthorizationDateResponse r3 = tx.increaseAuthorizationDate(tokenWs, childCommerceCode, childBuyOrder, authorizationCode);
            log.info(toJson(r3));
            List<DeferredCaptureHistoryResponse> r4 = tx.deferredCaptureHistory(tokenWs, childCommerceCode, childBuyOrder);
            log.info(toJson(r4));


        } catch (TransactionCommitException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-commit", "details", details);
    }

    @RequestMapping(value = {"/status"}, method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final MallFullTransactionStatusResponse response = tx.status(tokenWs);


            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionStatusException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-status", "details", details);
    }

    @RequestMapping(value = "/status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("transaccioncompleta/mall-transaction-status-form", "model", getModel());
    }

    @RequestMapping(value = {"/refund"}, method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token") String tokenWs,
                               @RequestParam("amount") double amount,
                               @RequestParam("commerceCode") String childCommerceCode,
                               @RequestParam("buyOrder") String buyOrder,
                               HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));
        //double amount,String commerceCode, String buyOrder
        cleanModel();
        addRequest("token_ws", tokenWs);
        addRequest("amount", amount);
        addRequest("commerceCode", childCommerceCode);
        addRequest("buyOrder", buyOrder);


        try {
            final MallFullTransactionRefundResponse response = tx.refund(tokenWs,buyOrder,childCommerceCode,amount);
            log.debug(String.format("response : %s", response.getResponseCode()));
            addModel("response", response);
        } catch (IOException | TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-refund", "model", getModel());
    }

    @RequestMapping(value = "/refund-form", method = RequestMethod.GET)
    public ModelAndView refundForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("transaccioncompleta/mall-transaction-refund-form", "model", getModel());
    }

}

