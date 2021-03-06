package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.MallFullTransaction;
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
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

@Controller
@RequestMapping(value = "/mallfulltransaction")
public class MallFullTransactionController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(MallFullTransactionController.class);

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
        details.put("commerceCode", "597055555552");

        try {
            MallFullTransactionCreateResponse response = MallFullTransaction.Transaction.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(1000, "597055555552", buyOrder));
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
            final MallFullTransactionInstallmentsResponse response = MallFullTransaction.Transaction.installment(token,installmentsDetails);
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

        MallTransactionCommitDetails details2 = MallTransactionCommitDetails.build().add(commerceCode,buyOrder,idQueryInstallments,deferredPeriodIndex,gracePeriod);

        try {
            final MallFullTransactionCommitResponse response = MallFullTransaction.Transaction.commit(tokenWs,details2);


            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionCommitException | IOException e) {
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
            final MallFullTransactionStatusResponse response = MallFullTransaction.Transaction.status(tokenWs);


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
                               @RequestParam("commerceCode") String commerceCode,
                               @RequestParam("buyOrder") String buyOrder,
                               HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));
        //double amount,String commerceCode, String buyOrder
        cleanModel();
        addRequest("token_ws", tokenWs);
        addRequest("amount", amount);
        addRequest("commerceCode", commerceCode);
        addRequest("buyOrder", buyOrder);


        try {
            final MallFullTransactionRefundResponse response = MallFullTransaction.Transaction.refund(tokenWs,amount,commerceCode,buyOrder);
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

