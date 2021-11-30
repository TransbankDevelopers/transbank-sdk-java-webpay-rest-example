package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.*;
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

    private WebpayPlus.MallTransaction tx;
    public WebpayPlusMallDeferredController(){
        tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/webpayplusmalldeferred", method = RequestMethod.GET)
    public ModelAndView create(HttpServletRequest request) {

        log.info("Webpay Plus Mall MallTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().append("-commit").toString();

        String mallOneCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED_CHILD1;
        String mallTwoCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED_CHILD2;
       final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        Map<String, Object> details = new HashMap<>();

        Map<String, Object> req = new HashMap<>();
        req.put("buyOrder", buyOrder);
        req.put("buyOrderMallOne", buyOrderMallOne);
        req.put("buyOrderMallTwo", buyOrderMallTwo);
        req.put("sessionId", sessionId);
        req.put("amountMallOne", amountMallOne);
        req.put("amountMallTwo", amountMallTwo);
        req.put("returnUrl", returnUrl);
        req.put("details", mallDetails);

        details.put("req", toJson(req));

        try {

            final WebpayPlusMallTransactionCreateResponse response = tx.create(buyOrder,
                    sessionId, returnUrl, mallDetails);
            details.put("url", response.getUrl());
            details.put("token", response.getToken());

            details.put("resp", toJson(response));
        }
        catch (TransactionCreateException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
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

        Map<String, Object> req = new HashMap<>();
        req.put("token_ws", tokenWs);
        req.put("child_commerce_code", childCommerceCode);
        req.put("child_buy_order", childBuyOrder);
        req.put("child_amount", amount);

        details.put("req", toJson(req));

        try {
            final WebpayPlusMallTransactionCaptureResponse response = tx.capture(tokenWs, childCommerceCode, childBuyOrder, authorizationCode, amount);

            log.debug(String.format("response : %s", response));
            details.put("response", response);

            details.put("resp", toJson(response));
        }
        catch (TransactionCaptureException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-capture", "details", details);
    }

    @RequestMapping(value = {"/webpayplusmalldeferred-commit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView commit(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusMallTransactionCommitResponse response = tx.commit(tokenWs);

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

            details.put("resp", toJson(response));
        }
        catch (TransactionCommitException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
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

        Map<String, Object> details = new HashMap<>();

        Map<String, Object> req = new HashMap<>();
        req.put("token_ws", token);
        req.put("child_commerce_code", childCommerceCode);
        req.put("child_buy_order", childBuyOrder);
        req.put("amount", amount);

        details.put("req", toJson(req));

        try {

            final WebpayPlusTransactionRefundResponse response = tx.refund(token, childBuyOrder, childCommerceCode, amount);
            details.put("resp", toJson(response));
        }
        catch (TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }

        return new ModelAndView("webpayplusmalldeferred-refund", "details", details);
    }

    @RequestMapping(value = "/webpayplusmalldeferred-status", method = RequestMethod.POST)
    public ModelAndView webpayplusDeferredStatus(@RequestParam("token_ws") String token){
        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", token);

        try {
            final WebpayPlusMallTransactionStatusResponse response = tx.status(token);
            details.put("resp", toJson(response));
        }
        catch (TransactionStatusException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("webpayplus-status", "details", details);
    }

    @RequestMapping(value = "/webpayplusmalldeferred-status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request) {
        String statusEndpoint = "/webpayplusmalldeferred-status";

        addModel("endpoint", statusEndpoint);
        return new ModelAndView("webpayplus-status-form", "model", getModel());
    }


}
