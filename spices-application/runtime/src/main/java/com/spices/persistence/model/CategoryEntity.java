package com.spices.persistence.model;

import com.google.common.collect.Sets;
import com.spices.domain.Product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @Column(name = "category_id")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    @GeneratedValue(generator = "category_seq", strategy = GenerationType.SEQUENCE)
    private Long categoryId;

    @Column(name = "category_name", unique = true)
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id")
    private CategoryEntity parentCategory;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products = Sets.newHashSet();

    public CategoryEntity() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public CategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryEntity parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void addProduct(ProductEntity product) {
        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(ProductEntity product) {
        products.remove(product);
        product.getCategories().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity)) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}
