package io.github.aggie.testing;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void initializeOrder() {
        System.out.println("Inside before each");
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        System.out.println("Inside after each");
        order.cancel();
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        // given + when

        // then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Tag("Pizza")
    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        // given
        Meal meal = new Meal(14, "Pizza");

        // when
        order.addMealToOrder(meal);

        // then
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(), hasItem(meal));
        assertThat(order.getMeals().get(0).getPrice(), equalTo(14));
    }

    @Tag("Pizza")
    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        // given
        Meal meal = new Meal(14, "Pizza");

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

        // when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        // then
        assertThat(order.getMeals(), contains(meal1, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
        assertThat(order.getMeals().get(0), is(meal1));
        assertThat(order.getMeals().get(1), is(meal2));
    }

    @Disabled
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

    @Test
    void orderTotalPriceShouldNotExceedsMaxIntValue() {
        // given
        Meal meal1 = new Meal(Integer.MAX_VALUE, "Chicken");
        Meal meal2 = new Meal(Integer.MAX_VALUE, "Fish");

        // when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        // then
        assertThrows(IllegalStateException.class, () -> order.totalPrice());
    }

    @Test
    void emptyOrderTotalPriceShouldEqualZero() {
        // given + when
        // Order is created in BeforeEach

        // then
        assertThat(order.totalPrice(), is(0));
    }

    @Test
    void cancelingOrderShouldRemoveAllItemsFromMealsList() {
        // given
        Meal meal1 = new Meal(24, "Chicken");
        Meal meal2 = new Meal(32, "Fish");

        // when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);
        order.cancel();

        // then
        assertThat(order.getMeals(), is(empty()));
        assertThat(order.getMeals().size(), is(0));
    }
}
