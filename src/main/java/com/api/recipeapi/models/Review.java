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
        if (rating <= 0 || rating > 10) {
            throw new IllegalStateException("Rating must be between 0 and 10.");
        }
        this.rating = rating;
    }
    public int getRating() {            //TODO part of the "averageRating" process
        return this.rating;
    }

    public void checkUser() throws IllegalStateException {      //TODO beginning of user validation for review and recipe

        if (username.equals()) {
            throw new IllegalStateException("We know you love your own recipe, but you can't submit a review for it!");
        }
    }

}
