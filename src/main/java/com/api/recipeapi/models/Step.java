package com.api.recipeapi.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "steps")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Id
    @GeneratedValue(generator = "step_generator")
    private long id;

    @NotNull
    private int stepNumber;

    @NotNull
    private String description;
}
