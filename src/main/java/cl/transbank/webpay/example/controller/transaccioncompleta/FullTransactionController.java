package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.Options;
import cl.transbank.patpass.PatpassOptions;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.model.*;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
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
@RequestMapping(value = "/fulltransaction")
public class FullTransactionController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(FullTransactionController.class);

    @RequestMapping(value = "/create-form", method = RequestMethod.GET)
    public ModelAndView createForm(HttpServletRequest request){

        log.info("Transaccion completa FullTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1;
        String cardNumber= "4051885600446623";
        String cardExpirationDate= "22/06";
        short cvv = 456;
        addModel("buyOrder", buyOrder);
        addModel("sessionId", sessionId);
        addModel("amount", amount);
        addModel("cardNumber", cardNumber);
        addModel("cardExpirationDate", cardExpirationDate);
        addModel("cvv", cvv);
        return new ModelAndView("transaccioncompleta/transaction-create-form", "model", getModel());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView fullTransactionCreate(HttpServletRequest request,
                                              @RequestParam("buyOrder") String buyOrder,
                                              @RequestParam("sessionId") String sessionId,
                                              @RequestParam("amount") double amount,
                                              @RequestParam(name="cardNumber") String cardNumber,
                                              @RequestParam("cardExpirationDate") String cardExpirationDate,
                                              @RequestParam("cvv") short cvv) {



        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("sessionId", sessionId);
        details.put("amount", amount);
        details.put("cardNumber", cardNumber);
        details.put("cardExpirationDate", cardExpirationDate);
        details.put("cvv", cvv);
        try {
            FullTransactionCreateResponse response = FullTransaction.Transaction.create(buyOrder, sessionId, amount, cardNumber, cardExpirationDate, cvv);

            details.put("token", response.getToken());
        } catch (TransactionCreateException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-create", "details", details);
    }

    @RequestMapping(value = {"/commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token") String tokenWs, HttpServletRequest request,
                               @RequestParam("idQueryInstallments") Long idQueryInstallments) {
        log.info(String.format("token_ws : %s", tokenWs));

        byte deferredPeriodIndex= 1;
        Boolean gracePeriod = false;

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);
        details.put("deferred_period_index", deferredPeriodIndex);
        details.put("grace_period", gracePeriod);
        try {
            final FullTransactionCommitResponse response = FullTransaction.Transaction.commit(tokenWs,idQueryInstallments,deferredPeriodIndex,gracePeriod);

            details.put("amount", response.getAmount());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionCommitException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-commit", "details", details);
    }

    @RequestMapping(value = "/installments", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token") String token,
                               @RequestParam("installmentsNumber") byte installmentsNumber) {

        cleanModel();
        addRequest("token", token);
        addRequest("installmentsNumber", installmentsNumber);
        System.out.println("Installments Number:"+installmentsNumber);
        try {
            final FullTransactionInstallmentResponse response = FullTransaction.Transaction.installment(token, installmentsNumber);
            addModel("response", response);
            addModel("token", token);
        } catch ( IOException | TransactionInstallmentException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-installments", "model", getModel());
    }

    @RequestMapping(value = {"/status"}, method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);
        try {
            final FullTransactionStatusResponse response = FullTransaction.Transaction.status(tokenWs);

            details.put("amount", response.getAmount());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionStatusException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-status", "details", details);
    }

    @RequestMapping(value = "/status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("transaccioncompleta/transaction-status-form", "model", getModel());
    }

    @RequestMapping(value = {"/refund"}, method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token") String tokenWs,
                               @RequestParam("amount") double amount,HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        cleanModel();
        addRequest("token_ws", tokenWs);
        addRequest("amount", amount);
        try {
            final FullTransactionRefundResponse response = FullTransaction.Transaction.refund(tokenWs,amount);
            log.debug(String.format("response : %s", response.getResponseCode()));
            addModel("response", response);
        } catch (IOException | TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-refund", "model", getModel());
    }

    @RequestMapping(value = "/refund-form", method = RequestMethod.GET)
    public ModelAndView refundForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("transaccioncompleta/transaction-refund-form", "model", getModel());
    }

    private Options getOptions(IntegrationType type){
        Options options = new PatpassOptions();
        options.setApiKey("6E55588C0ECC5507DDE234738FB130F79AD4CD9FD8B875911963D990F7342A8D");
        options.setCommerceCode("597034925658");
        options.setIntegrationType(type);

        return options;
    }

}

