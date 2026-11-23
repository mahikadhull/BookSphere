package model;

import java.io.Serializable;

public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private String categoryId;
    private String categoryName;
    private String description;

    // Constructor
    public Category(String categoryId, String categoryName,
                    String description) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    // Getters and Setters
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
