package cl.transbank.webpay.example.controller.patpass.comercio;

import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

        // clean model
        cleanModel();

        try {
            // call the SDK
            final PatpassComercioInscriptionStartResponse response = PatpassComercio.Inscription.start("https://www.comercio.com/urlretorno",
                    "nombre",
                    "apellido",
                    "sapellido",
                    "14959787-6",
                    "76",
                    "https://www.comercio.com/urlrfinal",
                    "28299257",
                    1500,
                    "012356545",
                    "99999999",
                    "nombre del patpass",
                    "persona@persona.cl",
                    "comercio@comercio.cl",
                    "huerfanos 101",
                    "Santiago");
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
}
