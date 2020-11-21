package io.github.aggie.testing.account;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        // given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        // when
        List<Account> allActiveAccounts = accountService.getAllActiveAccounts();

        // then
        assertThat(allActiveAccounts, hasSize(2));
    }

    private List<Account> prepareAccountData() {
        Address address1 = new Address("Ocean Drive", "78");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address3 = new Address("Boulevard", "212");
        Account account3 = new Account(address3);

        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getNoActiveAccounts() {
        // given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getAllAccounts()).willReturn(Collections.emptyList());

        // when
        List<Account> allActiveAccounts = accountService.getAllActiveAccounts();

        // then
        assertThat(allActiveAccounts, hasSize(0));
    }

    @Test
    void getAccountsByName() {
        // given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getByName("Dave")).willReturn(Collections.singletonList("James"));

        // when
        List<String> accountNames = accountService.findByName("Dave");

        // then
        assertThat(accountNames, contains("James"));
    }
}
