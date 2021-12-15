package ua.com.registry.tutor.authentication.domain.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

  String message() default "Password must consist of at least 1 lower case and upper case letters, 1 digit and be at least 8 symbols long";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
