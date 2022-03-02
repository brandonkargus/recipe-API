package com.api.recipeapi.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "reviews")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(generator = "review_generator")
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private int rating;

    private String description;

    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return this.rating;
    }

}
