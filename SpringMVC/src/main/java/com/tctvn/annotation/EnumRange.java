package com.tctvn.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumRangeValidator.class)
@Documented
public @interface EnumRange {
    Class<? extends Enum<?>> enumClass();
    String message() default "{DateFormat}";
    String[] arg();
    
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}
