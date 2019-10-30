package cl.transbank.webpay.example;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

    @Autowired
    ConfigProperties config;

    public static void main(String[] args) {

        System.out.println("ENV: " + System.getenv());

        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterStart() throws Exception{

        System.out.println("Config: " + config);

        //WebpayPlus Live config
        WebpayPlus.Transaction.setCommerceCode(config.getWebpayplusCommerceCode());
        WebpayPlus.Transaction.setApiKey(config.getWebpayplusApiKey());
        WebpayPlus.Transaction.setIntegrationType(IntegrationType.LIVE);

        //WebpayPlus.MallTransaction Live config
        WebpayPlus.MallTransaction.setCommerceCode(config.getWebpayplusMallCommerceCode());
        WebpayPlus.MallTransaction.setApiKey(config.getWebpayplusApiKey());
        WebpayPlus.MallTransaction.setIntegrationType(IntegrationType.LIVE);

        // OneclickMall Live Config
        OneclickMall.setCommerceCode(config.getOneclickMallCommerceCode());
        OneclickMall.setApiKey(config.getOneclickMallApiKey());
        OneclickMall.setIntegrationType(IntegrationType.LIVE);

        //PatpassComercio Live Config
        PatpassComercio.setCommerceCode(config.getPatpassComercioCommerceCode());
        PatpassComercio.setApiKey(config.getPatpassComercioApiKey());
        PatpassComercio.setIntegrationType(IntegrationType.LIVE);

        //FullTransaction Live Config
        FullTransaction.Transaction.setCommerceCode(config.getFullTransactionCommerceCode());
        FullTransaction.Transaction.setApiKey(config.getFullTransactionApiKey());
        FullTransaction.Transaction.setIntegrationType(IntegrationType.LIVE);
    }

}
