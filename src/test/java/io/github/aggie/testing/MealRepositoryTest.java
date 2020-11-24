package io.github.aggie.testing;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MealRepositoryTest {

    private MealRepository mealRepository = new MealRepository();

    @Test
    void shouldBeAbleToAddMealToRepository() {
        // given
        Meal meal = new Meal(8, "Sandwich");

        // when
        mealRepository.add(meal);

        // then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
        assertThat(mealRepository.getAllMeals().size(), equalTo(1));
    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {
        // given
        Meal meal1 = new Meal(10, "Small French Fries");
        Meal meal2 = new Meal(20, "Medium French Fries");
        Meal meal3 = new Meal(25, "XL French Fries");
        mealRepository.add(meal1);
        mealRepository.add(meal2);
        mealRepository.add(meal3);

        // when
        mealRepository.delete(meal2);

        // then
        assertThat(mealRepository.getAllMeals().size(), equalTo(2));
    }
}
