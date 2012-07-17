package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.pawelniewiadomski.budget.banks.AbstractPage;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MainPage extends AbstractPage {

    @Override
    public String getUrl() {
        return "bi/produkt_lista.ebk";
    }

    @Nonnull
    public Iterable<String> getAccounts() {
        return Iterables.transform(getListOfAccounts(), new Function<PageElement, String>() {
            @Override
            public String apply(@Nullable PageElement from) {
                return from.find(By.cssSelector("a")).getText();
            }
        });
    }

    private Iterable<PageElement> getListOfAccounts() {
        return Iterables.filter(elementFinder.findAll(By.cssSelector(".tabela_lista tbody tr td")), new Predicate<PageElement>() {
            @Override
            public boolean apply(@Nullable PageElement input) {
                if (input == null) {
                    return false;
                }
                List<PageElement> links = input.findAll(By.tagName("a"));
                if (links.isEmpty()) {
                    return false;
                }
                if (StringUtils.startsWith(Iterables.get(links, 0).getAttribute("href"), "/bi/rachunek_szczegoly.ebk?rachunekId=")) {
                    return true;
                }
                return false;
            }
        });
    }
}
