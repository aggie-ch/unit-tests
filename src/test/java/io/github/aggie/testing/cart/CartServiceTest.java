package io.github.aggie.testing.cart;

import io.github.aggie.testing.order.Order;
import io.github.aggie.testing.order.OrderStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

//@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandler;
    @Captor
    ArgumentCaptor<Cart> argumentCaptor;

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
//        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);

        // when + then
        verify(cartHandler, never()).sendToPrepare(any());
//        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));
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

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
//        verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));
    }

    @Disabled
    @Test
    @ExtendWith(MockitoExtension.class)
    void processCartShouldSendToPrepareWithArgumentCaptorAnnotation() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        Annotations:
//        CartHandler cartHandler = mock(CartHandler.class);
//        CartService cartService = new CartService(cartHandler);
//        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));
    }

    @Test
    void shouldDoNothingWhenProcessCart() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        doNothing().when(cartHandler).sendToPrepare(cart);
        willDoNothing().given(cartHandler).sendToPrepare(cart);
        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), is(OrderStatus.PREPARING));
    }

    @Test
    void shouldAnswerWhenProcessCart() {
        // given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        doAnswer(invocation -> {
            Cart argument = invocation.getArgument(0);
            argument.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);

        when(cartHandler.canHandleCart(cart)).then(i -> {
            Cart argument = i.getArgument(0);
            argument.clearCart();
            return true;
        });

        willAnswer(invocation -> {
            Cart argument = invocation.getArgument(0);
            argument.clearCart();
            return true;
        }).given(cartHandler).canHandleCart(cart);

        given(cartHandler.canHandleCart(cart)).will(i -> {
            Cart argument = i.getArgument(0);
            argument.clearCart();
            return true;
        });

        // when
        Cart resultCart = cartService.processCart(cart);

        // then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));
    }

    @Test
    void deliveryShouldBeFree() {
        // given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

        CartHandler cartHandler = mock(CartHandler.class);
        doCallRealMethod().when(cartHandler).isDeliveryFree(cart);

        // when
        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        // then
        assertTrue(isDeliveryFree);
    }
}
