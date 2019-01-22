package com.hibicode.beerstore.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

// anotação para controlar ordem de carregamento de classes com mesma função, como controller advice
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GeneralExceptionHandler {
	
	private static final String GENERIC_MESSAGE_SERVER_ERROR = "generic-server-error";
	
	private static final Logger LOG = LoggerFactory.getLogger(GeneralExceptionHandler.class);
	
	@Autowired
	private ApiExceptionHandler apiExceptionHandler;
	
	/**
	 * Tratamento genérico de erros, para não retornar mensagem expondo dados da aplicação
	 *
	 * Este método foi colocado em classe diferente da ApiExceptionHandler para permitir carregamento
	 * com prioridade menor - a ordem default do spring permite que Exception (mais genérico) seja
	 * carregado antes das exceptions mais específicas - desta forma o tratamento de erros não cai
	 * nos blocos específicos de cada tipo de exceção.
	 *
	 *
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(final Exception exception, final Locale locale) {
		final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
				apiExceptionHandler.toApiError(GENERIC_MESSAGE_SERVER_ERROR, locale));
		
		LOG.error(errorResponse.toString(), exception);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
