package com.pawelniewiadomski.budget.pageobjects;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FramesPage implements Page {
    @ElementBy(id = "AccountsGrid")
    private PageElement accounts;

    @Override
    public String getUrl() {
        return "frames.aspx";
    }

    @Nonnull
    public Iterable<String> getAccounts() {
        return Iterables.transform(Iterables.filter(accounts.find(By.tagName("ul")).findAll(By.tagName("li")), new Predicate<PageElement>() {
            @Override
            public boolean apply(@Nullable PageElement input) {
                return input != null && StringUtils.indexOfAny(input.getAttribute("class"), new String[]{"header", "footer"}) == StringUtils.INDEX_NOT_FOUND;
            }
        }), new Function<PageElement, String>() {
            @Override
            public String apply(@Nullable PageElement from) {
                return from.find(By.cssSelector("p.Account a")).getText();
            }
        });
    }

}
