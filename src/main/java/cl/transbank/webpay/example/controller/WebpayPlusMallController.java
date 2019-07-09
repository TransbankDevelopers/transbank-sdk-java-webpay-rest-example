package cl.transbank.webpay.example.controller;

import cl.transbank.webpay.Options;
import cl.transbank.webpay.exception.CommitTransactionException;
import cl.transbank.webpay.exception.CreateTransactionException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusMallTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateMallTransactionDetails;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusMallTransactionResponse;
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
public class WebpayPlusMallController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusMallController.class);

    @RequestMapping(value = "/webpayplusmall", method = RequestMethod.GET)
    public ModelAndView webpayplusMallCreate(HttpServletRequest request) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        log.info("Webpay Plus Mall MallTransaction.create");
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().append("-commit").toString();

        String mallOneCommerceCode = "597055555536";
        String mallTwoCommerceCode = "597055555537";
        final CreateMallTransactionDetails mallDetails = CreateMallTransactionDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrderMallOne", buyOrderMallOne);
        details.put("buyOrderMallTwo", buyOrderMallTwo);
        details.put("sessionId", sessionId);
        details.put("amountMallOne", amountMallOne);
        details.put("amountMallTwo", amountMallTwo);
        details.put("returnUrl", returnUrl);

        try {
            Options options = WebpayPlus.buildOptionsForTestingWebpayPlusMall();
            final CreateWebpayPlusMallTransactionResponse response = WebpayPlus.MallTransaction.create(
                    buyOrderMallOne, sessionId, returnUrl, mallDetails, options);

            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (CreateTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmall-create", "details", details);
    }

    @RequestMapping(value = {"/webpayplusmall-commit"}, method = RequestMethod.POST)
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            Options options = WebpayPlus.buildOptionsForTestingWebpayPlusMall();
            final CommitWebpayPlusMallTransactionResponse response = WebpayPlus.MallTransaction.commit(tokenWs, options);

            double amount = 0;
            for (CommitWebpayPlusMallTransactionResponse.Detail detail : response.getDetails()) {
                amount += detail.getAmount();
            }
            details.put("amount", amount);

            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-commit", "-refund"));
        } catch (CommitTransactionException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmall-commit", "details", details);
    }
}
