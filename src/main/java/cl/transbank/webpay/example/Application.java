package cl.transbank.webpay.example;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    ConfigProperties config;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run (String... arg0) throws Exception{
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
