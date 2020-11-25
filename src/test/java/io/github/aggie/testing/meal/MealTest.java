package io.github.aggie.testing.meal;

import io.github.aggie.testing.extensions.IllegalArgumentExceptionIgnoreExtension;
import io.github.aggie.testing.meal.Meal;
import io.github.aggie.testing.order.Order;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class MealTest {

    @Spy
    private Meal mealSpy;

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

    @ExtendWith(IllegalArgumentExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 7})
    void mealPricesShouldBeLowerThan12(int price) {
        if(price > 4) {
            throw new IllegalArgumentException();
        }
        assertThat(price, lessThan(12));
    }

    @Tag("Pizza")
    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10, 2, "Sandwich"));
        order.addMealToOrder(new Meal(8, 5, "Hot-Dog"));
        order.addMealToOrder(new Meal(14, 4, "Pizza"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(60));
            };

            String name = "Test name: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);
        }

        return dynamicTests;
    }

    @Test
    void testMealSumPrice() {
        // given
        Meal meal = mock(Meal.class);
        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);
        given(meal.sumPrice()).willCallRealMethod();

        // when
        int result = meal.sumPrice();

        // then
        assertThat(result).isEqualTo(45);
    }

    @Test
    void testMealSumPriceWithSpy() {
        // given
//        Meal meal = spy(Meal.class);
        // or
        Meal meal = spy(new Meal(15, 3, "Kebab"));

//        given(meal.getPrice()).willReturn(15);
//        given(meal.getQuantity()).willReturn(3);

        // when
        int result = meal.sumPrice();

        // then
        then(meal).should().getPrice();
        then(meal).should().getQuantity();
        assertThat(result).isEqualTo(45);
    }

    @Disabled
    @Test
    @ExtendWith(MockitoExtension.class)
    void testMealSumPriceWithSpyWithAnnotation() {
        // given
        given(mealSpy.getPrice()).willReturn(15);
        given(mealSpy.getQuantity()).willReturn(3);

        // when
        int result = mealSpy.sumPrice();

        // then
        then(mealSpy).should().getPrice();
        then(mealSpy).should().getQuantity();
        assertThat(result).isEqualTo(45);
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }
}
