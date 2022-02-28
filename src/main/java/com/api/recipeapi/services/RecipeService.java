package com.api.recipeapi.services;

import com.api.recipeapi.exceptions.NoSuchRecipeException;
import com.api.recipeapi.models.Recipe;
import com.api.recipeapi.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Transactional
    public Recipe createNewRecipe(Recipe recipe) throws IllegalStateException {
        recipe.validate();
        recipe = recipeRepository.save(recipe);
        recipe.generateLocationURI();
        return recipe;
    }

    public Recipe getRecipeById(Long id) throws NoSuchRecipeException {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new NoSuchRecipeException("No recipe with ID " + id + " could be found.");
        }

        Recipe recipe = recipeOptional.get();
        recipe.generateLocationURI();
        return recipe;
    }

    public ArrayList<Recipe> getRecipesByName(String name) throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepository.findByNameContaining(name);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found with that name..");
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }
        return matchingRecipes;
    }

    public ArrayList<Recipe> getRecipesByUserName(String userName) throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepository.findByUserNameContaining(userName);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found from that user..");
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }
        return matchingRecipes;
    }

    public ArrayList<Recipe> getRecipesByTopChef365() throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepository.findByUserNameTopChef365();

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes have been added from topChef365.");
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }
        return matchingRecipes;
    }


    public ArrayList<Recipe> getRecipesByAverageReviewRating(double averageReviewRating) throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepository.findByAverageReviewRating(averageReviewRating);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found with a minimum average review rating of: " + averageReviewRating);
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }
        return matchingRecipes;
    }

    public ArrayList<Recipe> getRecipesByNameContainingAndAverageReviewRating(String name, double averageReviewRating) throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepository.findRecipesByNameContainingAndAverageReviewRating(name,averageReviewRating);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found with that name and/or review rating.");
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }
        return matchingRecipes;
    }

    public ArrayList<Recipe> getAllRecipes() throws NoSuchRecipeException {
        ArrayList<Recipe> recipes = new ArrayList<>(recipeRepository.findAll());

        if (recipes.isEmpty()) {
            throw new NoSuchRecipeException("There are no recipes yet :( feel free to add one though");
        }
        return recipes;
    }

    @Transactional
    public Recipe deleteRecipeById(Long id) throws NoSuchRecipeException {
        try {
            Recipe recipe = getRecipeById(id);
            recipeRepository.deleteById(id);
            return recipe;
        } catch (NoSuchRecipeException e) {
            throw new NoSuchRecipeException(e.getMessage() + " Could not delete.");
        }
    }

    @Transactional
    public Recipe updateRecipe(Recipe recipe, boolean forceIdCheck) throws NoSuchRecipeException {
        try {
            if (forceIdCheck) {
                getRecipeById(recipe.getId());
            }
            recipe.validate();
            Recipe savedRecipe = recipeRepository.save(recipe);
            savedRecipe.generateLocationURI();
            return savedRecipe;
        } catch (NoSuchRecipeException e) {
            throw new NoSuchRecipeException("The recipe you passed in did not have an ID found in the database." +
                    " Double check that it is correct. Or maybe you meant to POST a recipe not PATCH one.");
        }
    }
}
