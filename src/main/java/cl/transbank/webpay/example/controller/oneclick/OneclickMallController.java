package cl.transbank.webpay.example.controller.oneclick;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.exception.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

@Controller
@RequestMapping("/oneclick-mall")
public class OneclickMallController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OneclickMallController.class);

    @Getter(AccessLevel.PRIVATE) private String username = "goncafa";
    @Getter(AccessLevel.PRIVATE) private String email = "gonzalo.castillo@continuum.cl";
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private String userTbkToken;
    @Getter(AccessLevel.PRIVATE) private String mallOneCommerceCode = IntegrationCommerceCodes.ONECLICK_MALL_CHILD1;
    @Getter(AccessLevel.PRIVATE) private String mallTwoCommerceCode = IntegrationCommerceCodes.ONECLICK_MALL_CHILD2;

    private Oneclick.MallInscription inscription;
    private Oneclick.MallTransaction tx;
    public OneclickMallController(){
        prepareLoger(Level.ALL);
        tx = new Oneclick.MallTransaction(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
        inscription = new Oneclick.MallInscription(new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST));
    }


    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {
        log.info("OneclickMall.Inscription.start");
        Map<String, Object> details = new HashMap<>();
        // clean model

        String responseUrl = request.getRequestURL().toString().replace("/start", "/finish");
        log.info(String.format("responseUrl : %s", responseUrl));

        // add request to storage in order to send them to the view
        details.put("username", getUsername());
        details.put("email", getEmail());
        details.put("responseUrl", responseUrl);

        Map<String, Object> req = new HashMap<>();
        req.put("username", username);
        req.put("email", email);
        req.put("responseUrl", responseUrl);

        details.put("req", toJson(req));

        try {
            // call the SDK
            final OneclickMallInscriptionStartResponse response = inscription.start(getUsername(), getEmail(), responseUrl);
            log.info(String.format("response : %s", response));

            details.put("tbk_token", response.getToken());
            details.put("url_webpay", response.getUrlWebpay());

            details.put("resp", toJson(response));

        }
        catch (InscriptionStartException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-start-inscription-form", "details", details);
    }

    @RequestMapping(value = "/finish", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView finish(@RequestParam("TBK_TOKEN") String token) {
        log.info("OneclickMall.Inscription.finish");
        log.info(String.format("TBK_TOKEN : %s", token));
        Map<String, Object> details = new HashMap<>();
        setUserTbkToken(token);

        Map<String, Object> req = new HashMap<>();
        req.put("token", token);

        details.put("req", toJson(req));

        try {
            final OneclickMallInscriptionFinishResponse response = inscription.finish(token);
            log.info(String.format("response : %s", response));

            if (null != response) {
                details.put("response", response);

                // add necesary data to make form works
                details.put("tbk_user", response.getTbkUser());
                details.put("username", getUsername());
            }

            details.put("resp", toJson(response));
        }
        catch (InscriptionFinishException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-authorize-payment-form", "details", details);
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public ModelAndView authorize(@RequestParam("username") String username,
                                  @RequestParam("tbk_user") String tbkUser,
                                  @RequestParam("amount") double amount) {
        log.info("OneclickMall.Transaction.authorize");
        log.info(String.format("username : %s", username));
        log.info(String.format("tbk_user : %s", tbkUser));
        Map<String, Object> details = new HashMap<>();
        // clean model
        cleanModel();

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amount, getMallOneCommerceCode(), buyOrderMallOne, (byte) 1)
                .add(amount, getMallTwoCommerceCode(), buyOrderMallTwo, (byte) 1);

        // add request to storage in order to send them to the view
        addRequest("buyOrder", buyOrder);
        addRequest("buyOrderMallOne", buyOrderMallOne);
        addRequest("buyOrderMallTwo", buyOrderMallTwo);
        addRequest("details", mallDetails);

        Map<String, Object> req = new HashMap<>();
        req.put("username", username);
        req.put("tbkUser", tbkUser);
        req.put("buyOrder", buyOrder);
        req.put("details", mallDetails);

        details.put("req", toJson(req));

        try {
            final OneclickMallTransactionAuthorizeResponse response = tx.authorize(username, tbkUser, buyOrder, mallDetails);
            log.info(String.format("response : %s", response));

            if (null != response) {
                details.put("response", response);

                details.put("buyOrder", buyOrder);
                details.put("childOneCommerceCode", getMallOneCommerceCode());
                details.put("childTwoCommerceCode", getMallTwoCommerceCode());
                details.put("chileOneBuyOrder", buyOrderMallOne);
                details.put("chileTwoBuyOrder", buyOrderMallTwo);
                details.put("amountMallOne", amount);
                details.put("amountMallTwo", amount);
            }

            details.put("resp", toJson(response));
        }
        catch (TransactionAuthorizeException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-refund-payment-form", "details", details);
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public ModelAndView refund(@RequestParam("buy_order") String buyOrder,
                               @RequestParam("child_commerce_code") String childCommerceCode,
                               @RequestParam("child_buy_order") String childBuyOrder,
                               @RequestParam("amount") double amount) {
        log.info("OneclickMall.Transaction.refund");
        log.info(String.format("buy_order : %s", buyOrder));
        log.info(String.format("child_commerce_code : %s", childCommerceCode));
        log.info(String.format("child_buy_order : %s", buyOrder));
        log.info(String.format("amount : %s", amount));

        Map<String, Object> details = new HashMap<>();
        // clean model
        cleanModel();

        // add request to storage in order to send them to the view
        addRequest("buy_order", buyOrder);
        addRequest("child_commerce_code", childCommerceCode);
        addRequest("child_buy_order", childBuyOrder);
        addRequest("amount", amount);

        Map<String, Object> req = new HashMap<>();
        req.put("buyOrder", buyOrder);
        req.put("childCommerceCode", childCommerceCode);
        req.put("childBuyOrder", childBuyOrder);
        req.put("amount", amount);

        details.put("req", toJson(req));

        try {
            final OneclickMallTransactionRefundResponse response = tx.refund(buyOrder, childCommerceCode, childBuyOrder, amount);
            log.info(String.format("response : %s", response));

            if (null != response) {
                addModel("response", String.format("response : %s", response));
            }
            details.put("resp", toJson(response));
        }
        catch (TransactionRefundException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("oneclick/oneclick-mall-end", "details", details);
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
        log.info("OneclickMall.Transaction.status");
        log.info(String.format("buy_order : %s", buyOrder));

        cleanModel();

        addRequest("buy_order", buyOrder);
        try {
            final OneclickMallTransactionStatusResponse response = tx.status(buyOrder);
            if (null != response) {
                String message = String.format("response : %s", response);
                log.info(message);
                addModel("response", message);
            }
        }catch (TransactionStatusException | IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("oneclick/oneclick-mall-status-request", "model", getModel());
    }

    @RequestMapping(value = "/inscription-delete", method = RequestMethod.GET)
    public ModelAndView inscriptionDelete(){
        log.info("OneclickMall.Inscription.delete");
        log.info(String.format("username : %s", getUsername()));
        log.info(String.format("tbk_token : %s", getUserTbkToken()));

        cleanModel();

        addRequest("username", getUsername());
        addRequest("tbk_token", getUserTbkToken());

        try {
            inscription.delete(getUsername(), getUserTbkToken());
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return new ModelAndView("oneclick/oneclick-mall-inscription-delete", "model", getModel());
    }
}
