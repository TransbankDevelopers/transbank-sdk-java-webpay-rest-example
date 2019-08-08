package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.patpass.PatpassByWebpay;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.model.FullTransactionCreateResponse;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
        } catch (TransactionCreateException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-create", "details", details);
    }

   /* @RequestMapping(value = {"/commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final FullTransactionCommitResponse response = FullTransaction.Transaction.commit(tokenWs);

            details.put("amount", response.getAmount());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionCommitException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("transaccioncompleta/transaction-create", "details", details);
    }*/

}

