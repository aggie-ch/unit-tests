package io.github.aggie.testing.cart;

import io.github.aggie.testing.order.Order;
import io.github.aggie.testing.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        verify(cartHandler).sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeast(1)).sendToPrepare(cart);

        then(cartHandler).should().canHandleCart(cart);
        then(cartHandler).should().sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders().get(0).getOrderStatus(), is(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(false);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart);

        assertThat(resultCart.getOrders().get(0).getOrderStatus(), is(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {
        // given
        CartHandler cartHandler = mock(CartHandler.class);

        given(cartHandler.canHandleCart(any())).willReturn(false);
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);

        // when + then
        verify(cartHandler, never()).sendToPrepare(any());
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        CartHandler cartHandler = mock(CartHandler.class);

        given(cartHandler.canHandleCart(cart)).willReturn(false, true, false, false);

        // when + then
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(
                argThat(cartObject -> cartObject.getOrders().size() > 0)))
                .willReturn(true);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        verify(cartHandler).sendToPrepare(cart);
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), is(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willThrow(IllegalStateException.class);

        // when + then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }
}
