package com.hibicode.beerstore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Classe de configuração para definir arquivo com mensagens de erro
 * injeção através do nome do atributo igual ao nome da class
 */

@Configuration
public class ApiErrorConfig {
  
  @Bean
  public MessageSource apiErrorMessageSource() {
    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/api_errors");
    messageSource.setDefaultEncoding("UTF-8");
    
    return messageSource;
  }
}
