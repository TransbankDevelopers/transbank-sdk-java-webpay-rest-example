package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

@Controller
public class WebpayPlusMallController extends BaseController {
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
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().append("-commit").toString();

        String mallOneCommerceCode = "597055555536";
        String mallTwoCommerceCode = "597055555537";
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
            final WebpayPlusMallTransactionCreateResponse response = WebpayPlus.MallTransaction.create(
                    buyOrder, sessionId, returnUrl, mallDetails);

            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (TransactionCreateException e) {
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
            final WebpayPlusMallTransactionCommitResponse response = WebpayPlus.MallTransaction.commit(tokenWs);

            double amount = 0;
            for (WebpayPlusMallTransactionCommitResponse.Detail detail : response.getDetails()) {
                amount += detail.getAmount();
            }
            details.put("amount", amount);
            details.put("child_commerce_code", response.getDetails().get(0).getCommerceCode());
            details.put("child_buy_order", response.getDetails().get(0).getBuyOrder());
            details.put("child_amount", response.getDetails().get(0).getAmount());

            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-commit", "-refund"));
        } catch (TransactionCommitException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmall-commit", "details", details);
    }

    @RequestMapping(value = "/webpayplusmall-refund-form", method = RequestMethod.GET)
    public ModelAndView webpayplusRefundForm(HttpServletRequest request) {
        String refundEndpoint = "/webpayplusmall-refund";

        Map<String, Object> details = new HashMap<>();
        details.put("refund-endpoint", refundEndpoint);
        return new ModelAndView("webpayplusmall-refund-form", "details", details);
    }

    @RequestMapping(value = "/webpayplusmall-refund", method = RequestMethod.POST)
    public ModelAndView webpayplusRefund(@RequestParam("token_ws") String token,
                                         @RequestParam("child_commerce_code") String childCommerceCode,
                                         @RequestParam("child_buy_order") String childBuyOrder,
                                         @RequestParam("amount") double amount) {

        cleanModel();
        addRequest("token_ws", token);
        addRequest("child_commerce_code", childCommerceCode);
        addRequest("child_buy_order", childBuyOrder);
        addRequest("amount", amount);

        try {
            final WebpayPlusMallTransactionRefundResponse response =
                WebpayPlus.MallTransaction.refund(token, childBuyOrder, childCommerceCode, amount);
            addModel("response", response);
        } catch (TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmall-refund", "model", getModel());
    }

    @RequestMapping(value = "/webpayplusmall-status", method = RequestMethod.POST)
    public ModelAndView webpayplusDeferredStatus(@RequestParam("token_ws") String token){
        cleanModel();
        addRequest("token_ws", token);

        try {
            final WebpayPlusMallTransactionStatusResponse response = WebpayPlus.MallTransaction.status(token);
            addModel("response", response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("webpayplus-status", "model", getModel());
    }
}
