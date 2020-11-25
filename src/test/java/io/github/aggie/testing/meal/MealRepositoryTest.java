package io.github.aggie.testing.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MealRepositoryTest {

    private MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

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

    @Test
    void shouldBeAbleToFindMealByPrice() {
        // given
        Meal meal = new Meal(14, "Small French Fries");
        mealRepository.add(meal);

        // when
        List<Meal> results = mealRepository.findByPrice(14);

        // then
        assertThat(results.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByExactName() {
        // given
        Meal meal = new Meal(10, "French Fries");
        Meal meal2 = new Meal(12, "Fre");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        // when
        List<Meal> results = mealRepository.findByName("French Fries", true);

        // then
        assertThat(results.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByStartingLetters() {
        // given
        Meal meal = new Meal(10, "French Fries");
        Meal meal2 = new Meal(12, "Fre");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        // when
        List<Meal> results = mealRepository.findByName("F", false);

        // then
        assertThat(results.size(), is(2));
    }
}
