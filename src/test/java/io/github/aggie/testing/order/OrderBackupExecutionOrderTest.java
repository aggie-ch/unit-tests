package io.github.aggie.testing.order;

import io.github.aggie.testing.order.Order;
import io.github.aggie.testing.order.OrderBackup;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExecutionOrderTest {

    @Test
    void callingBackupWithoutCreatingAFileFirstShouldThrowException() {
        // given
        OrderBackup orderBackup = new OrderBackup();
        Order order = new Order();

        // when + then
        assertThrows(IOException.class, () -> orderBackup.backupOrder(order));
    }
}
