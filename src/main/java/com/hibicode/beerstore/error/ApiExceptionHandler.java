package com.hibicode.beerstore.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hibicode.beerstore.error.exceptions.BusinessException;
import com.hibicode.beerstore.error.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe para capturar exceções e aplicar tratamento
 */

// anotação para controlar ordem de carregamento de classes com mesma função, como controller advice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor // p/ lombok criar construtor que recebe os atributos não iniciados (apierrormessagesource)
public class ApiExceptionHandler {
	
	// constantes para mensagens genéricas
	private static final String NO_MESSAGE_AVALIABLE = "No message avaliable";
	private static final String GENERIC_MESSAGE_INVALID_VALUE = "generic-invalid-value";
	
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	// injeção através do nome do atributo igual ao nome da class
	private final MessageSource apiErrorMessageSource;
	
	/**
	 * método para tratamento de parâmetros inválidos recebidos nos requests
	 *
	 * @param locale    no request http se for informado o param "Accept-Language" o param locale recebe esta informação, p/ então ler
	 *                  o arquivo api_errors_idioma correspondente
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception,
																			   final Locale locale) {
		// stream de erros de validação
		final Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		
		// getdefaultmessage = msg indicada nas anotações das validações nas entidades
		final List<ErrorResponse.ApiError> apiErrors = errors
				.map(ObjectError::getDefaultMessage)
				.map(code -> toApiError(code, locale))
				.collect(Collectors.toList());
		
		final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
		
		LOG.warn(errorResponse.toString(), exception);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	/**
	 * Método para tratar erros de atributos informados com valores inválidos
	 * (attr baseado em enum c/ valor informado inexistente por exemplo)
	 */
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(final InvalidFormatException exception,
	                                                                  final Locale locale) {
		// passa o valor inválido (exception.getValue) como parâmetro para substituir na mensagem de erro
		final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST,
				this.toApiError(GENERIC_MESSAGE_INVALID_VALUE, locale, exception.getValue()));
		
		LOG.warn(errorResponse.toString(), exception);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception,
																 final Locale locale) {
		final ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), toApiError(exception.getCode(),
                locale ));
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	/**
	 * A partir da chave / código do erro obter mensagem de erro, de acordo com locale
	 * Utiliza bundle de mensagens
	 * Dispara log também p/ monitoramento
	 */
	public ErrorResponse.ApiError toApiError(final String code, final Locale locale, final Object... args) {
		String message;
		try {
			message = apiErrorMessageSource.getMessage(code, args, locale);
		} catch (final NoSuchMessageException e) {
			// se não encontrar msg lançar log e retornar msg default
			LOG.error("Could not find any message for {} code under {} locale", code, locale);
			message = NO_MESSAGE_AVALIABLE;
		}
		
		return new ErrorResponse.ApiError(code, message);
	}
}
