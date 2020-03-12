package mostwanted.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;
//    private Set<ConstraintViolation> constraintViolations;

    @Autowired
    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
//        this.constraintViolations = new HashSet<>();
    }


    @Override
    public <E> boolean isValid(E entity) {
//        Set<ConstraintViolation<E>> validate = this.validator.validate(entity);
        return this.validator.validate(entity).size() == 0;
    }
}
