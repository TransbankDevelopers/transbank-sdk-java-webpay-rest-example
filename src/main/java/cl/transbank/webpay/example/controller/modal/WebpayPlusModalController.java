package cl.transbank.webpay.example.controller.modal;

import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.example.controller.ErrorController;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.modal.WebpayPlusModal;
import cl.transbank.webpay.modal.responses.ModalTransactionCreateResponse;
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
import java.util.logging.Level;

@Controller
public class WebpayPlusModalController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(WebpayPlusModalController.class);
    private WebpayPlusModal.Transaction tx;

    public WebpayPlusModalController(){
        prepareLoger(Level.ALL);
        tx = new WebpayPlusModal.Transaction();
    }

    @RequestMapping(value = "/modal/webpayplus-modal", method = RequestMethod.GET)
    public ModelAndView webpayplus(HttpServletRequest request) {
        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;

        Map<String, Object> details = new HashMap<>();

        Map<String, Object> req = new HashMap<>();
        req.put("buyOrder", buyOrder);
        req.put("sessionId", sessionId);
        req.put("amount", amount);

        details.put("req", toJson(req));
        try {
            final ModalTransactionCreateResponse response = tx.create(buyOrder, sessionId, amount);
            details.put("token", response.getToken());

            details.put("resp", toJson(response));
        }
        catch (TransactionCreateException e) {
            log.error(e.getLocalizedMessage(), e);
            details.put("resp", e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return new ErrorController().error();
        }

        return new ModelAndView("/modal/webpayplus-modal", "details", details);
    }

    @RequestMapping(value = {"/webpayplus-modal-end"}, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView webpayplusEnd(@RequestParam("token_ws") String tokenWs, HttpServletRequest request) {
        log.info(String.format("token_ws : %s", tokenWs));

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusTransactionCommitResponse response = tx.commit(tokenWs);
            log.debug(String.format("response : %s", response));
            details.put("response", response);
            details.put("refund-endpoint", request.getRequestURL().toString().replace("-end", "-refund"));

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
        return new ModelAndView("webpayplus-end", "details", details);
    }

    @RequestMapping(value = "/webpayplus-modal-refund", method = RequestMethod.POST)
    public ModelAndView webpayplusRefund(@RequestParam("token_ws") String tokenWs,
                                         @RequestParam("amount") double amount,
                                         HttpServletRequest request) {
        log.info(String.format("token_ws : %s | amount : %s", tokenWs, amount));
        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", tokenWs);

        try {
            final WebpayPlusTransactionRefundResponse response = tx.refund(tokenWs, amount);
            details.put("response", response);
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

        return new ModelAndView("webpayplus-refund", "details", details);
    }

    @RequestMapping(value = {"/webpayplus-modal-refund-form", "/webpayplusdeferred-refund-form"}, method = RequestMethod.GET)
    public ModelAndView webpayplusRefundForm(HttpServletRequest request) {
        String refundEndpoint = request.getRequestURL().toString().endsWith("webpayplusdeferred-refund-form") ?
                "/webpayplusdeferred-refund" : "/webpayplus-refund";
        Map<String, Object> details = new HashMap<>();
        details.put("refund-endpoint", refundEndpoint);

        return new ModelAndView("webpayplus-refund-form", "details", details);
    }


    @RequestMapping(value = {"/webpayplus-modal-status-form","/webpayplusmall-status-form", "/webpayplusdeferred-status-form"}, method = RequestMethod.GET)
    public ModelAndView webpayplusStatusForm(HttpServletRequest request){
        String endpoint = request.getRequestURL().toString().replace("-form", "");
        addModel("endpoint", endpoint);
        return new ModelAndView("webpayplus-status-form", "model", getModel());
    }

    @RequestMapping(value= "/webpayplus-modal-status", method = RequestMethod.POST)
    public ModelAndView webpayplusStatus(@RequestParam("token_ws") String token){

        Map<String, Object> details = new HashMap<>();
        details.put("token_ws", token);
        try {
            final WebpayPlusTransactionStatusResponse response = tx.status(token);
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
