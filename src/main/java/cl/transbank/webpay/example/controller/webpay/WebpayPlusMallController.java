package cl.transbank.webpay.example.controller.webpay;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.Options;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.patpass.PatpassOptions;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

@Controller
public class WebpayPlusMallController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusMallController.class);

    @RequestMapping(value = "/webpayplusmall-form", method = RequestMethod.GET)
    public ModelAndView webpayplusForm(HttpServletRequest request) {
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1;
        String mallOneCommerceCode = "597034918546";
        String returnUrl = request.getRequestURL().toString().replace("-form", "-commit");

        addModel("returnUrl", returnUrl);
        addModel("buyOrder", buyOrder);
        addModel("sessionId", sessionId);
        addModel("amount1", amountMallOne);
        addModel("commerceCode1", mallOneCommerceCode);
        addModel("buyOrder1", buyOrderMallOne);



        return new ModelAndView("webpayplusmall-form", "model", getModel());
    }

    @RequestMapping(value = "/webpayplusmall", method = RequestMethod.POST)
    public ModelAndView webpayplusMallCreate(HttpServletRequest request,
                                             @RequestParam("returnUrl") String returnUrl,
                                             @RequestParam("buyOrder") String buyOrder,
                                             @RequestParam("sessionId") String sessionId,
                                             @RequestParam(name="amount1") Double amount1,
                                             @RequestParam(name="amount2", required = false) Double amount2,
                                             @RequestParam(name="buyOrder1") String buyOrder1,
                                             @RequestParam(name="buyOrder2",defaultValue = "") String buyOrder2,
                                             @RequestParam("commerceCode1")  String commerceCode1,
                                             @RequestParam(name="commerceCode2", defaultValue = "") String commerceCode2) {

        log.info("Webpay Plus Mall MallTransaction.create");

       // String mallTwoCommerceCode = "597055555537";
        final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amount1, commerceCode1, buyOrder1);
        if(amount2!=null && !buyOrder2.equalsIgnoreCase("") && !commerceCode2.equalsIgnoreCase("")){
            mallDetails.add(amount2, commerceCode2, buyOrder2);
        }

        Map<String, Object> details = new HashMap<>();
        details.put("buyOrder", buyOrder);
        details.put("buyOrderMallOne", buyOrder1);
        details.put("buyOrderMallTwo", buyOrder2);
        details.put("sessionId", sessionId);
        details.put("amountMallOne", amount1);
        details.put("amountMallTwo", amount2);
        details.put("mallOneCommerceCode", commerceCode1);
        details.put("mallTwpCommerceCode", commerceCode2);
        details.put("returnUrl", returnUrl);
        try {
            final WebpayPlusMallTransactionCreateResponse response = WebpayPlus.MallTransaction.create(
                    buyOrder, sessionId, returnUrl, mallDetails);

            details.put("url", response.getUrl());
            details.put("token", response.getToken());
        } catch (TransactionCreateException | IOException e) {
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
        } catch (TransactionCommitException | IOException e) {
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
        } catch (TransactionRefundException | IOException e) {
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

    private Options getOptions(IntegrationType type){
        Options options = new PatpassOptions();
        options.setApiKey("C60B616633B57B9D0ACB346E9F3F18B414B702A2FE1172DEDC2F5E16EB9FB433");
        options.setCommerceCode("34925933");
        options.setIntegrationType(type);

        return options;
    }
}
