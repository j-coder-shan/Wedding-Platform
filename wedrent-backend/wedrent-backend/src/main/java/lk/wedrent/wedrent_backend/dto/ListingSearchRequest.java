package lk.wedrent.wedrent_backend.dto;

import lk.wedrent.wedrent_backend.entity.ListingCategory;
import java.math.BigDecimal;

public class ListingSearchRequest {
    private String category;  // Changed to String to match API specs
    private String city;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Double minRating;
    private Integer page = 0;
    private Integer size = 20;
    private String sort = "id,desc";
    private String q;  // Search query for title/description

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ListingCategory getCategoryEnum() {
        if (category == null || category.trim().isEmpty()) {
            return null;
        }
        try {
            return ListingCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinRating() {
        return minRating;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
