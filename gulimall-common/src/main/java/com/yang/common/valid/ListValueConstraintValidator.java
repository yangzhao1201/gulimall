package com.yang.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TangYuan
 * @create 2021--06--19--06:41
 * @description
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {

    private Set<Integer> set=new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for (int i : vals) {
            set.add(i);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        return  set.contains(value);
    }
}
