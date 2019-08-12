package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.model.FullTransactionCommitResponse;
import cl.transbank.transaccioncompleta.model.FullTransactionCreateResponse;
import cl.transbank.transaccioncompleta.model.FullTransactionInstallmentResponse;
import cl.transbank.transaccioncompleta.model.FullTransactionStatusResponse;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionInstallmentException;
import cl.transbank.webpay.exception.TransactionStatusException;
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
@RequestMapping(value = "/fulltransaction")
public class FullTransactionController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(FullTransactionController.class);

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView fullTransactionCreate(HttpServletRequest request) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        log.info("Transaccion completa FullTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 10000;
        String cardNumber= "4051885600446623";
        String cardExpirationDate= "23/03";
        short cvv = 123;

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
    public ModelAndView patpassWebpayStatusForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("transaccioncompleta/transaction-status-form", "model", getModel());
    }

}

