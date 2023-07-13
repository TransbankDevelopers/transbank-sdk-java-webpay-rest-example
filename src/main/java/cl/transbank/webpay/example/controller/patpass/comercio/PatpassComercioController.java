package cl.transbank.webpay.example.controller.patpass.comercio;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.patpass.responses.*;
import cl.transbank.webpay.example.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value = "/patpass_comercio")
public class PatpassComercioController extends BaseController {
    private PatpassComercio.Inscription inscription;
    public PatpassComercioController(){
        inscription = new PatpassComercio.Inscription(new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO, IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.TEST));
    }
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ModelAndView start(HttpServletRequest request) {
        Map<String, Object> details = new HashMap<>();

        String returnUrl = request.getRequestURL().toString().replace("start","commit");
        String name = "Isaac";
        String lastName = "Newton";
        String secondLastName = "Gonzales";
        String rut = "11111111-1";
        String serviceId = "Service_" + getRandomNumber();
        String finalUrl = request.getRequestURL().toString().replace("start","voucher_return");
        Double maxAmount = null;
        String phone = "123456734";
        String cellPhone = "123456723";
        String patpassName = "Membresia de cable";
        String personEmail = "developer@continuum.cl";
        String commerceEmail = "developer@continuum.cl";
        String address = "huerfanos 101";
        String city = "Santiago";

        try {
            final PatpassComercioInscriptionStartResponse response = inscription.start(
                    returnUrl,
                    name,
                    lastName,
                    secondLastName,
                    rut,
                    serviceId,
                    finalUrl,
                    maxAmount,
                    phone,
                    cellPhone,
                    patpassName,
                    personEmail,
                    commerceEmail,
                    address,
                    city);

            details.put("returnUrl", returnUrl);
            details.put("name", name);
            details.put("firstLastName", lastName);
            details.put("secondLastName", secondLastName);
            details.put("rut", rut);
            details.put("serviceId", serviceId);
            details.put("finalUrl", finalUrl);
            details.put("maxAmount", maxAmount);
            details.put("phoneNumber", phone);
            details.put("mobileNumber", cellPhone);
            details.put("patpassName", patpassName);
            details.put("personEmail", personEmail);
            details.put("commerceEmail", commerceEmail);
            details.put("address", address);
            details.put("city", city);

            details.put("response", response);
            details.put("tbk_token", response.getToken());
            details.put("url_webpay", response.getUrl());
            details.put("resp", toJson(response));
        }  catch (Exception e) {
            e.printStackTrace();
            //log.info("ERROR", e);
            //details.put("resp", e.getMessage());
        }

        return new ModelAndView("patpass_comercio/start", "details", details);
    }
    @RequestMapping(value= "/commit", method = RequestMethod.POST)
    public ModelAndView commit(@RequestParam("j_token") String token){
        Map<String, Object> details = new HashMap<>();
        try {
            final PatpassComercioTransactionStatusResponse response = inscription.status(token);
            details.put("response", response);
            details.put("token", token);
            details.put("url_voucher", response.getVoucherUrl());
            details.put("resp", toJson(response));
        } catch (Exception e) {
            log.error("ERROR", e);
            details.put("resp", e.getMessage());
        }
        return new ModelAndView("patpass_comercio/commit", "details", details);
    }

    @RequestMapping(value = {"/voucher_return"}, method = RequestMethod.POST)
    public ModelAndView voucherReturn(
            @RequestParam("j_token") String token,
            HttpServletRequest request) {
        Map<String, Object> details = new HashMap<>();
        details.put("token", token);
        details.put("url_voucher", "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenVoucherLogin");
        return new ModelAndView("patpass_comercio/voucher-return", "details", details);
    }
}
