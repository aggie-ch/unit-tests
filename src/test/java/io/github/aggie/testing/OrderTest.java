package io.github.aggie.testing;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OrderTest {

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        // given
        Order order = new Order();

        // when

        // then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        // given
        Meal meal = new Meal(14, "Pizza");
        Order order = new Order();

        // when
        order.addMealToOrder(meal);

        // then
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(), hasItem(meal));
        assertThat(order.getMeals().get(0).getPrice(), equalTo(14));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        // given
        Meal meal = new Meal(14, "Pizza");
        Order order = new Order();

        // when
        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        // then
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), not(contains(meal)));
    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        // given
        Meal meal1 = new Meal(24, "Chicken");
        Meal meal2 = new Meal(32, "Fish");
        Order order = new Order();

        // when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        // then
        assertThat(order.getMeals(), contains(meal1, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
    }

    @Test
    void testIfTwoListsAreTheSame() {
        // given
        Meal meal1 = new Meal(24, "Margherita");
        Meal meal2 = new Meal(32, "Pepperoni");
        Meal meal3 = new Meal(38, "Hawaii");

        // when
        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal2);

        // then
        assertThat(meals1, is(meals2));
    }
}