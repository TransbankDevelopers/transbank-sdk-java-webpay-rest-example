package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.TransactionCaptureException;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.*;
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
public class WebpayPlusMallDeferredController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusMallDeferredController.class);

    @RequestMapping(value = "/webpayplusmalldeferred", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        java.util.logging.Logger globalLog = java.util.logging.Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        log.info("Webpay Plus Mall MallTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().append("-commit").toString();

        String mallOneCommerceCode = "597055555546";
        String mallTwoCommerceCode = "597055555545";
       final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("buyOrderMallOne", buyOrderMallOne);
        details.put("buyOrderMallTwo", buyOrderMallTwo);
        details.put("sessionId", sessionId);
        details.put("amountMallOne", amountMallOne);
        details.put("amountMallTwo", amountMallTwo);
        details.put("returnUrl", returnUrl);

        try {

            final WebpayPlusMallTransactionCreateResponse response = WebpayPlus.MallDeferredTransaction.create(buyOrder,
                    sessionId, returnUrl, mallDetails);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (TransactionCreateException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-create", "details", details);
    }

    @RequestMapping(value = {"/webpayplusmalldeferred-capture"}, method = RequestMethod.POST)
    public ModelAndView capture(@RequestParam("token_ws") String tokenWs,
                                @RequestParam("child_commerce_code") String childCommerceCode,
                                @RequestParam("child_buy_order") String childBuyOrder,
                                @RequestParam("child_amount") double amount,
                                @RequestParam("authorization_code") String authorizationCode,
                                HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);
        details.put("child_commerce_code", childCommerceCode);
        details.put("child_buy_order", childBuyOrder);
        details.put("child_amount", amount);

        try {
            final WebpayPlusMallTransactionCaptureResponse response = WebpayPlus.MallDeferredTransaction.capture(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);

            log.debug(String.format("response : %s", response));
            details.put("response", response);

        } catch (IOException | TransactionCaptureException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-capture", "details", details);
    }

    @RequestMapping(value = {"/webpayplusmalldeferred-commit"}, method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusMallTransactionCommitResponse response = WebpayPlus.MallDeferredTransaction.commit(tokenWs);

            double amount = 0;
            for (WebpayPlusMallTransactionCommitResponse.Detail detail : response.getDetails()) {
                amount += detail.getAmount();
            }
            details.put("amount", amount);
            details.put("child_commerce_code", response.getDetails().get(0).getCommerceCode());
            details.put("child_buy_order", response.getDetails().get(0).getBuyOrder());
            details.put("child_amount", response.getDetails().get(0).getAmount());
            details.put("authorization_code", response.getDetails().get(0).getAuthorizationCode());

            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-commit", "-refund"));
        } catch (TransactionCommitException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-commit", "details", details);
    }

    @RequestMapping(value = "/webpayplusmalldeferred-refund-form", method = RequestMethod.GET)
    public ModelAndView refundForm(HttpServletRequest request) {
        String refundEndpoint = "/webpayplusmalldeferred-refund";

        Map<String, Object> details = new HashMap<>();
        details.put("refund-endpoint", refundEndpoint);
        return new ModelAndView("webpayplusmalldeferred-refund-form", "details", details);
    }

    @RequestMapping(value = "/webpayplusmalldeferred-refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("token_ws") String token,
                                         @RequestParam("child_commerce_code") String childCommerceCode,
                                         @RequestParam("child_buy_order") String childBuyOrder,
                                         @RequestParam("child_amount") double amount) {

        cleanModel();
        addRequest("token_ws", token);
        addRequest("child_commerce_code", childCommerceCode);
        addRequest("child_buy_order", childBuyOrder);
        addRequest("child_amount", amount);

        try {

            final WebpayPlusTransactionRefundResponse response = WebpayPlus.MallDeferredTransaction.refund(token, childBuyOrder, childCommerceCode, amount);
            addModel("response", response);
        } catch (TransactionRefundException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-refund", "model", getModel());
    }

    @RequestMapping(value = "/webpayplusmalldeferred-status", method = RequestMethod.POST)
    public ModelAndView status(@RequestParam("token_ws") String token){
        cleanModel();
        addRequest("token_ws", token);

        try {
            final WebpayPlusMallTransactionStatusResponse status = WebpayPlus.MallDeferredTransaction.status(token);
            addModel("response", status);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("webpayplus-status", "model", getModel());
    }

    @RequestMapping(value = "/webpayplusmalldeferred-status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request) {
        String statusEndpoint = "/webpayplusmalldeferred-status";

        addModel("endpoint", statusEndpoint);
        return new ModelAndView("webpayplus-status-form", "model", getModel());
    }
}
