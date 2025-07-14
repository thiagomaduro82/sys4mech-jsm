package com.sys4business.sys4mech.models.predicate;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.sys4business.sys4mech.models.QCustomer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetCustomerPredicate {

    private Optional<String> name;
    private Optional<String> email;
    private Optional<String> city;

    public BooleanBuilder toPredicate() {
        QCustomer qCustomer = QCustomer.customer;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // Apply filters if present
        name.ifPresent(val -> booleanBuilder.and(qCustomer.name.containsIgnoreCase(val)));
        email.ifPresent(val -> booleanBuilder.and(qCustomer.email.containsIgnoreCase(val)));
        city.ifPresent(val -> booleanBuilder.and(qCustomer.city.containsIgnoreCase(val)));
        return booleanBuilder;
    }
    
}
