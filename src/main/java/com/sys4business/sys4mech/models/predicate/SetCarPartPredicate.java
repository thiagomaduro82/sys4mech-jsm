package com.sys4business.sys4mech.models.predicate;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.sys4business.sys4mech.models.QCarPart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetCarPartPredicate {

    private Optional<String> name;
    private Optional<String> description;

    public BooleanBuilder toPredicate() {
        QCarPart qCarPart = QCarPart.carPart;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        name.ifPresent(val -> booleanBuilder.and(qCarPart.name.containsIgnoreCase(val)));
        description.ifPresent(val -> booleanBuilder.and(qCarPart.description.containsIgnoreCase(val)));
        return booleanBuilder;
    }
    
}
