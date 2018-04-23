package com.spices.api.dto;

import java.util.Objects;

public class CategoryResponseDto {
    private Long id;
    private Long parentCategoryId;
    private String name;
    private String description;

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(Long id, Long parentCategoryId, String name, String description) {
        this.id = id;
        this.parentCategoryId = parentCategoryId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryResponseDto)) return false;
        CategoryResponseDto that = (CategoryResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parentCategoryId, that.parentCategoryId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, parentCategoryId, name, description);
    }
}
