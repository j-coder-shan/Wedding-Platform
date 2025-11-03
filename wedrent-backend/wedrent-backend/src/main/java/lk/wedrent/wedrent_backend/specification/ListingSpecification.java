package lk.wedrent.wedrent_backend.specification;

import lk.wedrent.wedrent_backend.entity.Listing;
import lk.wedrent.wedrent_backend.entity.ListingCategory;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ListingSpecification {

    public static Specification<Listing> filter(ListingCategory category, String city, 
                                               BigDecimal minPrice, BigDecimal maxPrice, 
                                               Double minRating) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Only show active listings
            predicates.add(criteriaBuilder.equal(root.get("status"), "ACTIVE"));

            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (city != null && !city.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("location")), 
                    "%" + city.toLowerCase() + "%"
                ));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("avgRating"), minRating));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Listing> hasCategory(ListingCategory category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                criteriaBuilder.equal(root.get("category"), category),
                criteriaBuilder.equal(root.get("status"), "ACTIVE")
            );
        };
    }

    public static Specification<Listing> hasLocationContaining(String location) {
        return (root, query, criteriaBuilder) -> {
            if (location == null || location.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("location")), 
                    "%" + location.toLowerCase() + "%"
                ),
                criteriaBuilder.equal(root.get("status"), "ACTIVE")
            );
        };
    }

    public static Specification<Listing> hasTitleContaining(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")), 
                    "%" + title.toLowerCase() + "%"
                ),
                criteriaBuilder.equal(root.get("status"), "ACTIVE")
            );
        };
    }

    public static Specification<Listing> isActive() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("status"), "ACTIVE");
    }

    public static Specification<Listing> hasMinRating(Double minRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("avgRating"), minRating),
                criteriaBuilder.equal(root.get("status"), "ACTIVE")
            );
        };
    }

    public static Specification<Listing> inPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(criteriaBuilder.equal(root.get("status"), "ACTIVE"));
            
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}