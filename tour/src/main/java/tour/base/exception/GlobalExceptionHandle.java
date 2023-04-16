package tour.base.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tour.base.utils.ApiResponse;

/**
 * @author zhdpzz
 * @description:
 * @date 2023年04月03日 17:22
 */
@RestControllerAdvice
public class GlobalExceptionHandle{
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse doException(RuntimeException e){
        return new ApiResponse("401",e.getMessage());
    }
}
