package com.spices.repository;

import com.spices.domain.Category;

public interface CategoryTestRepository {
    Category getCategory(String categoryName);
}
