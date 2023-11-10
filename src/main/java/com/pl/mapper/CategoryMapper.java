package com.pl.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public Category mapCategoryFromDto(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.name(),
                            categoryDTO.image());
    }
    public CategoryDTO mapCategoryToDto(Category category){
        return  new CategoryDTO(category.getName(), category.getImages());
    }

    public List<CategoryDTO>mapCategoryToListDto(final  List<Category> categories){
        return categories.stream()
                .map(this::mapCategoryToDto)
                .toList();
    }




}
