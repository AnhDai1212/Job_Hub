package java_spring.job_hub.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameConstrain implements ConstraintValidator<UsernameConstraint, String> {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{4,20}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) return false;
        return username.matches(USERNAME_REGEX);
    }
}
