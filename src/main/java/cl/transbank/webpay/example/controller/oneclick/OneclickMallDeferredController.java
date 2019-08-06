package cl.transbank.webpay.example.controller.oneclick;

import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.exception.InscriptionFinishException;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionAuthorizeException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.OneclickMallDeferred;
import cl.transbank.webpay.oneclick.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.logging.Level;

@Controller
@RequestMapping("/oneclick-mall-deferred")
public class OneclickMallDeferredController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OneclickMallDeferredController.class);

    @Getter(AccessLevel.PRIVATE) private String username = "goncafadeferred";
    @Getter(AccessLevel.PRIVATE) private String email = "gonzalo.castillo@continuum.cl";
    @Getter(AccessLevel.PRIVATE) private String mallOneCommerceCode = "597055555548";
    @Getter(AccessLevel.PRIVATE) private String mallTwoCommerceCode = "597055555549";

    public OneclickMallDeferredController() {
        prepareLoger(Level.ALL);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {
        logger.info("OneclickMallDeferred.Inscription.start");

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
            final OneclickMallInscriptionStartResponse response = OneclickMallDeferred.Inscription.start(getUsername(), getEmail(), responseUrl);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                // add response to model in order to send it to the view
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_token", response.getToken());
                addModel("url_webpay", response.getUrlWebpay());
            }
        }catch (InscriptionStartException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-start-inscription-form", "model", getModel());
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public ModelAndView finish(@RequestParam("TBK_TOKEN") String token) {
        logger.info("OneclickMallDeferred.Inscription.finish");
        logger.info(String.format("TBK_TOKEN : %s", token));

        // clean model
        cleanModel();

        // add request to storage in order to send it to the view
        addRequest("token", token);

        try {
            final OneclickMallInscriptionFinishResponse response = OneclickMallDeferred.Inscription.finish(token);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);

                // add necesary data to make form works
                addModel("tbk_user", response.getTbkUser());
                addModel("username", getUsername());
            }
        }catch (InscriptionFinishException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-authorize-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(@RequestParam("username") String username,
                                  @RequestParam("tbk_user") String tbkUser,
                                  @RequestParam("amount") double amount) {
        logger.info("OneclickMall.Transaction.authorize");
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
            final OneclickMallTransactionAuthorizeResponse response = OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", response);

                addModel("buyOrder", buyOrder);
                addModel("childOneCommerceCode", getMallOneCommerceCode());
                addModel("childTwoCommerceCode", getMallTwoCommerceCode());
                addModel("chileOneBuyOrder", buyOrderMallOne);
                addModel("chileTwoBuyOrder", buyOrderMallTwo);
                addModel("amountMallOne", amount);
                addModel("amountMallTwo", amount);
            }
        }catch (TransactionAuthorizeException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-refund-payment-form", "model", getModel());
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("buy_order") String buyOrder,
                               @RequestParam("child_one_commerce_code") String childOneCommerceCode,
                               @RequestParam("child_two_commerce_code") String childTwoCommerceCode,
                               @RequestParam("child_one_buy_order") String chileOneBuyOrder,
                               @RequestParam("child_two_buy_order") String chileTwoBuyOrder,
                               @RequestParam("amount_mall_one") double amountMallOne,
                               @RequestParam("amount_mall_two") double amountMallTwo) {
        logger.info("OneclickMall.Transaction.refund");
        logger.info(String.format("buy_order : %s", buyOrder));
        logger.info(String.format("child_one_commerce_code : %s", childOneCommerceCode));
        logger.info(String.format("child_two_commerce_code : %s", childTwoCommerceCode));
        logger.info(String.format("child_one_buy_order : %s", chileOneBuyOrder));
        logger.info(String.format("child_two_buy_order : %s", chileTwoBuyOrder));
        logger.info(String.format("amount_mall_one : %s", amountMallOne));
        logger.info(String.format("amount_mall_two : %s", amountMallTwo));

        // clean model
        cleanModel();

        // add request to storage in order to send them to the view
        addRequest("buy_order", buyOrder);
        addRequest("child_one_commerce_code", childOneCommerceCode);
        addRequest("child_two_commerce_code", childTwoCommerceCode);
        addRequest("child_one_buy_order", chileOneBuyOrder);
        addRequest("child_two_buy_order", chileTwoBuyOrder);
        addRequest("amount_mall_one", amountMallOne);
        addRequest("amount_mall_two", amountMallTwo);

        try {
            final OneclickMallTransactionRefundResponse response = OneclickMall.Transaction.refund(buyOrder, childOneCommerceCode, chileOneBuyOrder, amountMallOne);
            logger.info(String.format("response : %s", response));
            OneclickMall.Transaction.refund(buyOrder, childTwoCommerceCode, chileTwoBuyOrder, amountMallTwo);

            if (null != response) {
                addModel("response", response);
            }
        }catch (TransactionRefundException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-show-status-form", "model", getModel());
    }
}
