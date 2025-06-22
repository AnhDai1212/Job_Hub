package java_spring.job_hub.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameConstrain.class)
public @interface UsernameConstraint {
    String message() default "INVALID_USERNAME";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
