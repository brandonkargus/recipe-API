package com.api.recipeapi.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ingredients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(generator = "ingredient_generator")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String amount;

    private String state;
}
