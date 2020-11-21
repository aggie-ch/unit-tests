package io.github.aggie.testing.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

    @Override
    public List<Account> getAllAccounts() {
        Address address1 = new Address("Ocean Drive", "78");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address3 = new Address("Boulevard", "212");
        Account account3 = new Account(address3);

        return Arrays.asList(account1, account2, account3);
    }

    @Override
    public List<String> getByName(String name) {
        return null;
    }
}
