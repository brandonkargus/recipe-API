package com.api.recipeapi.repositories;

import com.api.recipeapi.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    ArrayList<Recipe> findByNameContaining(String name);

    @Query("FROM recipes WHERE name LIKE %:name% AND averageReviewRating >= :averageReviewRating")
    ArrayList<Recipe> findRecipesByNameContainingAndAverageReviewRating(String name, double averageReviewRating);         //TODO shorten method name

    @Query("FROM recipes WHERE averageReviewRating >= :averageReviewRating")
    ArrayList<Recipe> findByAverageReviewRating(double averageReviewRating);

    //@Query("FROM recipes WHERE userName LIKE %:userName%")
    ArrayList<Recipe> findByUserNameContaining(String userName);

    @Query("From recipes WHERE userName = 'topChef365'")
    ArrayList<Recipe> findByUserNameTopChef365();
}
