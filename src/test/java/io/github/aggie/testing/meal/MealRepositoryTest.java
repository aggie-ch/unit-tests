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
    void shouldBeAbleToFindMealByExactPrice() {
        // given
        Meal meal = new Meal(14, "Small French Fries");
        mealRepository.add(meal);

        // when
        List<Meal> results = mealRepository.findByPriceWithInitialData(14, SearchPrice.EXACT, mealRepository.getAllMeals());

        // then
        assertThat(results.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealByLowerPrice() {
        // given
        Meal meal = new Meal(13, "Hamburger");
        Meal meal2 = new Meal(8, "Small French Fries");
        mealRepository.add(meal);
        mealRepository.add(meal2);

        // when
        List<Meal> results = mealRepository.findByPriceWithInitialData(14, SearchPrice.LESS, mealRepository.getAllMeals());

        // then
        assertThat(results.size(), is(2));
    }

    @Test
    void shouldBeAbleToFindMealByHigherPrice() {
        // given
        Meal meal = new Meal(13, "Hamburger");
        Meal meal2 = new Meal(8, "Small French Fries");
        Meal meal3 = new Meal(4, "Small French Fries");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        mealRepository.add(meal3);

        // when
        List<Meal> results = mealRepository.findByPriceWithInitialData(8, SearchPrice.MORE, mealRepository.getAllMeals());

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

    @Test
    void shouldBeAbleToFindMealByExactNameAndExactPrice() {
        // given
        Meal meal1 = new Meal(20, "Sweet Potatoes");
        Meal meal2 = new Meal(20, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("Sweet Potatoes", true, 20, SearchPrice.EXACT);

        // then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal1));
    }

    @Test
    void shouldBeAbleToFindMealByExactNameAndLowerPrice() {
        // given
        Meal meal1 = new Meal(20, "Sweet Potatoes");
        Meal meal2 = new Meal(10, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("Pepperoni Pizza", true, 15, SearchPrice.LESS);

        // then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal2));
    }

    @Test
    void shouldBeAbleToFindMealByExactNameAndHigherPrice() {
        // given
        Meal meal1 = new Meal(20, "Sweet Potatoes");
        Meal meal2 = new Meal(10, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("Pepperoni Pizza", true, 8, SearchPrice.MORE);

        // then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal2));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterNameAndExactPrice() {
        // given
        Meal meal1 = new Meal(20, "Potatoes");
        Meal meal2 = new Meal(10, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("P", false, 20, SearchPrice.EXACT);

        // then
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(meal1));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterNameAndLowerPrice() {
        // given
        Meal meal1 = new Meal(20, "Potatoes");
        Meal meal2 = new Meal(10, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("P", false, 21, SearchPrice.LESS);

        // then
        assertThat(result.size(), is(2));
    }

    @Test
    void shouldBeAbleToFindMealByFirstLetterNameAndHigherPrice() {
        // given
        Meal meal1 = new Meal(20, "Potatoes");
        Meal meal2 = new Meal(10, "Pepperoni Pizza");
        mealRepository.add(meal1);
        mealRepository.add(meal2);

        // when
        List<Meal> result = mealRepository.find("P", false, 20, SearchPrice.MORE);

        // then
        assertThat(result.size(), is(0));
    }
}
