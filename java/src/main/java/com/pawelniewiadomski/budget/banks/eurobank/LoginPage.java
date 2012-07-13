package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.pawelniewiadomski.budget.banks.AbstractPage;
import com.pawelniewiadomski.budget.banks.mbank.MainFramePage;

import javax.annotation.Nonnull;

public class LoginPage extends AbstractPage {
    @ElementBy(name="uzytkownik")
    private PageElement customer;

    @ElementBy(name="hasloToken")
    private PageElement password;

    @ElementBy(name="tokenSprzetowy")
    private PageElement token;

    @ElementBy(name = "zaloguj")
    private PageElement confirm;

    @Override
    public String getUrl() {
        return "bi/bezpieczenstwo_logowanie.ebk";
    }

    public LoginPage setCustomer(@Nonnull CharSequence customer) {
        this.customer.clear();
        this.customer.type(customer);
        return this;
    }

    public LoginPage setPassword(@Nonnull CharSequence password) {
        this.password.clear();
        this.password.type(password);
        return this;
    }

    public LoginPage setToken(@Nonnull CharSequence token) {
        this.token.clear();
        this.token.type(token);
        return this;
    }

    public MainFramePage confirm() {
        confirm.click();
//        return pageBinder.bind(MainFramePage.class);
        return null;
    }
}
