package java_spring.job_hub.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {

    private int min;


    @Override
    public void initialize(DobConstraint constraintAnnotation) {   // xy ly data co dung hay khong, Se dien ra truoc, lay gia tri min tu anotation DobConstraint
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) { // khoi tao khi constraint khoi tao. de get gia tri lay vao
        if(Objects.isNull(value)){
            return true;
        }
        long years = ChronoUnit.YEARS.between(value, LocalDate.now());

        return years >= min;
    }
}
