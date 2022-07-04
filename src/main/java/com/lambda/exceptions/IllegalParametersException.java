package com.lambda.exceptions;

/**
 * THrown if the lambda handler can't find proper parameters in request.
 *
 * @author oklimenko@gmail.com
 */
public class IllegalParametersException extends RuntimeException {

    public IllegalParametersException(String reason) {
        super(reason);
    }
}
