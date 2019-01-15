package com.hibicode.beerstore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Classe de configuração para definir arquivo com mensagens de erro
 * injeção através do nome do atributo igual ao nome da class
 *
 * @author s2it_rboni
 * @version $Revision: $<br/>
 * $Id: $
 * @since 21/11/18 17:06
 */

@Configuration
public class ApiErrorConfig {
  
  public MessageSource apiErrorMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/api_errors");
    messageSource.setDefaultEncoding("UTF-8");
    
    return messageSource;
  }
}
