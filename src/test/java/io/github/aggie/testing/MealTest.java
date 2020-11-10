package io.github.aggie.testing;

import org.junit.jupiter.api.Test;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
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
}
