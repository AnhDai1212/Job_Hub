package java_spring.job_hub.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {}
)
public @interface DobConstraint {
    String message() default "Invalid date of birth";
    int min();
    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
