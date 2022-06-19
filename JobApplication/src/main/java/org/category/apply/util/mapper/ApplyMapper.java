package org.category.apply.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.category.apply.model.Apply;
import org.category.apply.to.ApplyTo;

import java.util.List;

@Mapper(componentModel="spring")
public interface ApplyMapper {

    @Mapping(target = "userId", source = "apply.user.id")
    @Mapping(target = "categoryId", source = "apply.category.id")
    ApplyTo toTo(Apply apply);

    List<ApplyTo> getToList(List<Apply> applied);
}
