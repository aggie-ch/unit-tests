package io.github.aggie.testing;

public class Account {
    private boolean active;
    private Address defaultDeliveryAddress;

    public Account() {
        this.active = false;
    }

    public Account(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;

        if (defaultDeliveryAddress != null) {
            activate();
        } else {
            this.active = false;
        }
    }

    void activate() {
        this.active = true;
    }

    boolean isActive() {
        return this.active;
    }

    public Address getDefaultDeliveryAddress() {
        return defaultDeliveryAddress;
    }

    public void setDefaultDeliveryAddress(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }
}
