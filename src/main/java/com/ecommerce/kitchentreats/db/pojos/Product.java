package com.ecommerce.kitchentreats.db.pojos;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String slug;

    private String description;

    private String price;

    private String image;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAat;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreatedAat() {
        return createdAat;
    }

    public void setCreatedAat(LocalDateTime createdAat) {
        this.createdAat = createdAat;
    }

    public LocalDateTime getUpdatedAat() {
        return updatedAat;
    }

    public void setUpdatedAat(LocalDateTime updatedAat) {
        this.updatedAat = updatedAat;
    }
}
