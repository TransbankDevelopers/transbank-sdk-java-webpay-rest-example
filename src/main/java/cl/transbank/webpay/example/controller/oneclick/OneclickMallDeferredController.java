package cl.transbank.webpay.example.controller.oneclick;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.exception.InscriptionFinishException;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionAuthorizeException;
import cl.transbank.webpay.exception.TransactionCaptureException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

@Controller
@RequestMapping("/oneclick-mall-deferred")
public class OneclickMallDeferredController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OneclickMallDeferredController.class);

    @Getter(AccessLevel.PRIVATE)
    private String username = "goncafadeferred";
    @Getter(AccessLevel.PRIVATE)
    private String email = "gonzalo.castillo@continuum.cl";
    @Getter(AccessLevel.PRIVATE)
    private String mallOneCommerceCode = IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED_CHILD1;
    @Getter(AccessLevel.PRIVATE)
    private String mallTwoCommerceCode = IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED_CHILD2;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private String userTbkToken;


    private Oneclick.MallInscription inscription;
    private Oneclick.MallTransaction tx;
    public OneclickMallDeferredController(){
        prepareLoger(Level.ALL);
        tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
        inscription = new Oneclick.MallInscription(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {
        logger.info("Oneclick.MallDeferredInscription.start");

        // clean model
        cleanModel();

        String responseUrl = request.getRequestURL().toString().replace("/start", "/finish");
        logger.info(String.format("responseUrl : %s", responseUrl));

        // add request to storage in order to send them to the view
        addRequest("username", getUsername());
        addRequest("email", getEmail());
        addRequest("responseUrl", responseUrl);

        try {
            // call the SDK
            final OneclickMallInscriptionStartResponse response = inscription.start(getUsername(),
                    getEmail(), responseUrl);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                // add response to model in order to send it to the view
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_token", response.getToken());
                addModel("url_webpay", response.getUrlWebpay());
            }
        } catch (InscriptionStartException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-deferred-start-inscription-form", "model", getModel());
    }

    @RequestMapping(value = "/finish", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView finish(@RequestParam("TBK_TOKEN") String token) {
        logger.info("Oneclick.MallDeferredInscription.finish");
        logger.info(String.format("TBK_TOKEN : %s", token));

        // clean model
        cleanModel();
        setUserTbkToken(token);

        // add request to storage in order to send it to the view
        addRequest("token", token);

        try {
            final OneclickMallInscriptionFinishResponse response = inscription.finish(token);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_user", response.getTbkUser());
                addModel("username", getUsername());
                addModel("token", token);
            }
        } catch (InscriptionFinishException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-deferred-authorize-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(@RequestParam("username") String username, @RequestParam("tbk_user") String tbkUser,
            @RequestParam("amount") double amount) {
        logger.info("Oneclick.MallDeferredTransaction.authorize");
        logger.info(String.format("username : %s", username));
        logger.info(String.format("tbk_user : %s", tbkUser));

        // clean model
        cleanModel();

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        MallTransactionCreateDetails details = MallTransactionCreateDetails.build()
                .add(amount, getMallOneCommerceCode(), buyOrderMallOne, (byte) 1)
                .add(amount, getMallTwoCommerceCode(), buyOrderMallTwo, (byte) 1);

        // add request to storage in order to send them to the view
        addRequest("buyOrder", buyOrder);
        addRequest("buyOrderMallOne", buyOrderMallOne);
        addRequest("buyOrderMallTwo", buyOrderMallTwo);
        addRequest("details", details);

        try {
            final OneclickMallTransactionAuthorizeResponse response = tx.authorize(username, tbkUser, buyOrder, details);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);
                OneclickMallTransactionAuthorizeResponse.Detail detail = response.getDetails().get(0);
                addModel("buyOrder", buyOrder);
                addModel("childBuyOrder", detail.getBuyOrder());
                addModel("commerceCode", detail.getCommerceCode());
                addModel("authorizationCode", detail.getAuthorizationCode());
                addModel("amount", detail.getAmount());
            }
        } catch (TransactionAuthorizeException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-deferred-show-capture-form", "model", getModel());
    }

    @RequestMapping(value = "/capture", method = RequestMethod.POST)
    public ModelAndView capture(@RequestParam("child_commerce_code") String childCommerceCode, 
    @RequestParam("child_buy_order") String childBuyOrder, @RequestParam("authorization_code") String authorizationCode, @RequestParam("amount") String amount, @RequestParam("buy_order") String buyOrder) {

        cleanModel();
        addRequest("childCommerceCode", childCommerceCode);
        addRequest("childBuyOrder", childBuyOrder);
        addRequest("authorizationCode", authorizationCode);
        addRequest("amount", amount);
        double doubleAmount = Double.parseDouble(amount);

        try {
            final OneclickMallTransactionCaptureResponse response = tx.capture(childCommerceCode, childBuyOrder, authorizationCode, doubleAmount);

            if (null != response) {
                logger.info(String.format("response : %s", response));
                addModel("response", response);

                addModel("buyOrder", buyOrder);
                addModel("childOneCommerceCode", getMallOneCommerceCode());
                addModel("chileOneBuyOrder", childBuyOrder);
                addModel("amountMallOne", amount);
            }
        } catch (TransactionCaptureException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-deferred-refund-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("buy_order") String buyOrder,
            @RequestParam("child_one_commerce_code") String childOneCommerceCode,
            @RequestParam("child_one_buy_order") String chileOneBuyOrder,
            @RequestParam("amount_mall_one") double amountMallOne) {

        // clean model
        cleanModel();

        // add request to storage in order to send them to the view
        addRequest("buy_order", buyOrder);
        addRequest("child_one_commerce_code", childOneCommerceCode);
        addRequest("child_one_buy_order", chileOneBuyOrder);
        addRequest("amount_mall_one", amountMallOne);

        try {
            final OneclickMallTransactionRefundResponse response = tx.refund(buyOrder,
                    childOneCommerceCode, chileOneBuyOrder, amountMallOne);
            logger.info(String.format("response : %s", response));
            if (null != response) {
                addModel("response", String.format("response : %s", response));
            }
        } catch (TransactionRefundException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-deferred-end", "model", getModel());
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ModelAndView statusRequest(@RequestParam("buy_order") String buyOrder){
        cleanModel();

        addRequest("buy_order", buyOrder);
        try {
            final OneclickMallTransactionStatusResponse response = tx.status(buyOrder);
            if (null != response) {
                String message = String.format("response : %s", response);
                logger.info(message);
                addModel("response", message);
            }
        }catch (TransactionStatusException | IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("oneclick/oneclick-mall-status-request", "model", getModel());
    }
    @RequestMapping(value = "/inscription-delete", method = RequestMethod.GET)
    public ModelAndView inscriptionDelete(){
        logger.info("OneclickMall.Inscription.delete");
        logger.info(String.format("username : %s", getUsername()));
        logger.info(String.format("tbk_token : %s", getUserTbkToken()));

        cleanModel();

        addRequest("username", getUsername());
        addRequest("tbk_token", getUserTbkToken());

        try {
            inscription.delete(getUsername(), getUserTbkToken());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return new ModelAndView("oneclick/oneclick-mall-inscription-delete", "model", getModel());
    }

    @RequestMapping(value = "/refund-form", method = RequestMethod.GET)
    public ModelAndView refundForm(HttpServletRequest request) {
        return new ModelAndView("oneclick/oneclick-mall-deferred-refund-form", "model", getModel());
    }

    @RequestMapping(value = "/status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request){
        return new ModelAndView("oneclick/oneclick-mall-deferred-status-form", "model", getModel());
    }
}
