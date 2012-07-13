package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.pawelniewiadomski.budget.utils.LinkTextPredicate;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MainFramePage extends AbstractMBankPage {
    @ElementBy(id = "AccountsGrid")
    private PageElement accounts;

    @Override
    public String getUrl() {
        return "frames.aspx";
    }

    @Nonnull
    public Iterable<String> getAccounts() {
        return Iterables.transform(Iterables.filter(getListOfAccounts(), new HeaderOrFooterPredicate()), new Function<PageElement, String>() {
            @Override
            public String apply(@Nullable PageElement from) {
                return from.find(By.cssSelector("p.Account a")).getText();
            }
        });
    }

    private List<PageElement> getListOfAccounts() {
        return accounts.find(By.tagName("ul")).findAll(By.tagName("li"));
    }

    @Nonnull
    public Iterable<PageElement> getActions(@Nonnull final String accountName) {
        PageElement account = Iterables.getOnlyElement(Iterables.filter(getListOfAccounts(), new Predicate<PageElement>() {
            @Override
            public boolean apply(@Nullable PageElement input) {
                return !input.findAll(By.linkText(accountName)).isEmpty();
            }
        }), null);
        return account != null ? account.findAll(By.cssSelector("p.Actions a")) : Collections.<PageElement>emptyList();
    }

    public TransactionHistoryPage openTransactionHistory(@Nonnull String accountName) {
        PageElement historyLink = Iterables.getOnlyElement(Iterables.filter(getActions(accountName), new LinkTextPredicate("Historia operacji")), null);
        historyLink.click();
        return pageBinder.bind(TransactionHistoryPage.class);
    }

}
