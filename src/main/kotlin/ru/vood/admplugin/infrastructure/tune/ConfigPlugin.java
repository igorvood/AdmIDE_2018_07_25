package ru.vood.admplugin.infrastructure.tune;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

//@Configuration
@Component
@PropertySource("classpath:DBProperty.properties")
public class ConfigPlugin {
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
