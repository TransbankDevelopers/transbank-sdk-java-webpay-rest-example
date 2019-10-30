package cl.transbank.webpay.example;

import lombok.ToString;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "transbank")
@ToString
public class ConfigProperties  {

    private @Setter @Getter String webpayplusCommerceCode;
    private @Setter @Getter  String webpayplusApiKey;

    private @Setter @Getter String webpayplusMallCommerceCode;
    private @Setter @Getter  String webpayplusMallApiKey;

    private @Setter @Getter String oneclickMallCommerceCode;
    private @Setter @Getter  String oneclickMallApiKey;

    private @Setter @Getter String patpassComercioCommerceCode;
    private @Setter @Getter  String patpassComercioApiKey;

    private @Setter @Getter String fullTransactionCommerceCode;
    private @Setter @Getter  String fullTransactionApiKey;


}