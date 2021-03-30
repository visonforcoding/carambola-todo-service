package xyz.visonforcoding.carambola;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.orm.hibernate5.HibernateQueryException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.visonforcoding.wonfu.spring.boot.starter.Response;
import xyz.visonforcoding.wonfu.spring.boot.starter.ResponseRet;
import xyz.visonforcoding.wonfu.spring.boot.starter.config.Log;

/**
 *
 * @author vison.cao <visonforcoding@gmail.com>
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleConstraintViolationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new Response(ResponseRet.parametrErrror, "参数错误", errors);
    }

    /**
     * 违反约束异常 字段不为空等
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handHibernateException(ConstraintViolationException ex) {
        return new Response(ResponseRet.dbExecuteFail, ex.getSQLException().toString());
    }

    @ExceptionHandler(GenericJDBCException.class)
    public Response handGenericJDBCException(GenericJDBCException ex) {
        return new Response(ResponseRet.dbExecuteFail, ex.getSQLException().toString());
    }

}
