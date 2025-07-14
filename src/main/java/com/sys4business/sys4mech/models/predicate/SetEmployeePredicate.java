package com.sys4business.sys4mech.models.predicate;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.sys4business.sys4mech.models.QEmployee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetEmployeePredicate {

    private Optional<String> name;
    private Optional<String> email;
    private Optional<String> city;

    public BooleanBuilder toPredicate() {
        QEmployee qEmployee = QEmployee.employee;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // Apply filters if present
        name.ifPresent(val -> booleanBuilder.and(qEmployee.name.containsIgnoreCase(val)));
        email.ifPresent(val -> booleanBuilder.and(qEmployee.email.containsIgnoreCase(val)));
        city.ifPresent(val -> booleanBuilder.and(qEmployee.city.containsIgnoreCase(val)));
        return booleanBuilder;
    }
    
}
