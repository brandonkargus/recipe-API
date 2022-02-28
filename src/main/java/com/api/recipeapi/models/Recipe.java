package com.api.recipeapi.models;

import com.api.recipeapi.exceptions.NoSuchReviewException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.decimal4j.util.DoubleRounder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "recipes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(generator = "recipe_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "minutes_to_make", nullable = false)
    private Integer minutesToMake;

    @Column(name = "difficulty_rating", nullable = false)
    private Integer difficultyRating;

    @Column(name = "average_review_rating", nullable = false)
    private double averageReviewRating;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Step> steps = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", nullable = false, foreignKey = @ForeignKey)
    private Collection<Review> reviews;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Transient
    @JsonIgnore
    private URI locationURI;

    public void setDifficultyRating(int difficultyRating) {
        if (difficultyRating < 0 || difficultyRating > 10) {
            throw new IllegalStateException("Difficulty rating must be between 0 and 10.");
        }
        this.difficultyRating = difficultyRating;
    }

    public void setAverageReviewRating() throws NoSuchReviewException {
        double reviewTotal = 0.0;
        if(reviews == null) {
            this.averageReviewRating = 0.0;
            throw new NoSuchReviewException("No reviews yet.");        //TODO return "no reviews" message
        } else {
            for(Review r : reviews) {
                reviewTotal += r.getRating();
            }
            this.averageReviewRating = DoubleRounder.round(reviewTotal / reviews.size(), 1);
        }
    }

    public void validate() throws IllegalStateException {
        if (ingredients.size() == 0) {
            throw new IllegalStateException("You have to have at least one ingredient for your recipe!");
        } else if (steps.size() == 0) {
            throw new IllegalStateException("You have to include at least one step for your recipe!");
        }
    }

    public void generateLocationURI() {
        try {
            locationURI = new URI(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/recipes/")
                            .path(String.valueOf(id))
                            .toUriString());
        } catch (URISyntaxException e) {
            //Exception should stop here.
        }
    }
}
