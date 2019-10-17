package cl.transbank.webpay.example.controller.oneclick;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.Options;
import cl.transbank.patpass.PatpassOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.model.*;
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
@RequestMapping("/oneclick-mall")
public class OneclickMallController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OneclickMallController.class);

    @Getter(AccessLevel.PRIVATE) @Setter private String username = "username";
    @Getter(AccessLevel.PRIVATE) @Setter private String email = "username@mail.com";
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private String userTbkToken;
    @Getter(AccessLevel.PRIVATE) @Setter private String mallOneCommerceCode = "597055555542";
    @Getter(AccessLevel.PRIVATE) @Setter private String mallTwoCommerceCode = "597055555543";

    public OneclickMallController() {
        prepareLoger(Level.ALL);
    }

    @RequestMapping(value = "/start-form", method = RequestMethod.GET)
    public ModelAndView startsForm(HttpServletRequest request){
        // clean model
        cleanModel();

        String responseUrl = request.getRequestURL().toString().replace("/start-form", "/finish");
        logger.info(String.format("responseUrl : %s", responseUrl));

        addModel("username", getUsername());
        addModel("email", getEmail());
        addModel("responseUrl", responseUrl);
        return new ModelAndView("oneclick/oneclick-mall-start-inscription-form", "model", getModel());
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ModelAndView start(HttpServletRequest request,
                              @RequestParam("username") String username,
                              @RequestParam("email") String email,
                              @RequestParam("responseUrl") String responseUrl) {
        logger.info("OneclickMall.Inscription.start");

        this.setEmail(email);
        this.setUsername(username);

        // add request to storage in order to send them to the view
        addRequest("username", getUsername());
        addRequest("email", getEmail());
        addRequest("responseUrl", responseUrl);

        try {
            // call the SDK
            final OneclickMallInscriptionStartResponse response = OneclickMall.Inscription.start(getUsername(), getEmail(), responseUrl);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                // add response to model in order to send it to the view
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_token", response.getToken());
                addModel("url_webpay", response.getUrlWebpay());
            }
        }  catch (InscriptionStartException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-inscription-started", "model", getModel());
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public ModelAndView finish(@RequestParam("TBK_TOKEN") String token) {
        logger.info("OneclickMall.Inscription.finish");
        logger.info(String.format("TBK_TOKEN : %s", token));
        setUserTbkToken(token);

        // clean model
        cleanModel();

        // add request to storage in order to send it to the view
        addRequest("token", token);
        Options options = getOptions(IntegrationType.LIVE);
        try {
            final OneclickMallInscriptionFinishResponse response = OneclickMall.Inscription.finish(token);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_user", response.getTbkUser());
                addModel("username", getUsername());

                String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
                String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
                byte installmentsOne = (byte) 1;
                String commerceCodeOne = getMallOneCommerceCode();
                double amount1= 1;

                addModel("buyOrder", buyOrder);
                addModel("buyOrderMallOne", buyOrderMallOne);
                addModel("installmentsOne", installmentsOne);
                addModel("commerceCodeOne", commerceCodeOne);
                addModel("amount1", amount1);
            }
        } catch (InscriptionFinishException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-authorize-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(@RequestParam("username") String username,
                                  @RequestParam("tbk_user") String tbkUser,
                                  @RequestParam("amount1") double amount1,
                                  @RequestParam(name="amount2",required = false, defaultValue = "0") double amount2,
                                  @RequestParam("buyOrder") String buyOrder,
                                  @RequestParam("buyOrderMallOne") String buyOrderMallOne,
                                  @RequestParam("installmentsOne") byte installmentsOne,
                                  @RequestParam("commerceCodeOne") String commerceCodeOne,
                                  @RequestParam(name="buyOrderMallTwo",defaultValue = "") String buyOrderMallTwo,
                                  @RequestParam(name="installmentsTwo", required = false, defaultValue = "0")  byte installmentsTwo,
                                  @RequestParam(name="commerceCodeTwo",defaultValue = "") String commerceCodeTwo) {
        logger.info("OneclickMall.Transaction.authorize");
        logger.info(String.format("username : %s", username));
        logger.info(String.format("tbk_user : %s", tbkUser));

        // clean model
        cleanModel();

        this.setMallOneCommerceCode(commerceCodeOne);
        this.setMallTwoCommerceCode(commerceCodeTwo);


        MallTransactionCreateDetails details = MallTransactionCreateDetails.build()
                .add(amount1, getMallOneCommerceCode(), buyOrderMallOne, installmentsOne);
            if(amount2 > 0 && !buyOrderMallTwo.equalsIgnoreCase("") &&
                    !commerceCodeTwo.equalsIgnoreCase("") &&
                    installmentsTwo>0)
                details.add(amount2, getMallTwoCommerceCode(), buyOrderMallTwo, installmentsTwo);

        // add request to storage in order to send them to the view
        addRequest("buyOrder", buyOrder);
        addRequest("buyOrderMallOne", buyOrderMallOne);
        addRequest("buyOrderMallTwo", buyOrderMallTwo);
        addRequest("details", details);
        try {
            final OneclickMallTransactionAuthorizeResponse response = OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);

                addModel("buyOrder", buyOrder);
                addModel("childOneCommerceCode", getMallOneCommerceCode());
                addModel("childTwoCommerceCode", getMallTwoCommerceCode());
                addModel("chileOneBuyOrder", buyOrderMallOne);
                addModel("chileTwoBuyOrder", buyOrderMallTwo);
                addModel("amountMallOne", amount1);
                addModel("amountMallTwo", amount2);
            }
        } catch (TransactionAuthorizeException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-refund-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("buy_order") String buyOrder,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam("amount") double amount) {
        logger.info("OneclickMall.Transaction.refund");
        logger.info(String.format("buy_order : %s", buyOrder));
        logger.info(String.format("child_commerce_code : %s", childCommerceCode));
        logger.info(String.format("child_buy_order : %s", buyOrder));
        logger.info(String.format("amount : %s", amount));

        // clean model
        cleanModel();

        // add request to storage in order to send them to the view
        addRequest("buy_order", buyOrder);
        addRequest("child_commerce_code", childCommerceCode);
        addRequest("child_buy_order", childBuyOrder);
        addRequest("amount", amount);
        Options options = getOptions(IntegrationType.LIVE);
        try {
            final OneclickMallTransactionRefundResponse response = OneclickMall.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", String.format("response : %s", response));
            }
        } catch (TransactionRefundException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-end", "model", getModel());
    }

    @RequestMapping(value = "/refund-form", method = RequestMethod.GET)
    public ModelAndView refundForm(HttpServletRequest request) {
        return new ModelAndView("oneclick/oneclick-mall-refund-form", "model", getModel());
    }

    @RequestMapping(value = "/status-form", method = RequestMethod.GET)
    public ModelAndView statusForm(HttpServletRequest request){
        return new ModelAndView("oneclick/oneclick-mall-status-form", "model", getModel());
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public ModelAndView statusRequest(@RequestParam("buy_order") String buyOrder){
        logger.info("OneclickMall.Transaction.status");
        logger.info(String.format("buy_order : %s", buyOrder));

        cleanModel();

        addRequest("buy_order", buyOrder);
        Options options = getOptions(IntegrationType.LIVE);
        try {
            final OneclickMallTransactionStatusResponse response = OneclickMall.Transaction.status(buyOrder);
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
        Options options = getOptions(IntegrationType.LIVE);
        try {
            OneclickMall.Inscription.delete(getUsername(), getUserTbkToken());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return new ModelAndView("oneclick/oneclick-mall-inscription-delete", "model", getModel());
    }

    private Options getOptions(IntegrationType type){
        Options options = new PatpassOptions();
        options.setApiKey("C60B616633B57B9D0ACB346E9F3F18B414B702A2FE1172DEDC2F5E16EB9FB433");
        options.setCommerceCode("597034926328");
        options.setIntegrationType(type);

        return options;
    }
}
