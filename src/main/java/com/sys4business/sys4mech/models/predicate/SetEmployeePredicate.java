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
        name.ifPresent(val -> qEmployee.name.like("%" + val + "%"));
        email.ifPresent(val -> qEmployee.email.like("%" + val + "%"));
        city.ifPresent(val -> qEmployee.city.like("%" + val + "%"));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        return booleanBuilder;
    }
    
}
