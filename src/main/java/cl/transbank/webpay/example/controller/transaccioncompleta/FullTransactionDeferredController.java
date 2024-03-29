package cl.transbank.webpay.example.controller.transaccioncompleta;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.example.controller.BaseController;
import cl.transbank.webpay.transaccioncompleta.FullTransaction;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
@RequestMapping(value = "/transaccion_completa_deferred")
public class FullTransactionDeferredController extends BaseController {

  private FullTransaction tx;

  public FullTransactionDeferredController() {
    tx =
      new FullTransaction(
        new WebpayOptions(
          IntegrationCommerceCodes.TRANSACCION_COMPLETA_DEFERRED,
          IntegrationApiKeys.WEBPAY,
          IntegrationType.TEST
        )
      );
  }

  @RequestMapping(value = "/form", method = RequestMethod.GET)
  public ModelAndView form(HttpServletRequest request) {
    double amount = 10000;
    Map<String, Object> details = new HashMap<>();
    details.put("create-endpoint", "/transaccion_completa_deferred/create");
    details.put("amount", amount);
    return new ModelAndView(
      "transaccion_completa_deferred/form",
      "details",
      details
    );
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public ModelAndView create(
    @RequestParam("card_number") String cardNumber,
    @RequestParam("cvv") int cvv,
    @RequestParam("year") String year,
    @RequestParam("month") String month,
    @RequestParam("amount") double amount,
    HttpServletRequest request
  ) {
    String buyOrder = "buyOrder_" + getRandomNumber();
    String sessionId = "sessionId_" + getRandomNumber();
    String cardExpirationDate = year + "/" + month;
    Map<String, Object> details = new HashMap<>();
    try {
      FullTransactionCreateResponse response = tx.create(
        buyOrder,
        sessionId,
        amount,
        (short) cvv,
        cardNumber,
        cardExpirationDate
      );
      details.put("resp", toJson(response));
      details.put("token", response.getToken());
      details.put("commit-endpoint", "/transaccion_completa_deferred/commit");
      details.put(
        "installments-endpoint",
        "/transaccion_completa_deferred/installments"
      );
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }

    return new ModelAndView(
      "transaccion_completa_deferred/create",
      "details",
      details
    );
  }

  @RequestMapping(value = { "/commit" }, method = RequestMethod.POST)
  public ModelAndView commit(
    @RequestParam("token") String token,
    HttpServletRequest request,
    @RequestParam(
      value = "id_query_installments",
      required = false
    ) Long idQueryInstallments,
    @RequestParam(
      value = "grace_period",
      required = false
    ) String gracePeriodCheck,
    @RequestParam(
      value = "deferred_period_index",
      required = false
    ) Byte deferredPeriodIndex
  ) {
    Boolean gracePeriod = gracePeriodCheck != null ? true : false;
    Map<String, Object> details = new HashMap<>();
    try {
      final FullTransactionCommitResponse response = tx.commit(
        token,
        idQueryInstallments,
        deferredPeriodIndex,
        gracePeriod
      );
      addDetailModelDeferred(
        response,
        token,
        response.getBuyOrder(),
        response.getAuthorizationCode(),
        response.getAmount(),
        details
      );
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }

    return new ModelAndView(
      "transaccion_completa_deferred/commit",
      "details",
      details
    );
  }

  @RequestMapping(value = "/installments", method = RequestMethod.POST)
  public ModelAndView installments(
    @RequestParam("token") String token,
    @RequestParam("installments") byte installments
  ) {
    Map<String, Object> details = new HashMap<>();
    try {
      final FullTransactionInstallmentResponse response = tx.installments(
        token,
        installments
      );
      details.put("response", response);
      details.put("token", token);
      details.put("id_query_installments", response.getIdQueryInstallments());
      details.put("resp", toJson(response));
      details.put("commit-endpoint", "/transaccion_completa_deferred/commit");
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }

    return new ModelAndView(
      "transaccion_completa_deferred/installments",
      "details",
      details
    );
  }

  @RequestMapping(value = { "/status" }, method = RequestMethod.POST)
  public ModelAndView status(
    @RequestParam("token_ws") String tokenWs,
    HttpServletRequest request
  ) {
    Map<String, Object> details = new HashMap<>();
    try {
      final FullTransactionStatusResponse response = tx.status(tokenWs);
      details.put("response", response);
      details.put("resp", toJson(response));
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }

    return new ModelAndView(
      "transaccion_completa_deferred/status",
      "details",
      details
    );
  }

  @RequestMapping(value = { "/refund" }, method = RequestMethod.POST)
  public ModelAndView refund(
    @RequestParam("token_ws") String tokenWs,
    @RequestParam("amount") double amount,
    HttpServletRequest request
  ) {
    Map<String, Object> details = new HashMap<>();
    try {
      final FullTransactionRefundResponse response = tx.refund(tokenWs, amount);
      details.put("response", response);
      details.put("resp", toJson(response));
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }
    return new ModelAndView(
      "transaccion_completa_deferred/refund",
      "details",
      details
    );
  }

  @RequestMapping(value = "/capture", method = RequestMethod.POST)
  public ModelAndView capture(
    @RequestParam("token_ws") String tokenWs,
    @RequestParam("buy_order") String buyOrder,
    @RequestParam("authorization_code") String authorizationCode,
    @RequestParam("amount") double amount,
    HttpServletRequest request
  ) {
    Map<String, Object> details = new HashMap<>();
    try {
      final FullTransactionCaptureResponse response = tx.capture(
        tokenWs,
        buyOrder,
        authorizationCode,
        amount
      );
      details.put("response", response);
      details.put("buy_order", buyOrder);
      details.put("token_ws", tokenWs);
      details.put("authorization_code", authorizationCode);
      details.put("amount", amount);
      details.put("refund-endpoint", "/transaccion_completa_deferred/refund");
      details.put("status-endpoint", "/transaccion_completa_deferred/status");
      details.put("resp", toJson(response));
    } catch (Exception e) {
      log.error("ERROR", e);
      details.put("resp", e.getMessage());
    }
    return new ModelAndView(
      "transaccion_completa_deferred/capture",
      "details",
      details
    );
  }

  private void addDetailModelDeferred(
    Object response,
    String tokenWs,
    String buyOrder,
    String authorizationCode,
    double amount,
    Map<String, Object> details
  ) {
    details.put("response", response);
    details.put("buy_order", buyOrder);
    details.put("token_ws", tokenWs);
    details.put("authorization_code", authorizationCode);
    details.put("amount", amount);
    details.put("capture-endpoint", "/transaccion_completa_deferred/capture");
    details.put("resp", toJson(response));
  }
}
