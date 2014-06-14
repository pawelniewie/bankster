package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;

import com.pawelniewiadomski.budget.banks.AbstractPage;
import com.pawelniewiadomski.budget.banks.mbank.MainFramePage;
import com.sun.jna.platform.win32.WinDef;

import javax.annotation.Nonnull;

public class LoginPage extends AbstractPage {
    @ElementBy(name="uzytkownik")
    private PageElement customer;

    @ElementBy(name="hasloToken")
    private PageElement password;

    @ElementBy(name="tokenSprzetowy")
    private PageElement token;

    @ElementBy(id = "login_next")
    private PageElement loginNext;

    @ElementBy(name = "zaloguj")
    private PageElement confirm;
    private boolean passwordVisible;

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

    public MainPage confirm() {
        confirm.click();
        return pageBinder.bind(MainPage.class);
    }

    public TimedCondition isPasswordVisible()
    {
        return password.timed().isVisible();
    }

    public LoginPage clickNext()
    {
        loginNext.click();
        return pageBinder.bind(LoginPage.class);
    }
}
