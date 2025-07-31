package com.sys4business.sys4mech.models.predicate;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.sys4business.sys4mech.models.QSupplier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetSupplierPredicate {

    private Optional<String> name;
    private Optional<String> email;
    private Optional<String> city;

    public BooleanBuilder toPredicate() {
        QSupplier qSupplier = QSupplier.supplier;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // Apply filters if present
        name.ifPresent(val -> booleanBuilder.and(qSupplier.name.containsIgnoreCase(val)));
        email.ifPresent(val -> booleanBuilder.and(qSupplier.email.containsIgnoreCase(val)));
        city.ifPresent(val -> booleanBuilder.and(qSupplier.city.containsIgnoreCase(val)));
        return booleanBuilder;
    }
    
}
