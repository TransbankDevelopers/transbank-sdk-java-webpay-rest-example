package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
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

@Controller
public class WebpayPlusMallController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusMallController.class);
    private WebpayPlus.MallTransaction tx;
    public WebpayPlusMallController(){
        tx = new WebpayPlus.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }


    @RequestMapping(value = "/webpayplusmall", method = RequestMethod.GET)
    public ModelAndView webpayplusMallCreate(HttpServletRequest request) {

        log.info("Webpay Plus Mall MallTransaction.create");
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        double amountMallTwo = 1000;
        String returnUrl = request.getRequestURL().append("-commit").toString();

        String mallOneCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_CHILD1;
        String mallTwoCommerceCode = IntegrationCommerceCodes.WEBPAY_PLUS_MALL_CHILD2;
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
            final WebpayPlusMallTransactionCreateResponse response = tx.create(
                    buyOrder, sessionId, returnUrl, mallDetails);

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

        return new ModelAndView("webpayplusmall-create", "details", details);
    }

    @RequestMapping(value = {"/webpayplusmall-commit"}, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
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

        Map<String, Object> details = new HashMap<>();

        Map<String, Object> req = new HashMap<>();
        req.put("token_ws", token);
        req.put("child_commerce_code", childCommerceCode);
        req.put("child_buy_order", childBuyOrder);
        req.put("amount", amount);

        details.put("req", toJson(req));

        try {
            final WebpayPlusMallTransactionRefundResponse response = tx.refund(token, childBuyOrder, childCommerceCode, amount);

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

        return new ModelAndView("webpayplusmall-refund", "details", details);
    }

    @RequestMapping(value = "/webpayplusmall-status", method = RequestMethod.POST)
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
}
