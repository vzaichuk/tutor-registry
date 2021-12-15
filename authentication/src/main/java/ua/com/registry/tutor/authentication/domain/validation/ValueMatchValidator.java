package ua.com.registry.tutor.authentication.domain.validation;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ValueMatchValidator implements ConstraintValidator<ValueMatch, Object> {

  private String property;
  private String propertyToMatch;

  public void initialize(ValueMatch constraintAnnotation) {
    this.property = constraintAnnotation.property();
    this.propertyToMatch = constraintAnnotation.propertyToMatch();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {

    Object propertyValue = new BeanWrapperImpl(value).getPropertyValue(property);
    Object propertyToMatchValue = new BeanWrapperImpl(value).getPropertyValue(propertyToMatch);

    if (Objects.nonNull(propertyValue)) {
      return propertyValue.equals(propertyToMatchValue);
    } else {
      return Objects.isNull(propertyToMatchValue);
    }
  }
}
