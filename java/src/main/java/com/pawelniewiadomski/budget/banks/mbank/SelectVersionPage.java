package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.pawelniewiadomski.budget.banks.AbstractPage;

import javax.annotation.Nonnull;

public class SelectVersionPage extends AbstractPage {

    @ElementBy(cssSelector=".button.blue.big.next")
    private PageElement button;

    @Override
    public String getUrl() {
        return "/";
    }

    public LoginPage useOldVersion() {
        button.click();
        return pageBinder.bind(LoginPage.class);
    }
}
