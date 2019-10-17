package cl.transbank.webpay.example;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        //WebpayPlus Live config
        WebpayPlus.Transaction.setCommerceCode("597034919178");
        WebpayPlus.Transaction.setApiKey("2A20B03296717F18C349B2EEDABC6FD4FBBB41E26E772C30BFA4E9B30061AE6E");
        WebpayPlus.Transaction.setIntegrationType(IntegrationType.LIVE);

        //WebpayPlus.MallTransaction Live config
        WebpayPlus.MallTransaction.setCommerceCode("34925933");
        WebpayPlus.MallTransaction.setApiKey("C60B616633B57B9D0ACB346E9F3F18B414B702A2FE1172DEDC2F5E16EB9FB433");
        WebpayPlus.MallTransaction.setIntegrationType(IntegrationType.LIVE);

        // OneclickMall Live Config
        OneclickMall.setCommerceCode("597034926328");
        OneclickMall.setApiKey("C60B616633B57B9D0ACB346E9F3F18B414B702A2FE1172DEDC2F5E16EB9FB433");
        OneclickMall.setIntegrationType(IntegrationType.LIVE);

        //PatpassComercio Live Config
        PatpassComercio.setCommerceCode("28299257");
        PatpassComercio.setApiKey("cxxXQgGD9vrVe4M41FIt");
        PatpassComercio.setIntegrationType(IntegrationType.LIVE);

        //FullTransaction Live Config
        FullTransaction.Transaction.setCommerceCode("597034925658");
        FullTransaction.Transaction.setApiKey("6E55588C0ECC5507DDE234738FB130F79AD4CD9FD8B875911963D990F7342A8D");
        FullTransaction.Transaction.setIntegrationType(IntegrationType.LIVE);

        SpringApplication.run(Application.class, args);
    }
}
