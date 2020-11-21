package io.github.aggie.testing.account;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class AccountServiceStubTest {

    @Test
    void getAllActiveAccounts() {
        // given
        AccountRepository accountRepositoryStub = new AccountRepositoryStub();
        AccountService accountService = new AccountService(accountRepositoryStub);

        // when
        List<Account> allActiveAccounts = accountService.getAllActiveAccounts();

        // then
        assertThat(allActiveAccounts, hasSize(2));
    }
}
