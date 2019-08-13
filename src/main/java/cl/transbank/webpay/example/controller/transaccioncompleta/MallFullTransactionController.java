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
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

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

        try {
            MallFullTransactionCreateResponse response = MallFullTransaction.Transaction.create(buyOrder, sessionId, cardNumber, cardExpirationDate, MallTransactionCreateDetails.build()
                    .add(1000, "597055555536", "r234n347"));
            details.put("token", response.getToken());
        } catch (TransactionCreateException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/mall-transaction-create", "details", details);
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

        return new ModelAndView("transaccioncompleta/mall-transaction-installment", "model", getModel());
    }
}

