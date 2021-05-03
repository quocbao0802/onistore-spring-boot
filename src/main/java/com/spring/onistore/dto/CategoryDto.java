package com.spring.onistore.dto;

import com.spring.onistore.entity.ProductCategory;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class CategoryDto implements Serializable {

    private Long product_id;

    private Long category_id;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
