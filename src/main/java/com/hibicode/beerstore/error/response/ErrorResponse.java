package com.hibicode.beerstore.error.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Bean para retornar erros
 */
// anotação do jackson para definir que deve considerar propriedades com qualquer visibilidade - neste caso as privadas
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
// lombok c/ modificador de escopo do construtor
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
  private final int statusCode;
  private final List<ApiError> errors;

  /**
   * Builder para retornar erro a partir da lista de erros + http status
   * @param status
   * @param errors
   * @return
   */
  public static ErrorResponse of(final HttpStatus status, final List<ApiError> errors) {
    return new ErrorResponse(status.value(), errors);
  }

  /**
   * Builder para retornar erro + http status
   * @param status
   * @param error
   * @return
   */
  public static ErrorResponse of(final HttpStatus status, final ApiError error) {
    return new ErrorResponse(status.value(), Collections.singletonList(error));
  }
  
  @Override
  public String toString() {
    return "ErrorResponse{" +
        "statusCode=" + statusCode +
        ", errors=" + errors +
        '}';
  }
  
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  @RequiredArgsConstructor
  public static class ApiError {
    private final String code;
    private final String message;
  
    @Override
    public String toString() {
      return "ApiError{" +
          "code='" + code + '\'' +
          ", message='" + message + '\'' +
          '}';
    }
  }
}
