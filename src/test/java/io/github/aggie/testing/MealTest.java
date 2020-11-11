package io.github.aggie.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(27);

        //when
        int discountedPrice = meal.getDiscountedPrice(5);

        //then
        assertEquals(22, discountedPrice);
//        assertThat(discountedPrice, equalTo(22));
        assertThat(discountedPrice).isEqualTo(22);
    }

    @Test
    void referencesToTheSameObjectShouldBeEqual() {
        //given + when
        Meal meal1 = new Meal(10);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);
//        assertThat(meal1, sameInstance(meal2));
        assertThat(meal1).isSameAs(meal2);
    }

    @Test
    void referencesToDifferentObjectsShouldNotBeEqual() {
        //given + when
        Meal meal1 = new Meal(10);
        Meal meal2 = new Meal(20);

        //then
        assertNotSame(meal1, meal2);
//        assertThat(meal1, not(sameInstance(meal2)));
        assertThat(meal1).isNotSameAs(meal2);
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame() {
        //given + when
        Meal meal1 = new Meal(15, "Pasta");
        Meal meal2 = new Meal(15, "Pasta");

        //then
        assertEquals(meal1, meal2, "Checking if two meals are equal");
        assertThat(meal1).isEqualTo(meal2);
    }

    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice() {
        //given
        Meal meal = new Meal(15, "Pasta");

        //when + then
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(18));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 18})
    void mealPricesShouldBeLowerThan20(int price) {
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
    void mealsShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(22));
    }

    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Hamburger", 22),
                Arguments.of("Cheeseburger", 28)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, endsWith("-cake"));
    }

    private static Stream<String> createCakeNames() {
        List<String> cakeNames = Arrays.asList("Pie-cake", "Muffin-cake");
        return cakeNames.stream();
    }
}
