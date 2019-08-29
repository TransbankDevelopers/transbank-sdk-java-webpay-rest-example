package cl.transbank.webpay.example.controller.patpass.comercio;

import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.model.PatpassComercioTransactionStatusResponse;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
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
@RequestMapping("/patpass-comercio")
public class PatpassComercioController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PatpassComercioController.class);


    public PatpassComercioController() {
        prepareLoger(Level.ALL);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {
        logger.info("PatpassComercio.Inscription.start");

        String url = request.getRequestURL().toString().replace("start","end-subscription");
        String name = "nombre";
        String firstLastName = "apellido";
        String secondLastName = "sapellido";
        String rut = "14140066-5";
        String serviceId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));;
        String finalUrl = request.getRequestURL().toString().replace("start","voucher-generated");
        String commerceCode = "28299257";
        double maxAmount = 1500;
        String phoneNumber = "123456734";
        String mobileNumber = "123456723";
        String patpassName = "nombre del patpass";
        String personEmail = "otromail@persona.cl";
        String commerceEmail = "otrocomercio@comercio.cl";
        String address = "huerfanos 101";
        String city = "Santiago";


        // clean model
        cleanModel();
        addRequest("url", url);
        addRequest("name", name);
        addRequest("firstLastName", firstLastName);
        addRequest("secondLastName", secondLastName);
        addRequest("rut", rut);
        addRequest("serviceId", serviceId);
        addRequest("finalUrl", finalUrl);
        addRequest("commerceCode", commerceCode);
        addRequest("maxAmount", maxAmount);
        addRequest("phoneNumber", phoneNumber);
        addRequest("mobileNumber", mobileNumber);
        addRequest("patpassName", patpassName);
        addRequest("personEmail", personEmail);
        addRequest("commerceEmail", commerceEmail);
        addRequest("address", address);
        addRequest("city", city);

        try {
            // call the SDK
            final PatpassComercioInscriptionStartResponse response = PatpassComercio.Inscription.start(url,
                    name,
                    firstLastName,
                    secondLastName,
                    rut,
                    serviceId,
                    finalUrl,
                    commerceCode,
                    maxAmount,
                    phoneNumber,
                    mobileNumber,
                    patpassName,
                    personEmail,
                    commerceEmail,
                    address,
                    city);
            logger.info(String.format("response : %s", response));

            if (null != response) {
                // add response to model in order to send it to the view
                addModel("response", response);


                // add necesary data to make form works
                addModel("tbk_token", response.getToken());
                addModel("url_webpay", response.getUrl());
            }
        }  catch (InscriptionStartException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("patpasscomercio/patpass-comercio-start-inscription-form", "model", getModel());
    }

    @RequestMapping(value = {"/end-subscription"}, method = RequestMethod.POST)
    public ModelAndView webpayplusEnd(@RequestParam("j_token") String tokenWs, HttpServletRequest request) {
        logger.info(String.format("token_ws : %s", tokenWs));
        addModel("token_ws", tokenWs);

        return new ModelAndView("patpasscomercio/patpass-comercio-end-inscription-form", "model", getModel());
    }

    @RequestMapping(value= "/status", method = RequestMethod.POST)
    public ModelAndView webpayplusStatus(@RequestParam("tokenComercio") String token){
        cleanModel();
        addRequest("token_ws", token);
        try {
            final PatpassComercioTransactionStatusResponse response = PatpassComercio.Transaction.status(token);
            addModel("response", response);
            addModel("token", token);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return new ErrorController().error();
        }
        return new ModelAndView("patpasscomercio/patpass-comercio-status", "model", getModel());
    }

    @RequestMapping(value = {"/voucher-generated"}, method = RequestMethod.POST)
    public ModelAndView voucherGenerated( HttpServletRequest request) {
        return new ModelAndView("patpasscomercio/patpass-comercio-end-voucher", "model", getModel());
    }
}
