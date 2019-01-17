package com.hibicode.beerstore.error;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
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
 *
 * @author s2it_rboni
 * @version $Revision: $<br/>
 * @since 21/11/18 17:10
 */

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
  
  private static final String NO_MESSAGE_AVALIABLE = "No message avaliable";

  private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

  // injeção através do nome do atributo igual ao nome da class
  private final MessageSource apiErrorMessageSource;

  /**
   * método para tratamento de parâmetros inválidos recebidos nos requests
   * @param exception
   * @param locale no request http se for informado o param "Accept-Language" o param locale recebe esta informação, p/ então ler
   *              o arquivo api_errors_idioma correspondente
   * @return ResponseEntity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception,
                                                                             final Locale locale) {
    // stream de erros de validação
    final Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

    // getdefaultmessage = msg indicada nas anotações das validações nas entidades
    final List<ErrorResponse.ApiError> apiErrors = errors
        .map(ObjectError::getDefaultMessage)
        .map(code -> toApiError(code, locale, exception, false))
        .collect(Collectors.toList());

    final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
    
    return ResponseEntity.badRequest().body(errorResponse);
  }

  /**
   * A partir da chave / código do erro obter mensagem de erro, de acordo com locale
   * Utiliza bundle de mensagens
   * Dispara log também p/ monitoramento
   *
   * @param code
   * @param locale
   * @param exception
   * @param showException
   * @return ErrorResponse.ApiError
   */
  private ErrorResponse.ApiError toApiError(final String code, final Locale locale, final Exception exception,
                                            final boolean showException) {
    String message;
    try {
      message = apiErrorMessageSource.getMessage(code, null, locale);
      final String logMsg = (!Strings.isNullOrEmpty(code) ? code : "") + " " + (!Strings.isNullOrEmpty(message) ? message : NO_MESSAGE_AVALIABLE);

      if(showException) {
        LOG.error(logMsg, exception);
      } else {
        LOG.warn(logMsg);
      }
    } catch (NoSuchMessageException e) {
      // se não encontrar msg lançar log e retornar msg default
      LOG.error("Could not find any message for {} code under {} locale", code, locale);
      message = NO_MESSAGE_AVALIABLE;
    }
    
    return new ErrorResponse.ApiError(code, message);
  }
}
