package ua.com.registry.tutor.authentication.domain.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValueMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueMatch {

  String message() default "Fields values aren't equal";

  String property();

  String propertyToMatch();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    ValueMatch[] value();
  }
}
