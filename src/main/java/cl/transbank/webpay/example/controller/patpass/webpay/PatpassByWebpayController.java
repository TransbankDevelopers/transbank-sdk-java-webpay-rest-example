package cl.transbank.webpay.example.controller.patpass.webpay;

import cl.transbank.patpass.PatpassByWebpay;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCreateResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionRefundResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionStatusResponse;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.webpayplus.model.WebpayPlusMallTransactionCommitResponse;
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
@RequestMapping("/patpass-webpay")
public class PatpassByWebpayController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(PatpassByWebpayController.class);

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        log.info("Patpass Webpay Transaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String returnUrl = request.getRequestURL().toString().replaceFirst("create","commit");
        String serviceId = nextString(20);
        String cardHolderId = nextString(20);
        String cardHolderName = nextString(20);
        String cardHolderLastName1 = nextString(20);
        String cardHolderLastName2 = nextString(20);
        String cardHolderMail = String.format("%s@%s.COM", nextString(10), nextString(7));
        String cellphoneNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String expirationDate = "2222-11-11";
        String commerceMail = String.format("%s@%s.COM", nextString(10), nextString(7));

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("sessionId", sessionId);
        details.put("serviceId", serviceId);
        details.put("cardHolderId", cardHolderId);
        details.put("cardHolderName", cardHolderName);
        details.put("cardHolderLastName1", cardHolderLastName1);
        details.put("cardHolderLastName2", cardHolderLastName2);
        details.put("cardHolderMail", cardHolderMail);
        details.put("cellphoneNumber", cellphoneNumber);
        details.put("expirationDate", expirationDate);
        details.put("commerceMail", commerceMail);

        try {
            PatpassByWebpayTransactionCreateResponse response = PatpassByWebpay.Transaction.create(buyOrder, sessionId, 1000, returnUrl, serviceId, cardHolderId, cardHolderName,
                    cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, false, null);
            details.put("token", response.getToken());
            details.put("url", response.getUrl());
        } catch (TransactionCreateException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-create", "details", details);
    }

    @RequestMapping(value = {"/commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final PatpassByWebpayTransactionCommitResponse response = PatpassByWebpay.Transaction.commit(tokenWs);

            details.put("amount", response.getAmount());
            log.debug(String.format("response : %s", response));
            details.put("response", response);
        } catch (TransactionCommitException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-commit", "details", details);
    }


    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token_ws") String token,
                                         @RequestParam("amount") double amount) {

        cleanModel();
        addRequest("token_ws", token);
        addRequest("amount", amount);

        try {
            final PatpassByWebpayTransactionRefundResponse response = PatpassByWebpay.Transaction.refund(token, 10);
            addModel("response", response);
        } catch (TransactionRefundException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-refund", "model", getModel());
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token_ws") String token){
        cleanModel();
        addRequest("token_ws", token);

        try {
            final PatpassByWebpayTransactionStatusResponse response = PatpassByWebpay.Transaction.status(token);
            addModel("response", response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-status", "model", getModel());
    }

    @RequestMapping(value = "/refund-form", method = RequestMethod.GET)
    public ModelAndView patpassWebpayRefundForm(HttpServletRequest request) {
        String endpoint = request.getRequestURL().toString().replace("-form", "");

        Map<String, Object> details = new HashMap<>();
        details.put("refund-endpoint", endpoint);
        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-refund-form", "details", details);
    }

    @RequestMapping(value = "/status-form", method = RequestMethod.GET)
    public ModelAndView patpassWebpayStatusForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("patpasswebpay/patpass-webpay-transaction-status-form", "model", getModel());
    }

    static String nextString(int length) {
        char[] buf = new char[length];
        Random random = new Random();
        final char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}
