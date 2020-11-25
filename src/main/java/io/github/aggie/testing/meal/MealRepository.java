package io.github.aggie.testing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Meal> findByName(String name, boolean exactMatch) {
        if (exactMatch) {
            return meals.stream()
                    .filter(meal -> meal.getName().equals(name))
                    .collect(Collectors.toList());
        } else {
            return meals.stream()
                    .filter(meal -> meal.getName().startsWith(name))
                    .collect(Collectors.toList());
        }
    }

    public List<Meal> findByPrice(int price) {
        return meals.stream()
                .filter(meal -> meal.getPrice() == price)
                .collect(Collectors.toList());
    }
}
