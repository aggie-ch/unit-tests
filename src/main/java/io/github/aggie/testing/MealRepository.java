package io.github.aggie.testing;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void add(Meal meal) {
        meals.add(meal);
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }
}
