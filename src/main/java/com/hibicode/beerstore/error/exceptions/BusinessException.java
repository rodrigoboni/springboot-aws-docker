package com.hibicode.beerstore.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author s2it_rboni
 * @version $Revision: $<br/>
 * $Id: $
 * @since 24/01/19 14:10
 */
@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final String entity; // utilizado como parametro da mensagem de erro (é genérica, para indicar em que entidade ocorreu erro)
    private final HttpStatus status;
}
