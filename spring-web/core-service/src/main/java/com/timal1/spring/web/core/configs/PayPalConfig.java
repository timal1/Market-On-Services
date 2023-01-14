package com.timal1.spring.web.core.configs;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class PayPalConfig {

  //  @Value("{paypal.client-id}")
    private String clientId = "ATSgMOiFZFDO9WAy1rTOa3dikMfCsQ4sRCW3grUnPLBluJRhjc_xtPhvelltPt1VwPE8mk3v_xbuVkw-";

  //  @Value("{paypal.client-secret}")
    private String secret = "EEqCP_t2SFlFRx4GIwDU4v9JKivW8a2O9GF9s5EXBwm27JiJSTBKFnJmovSOzPsALF4XI2VbPOvOwmqs";

    private PayPalEnvironment environment;

    @PostConstruct
    private void init() {
        this.environment = new PayPalEnvironment.Sandbox(clientId, secret);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient() {
        return new PayPalHttpClient(environment);
    }
}
