package org.category.apply.util.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.category.apply.model.Category;
import org.category.apply.to.CategoryTo;
import org.category.apply.to.CategoryWithDetailsTo;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    @Named(value = "to")
    CategoryTo toTo(Category category);

    Category toEntity(CategoryTo categoryTo);

    @Named(value = "toWith")
    CategoryWithDetailsTo toToWithDetails(Category category);

    @IterableMapping(qualifiedByName = "to")
    List<CategoryTo> getToList(List<Category> categories);

    @IterableMapping(qualifiedByName = "toWith")
    List<CategoryWithDetailsTo> getToWithDetailsList(List<Category> categories);
}
