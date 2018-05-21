package br.com.pmattiollo.limitsservice.controller;

import br.com.pmattiollo.limitsservice.bean.LimitConfiguration;
import br.com.pmattiollo.limitsservice.configuration.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    private Configuration configuration;

    public LimitsConfigurationController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfiguration() {
        return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }

    @GetMapping("fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallBackRetrieveConfiguration")
    public LimitConfiguration retrieveConfiguration() {
        throw new RuntimeException();
    }

    public LimitConfiguration fallBackRetrieveConfiguration() {
        return new LimitConfiguration(999, 9);
    }

}
