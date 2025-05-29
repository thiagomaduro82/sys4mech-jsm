package com.sys4business.sys4mech.models.predicate;

import java.util.Optional;

import com.querydsl.core.BooleanBuilder;
import com.sys4business.sys4mech.models.QRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SetRolePredicate {
    
    private Optional<String> uuid;
    private Optional<String> name;

    public BooleanBuilder toPredicate() {
        QRole qRole = QRole.role;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        uuid.ifPresent(val -> booleanBuilder.and(qRole.uuid.like("%" + val + "%")));
        name.ifPresent(val -> booleanBuilder.and(qRole.name.like("%" + val + "%")));
        return booleanBuilder;
    }
}
