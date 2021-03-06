package com.spices.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.spices.api.converter.CategoryToCategoryResponseDtoConverter;
import com.spices.api.dto.CategoryResponseDto;
import com.spices.domain.Category;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.service.exception.CategoryServiceException;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositoryFacade categoryRepositoryFacade;
    private final CategoryToCategoryResponseDtoConverter toCategoryResponseDtoConverter;

    @Inject
    CategoryServiceImpl(CategoryRepositoryFacade categoryRepositoryFacade, CategoryToCategoryResponseDtoConverter toCategoryResponseDtoConverter) {
        this.categoryRepositoryFacade = categoryRepositoryFacade;
        this.toCategoryResponseDtoConverter = toCategoryResponseDtoConverter;
    }

    @Override
    public void createCategories(List<Category> categories) {
        checkIfAnyCategoryExists(categories);
        categoryRepositoryFacade.createCategories(categories);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        checkIfAnyCategoryDoesNotExist(categories);
        categoryRepositoryFacade.updateCategories(categories);
    }

    @Override
    public List<CategoryResponseDto> retrieveCategories() {
        List<Category> categories = categoryRepositoryFacade.getCategories();
        return categories.stream()
                .map(toCategoryResponseDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategories(List<Long> categoryIds, boolean deleteParentCategories) {
        if (!deleteParentCategories) {
            boolean someCategoryIsParent = checkIfAnyCategoryIsParent(categoryIds);
            if (someCategoryIsParent) {
                throw new CategoryServiceException("Cannot delete parent categories", CategoryServiceException.Type.CANNOT_DELETE_PARENT_CATEGORY);
            }
        }
        categoryRepositoryFacade.deleteCategories(categoryIds);
    }

    private void checkIfAnyCategoryExists(List<Category> categories) {
        categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories).ifPresent(categoryName -> {
                throw new CategoryServiceException(categoryName, CategoryServiceException.Type.DUPLICATE_CATEGORY);
        });
    }

    private void checkIfAnyCategoryDoesNotExist(List<Category> categories) {
        categories.stream()
                .filter(category -> !categoryRepositoryFacade.checkIfCategoryExists(category.getId()))
                .findAny()
                .ifPresent(category -> {
                    throw new CategoryServiceException(category.getId().toString(), CategoryServiceException.Type.CATEGORY_DOES_NOT_EXIST);
                });
    }

    private boolean checkIfAnyCategoryIsParent(List<Long> categoryIds) {
        return categoryRepositoryFacade.checkIfAnyCategoryHasSubCategories(categoryIds);
    }
}
