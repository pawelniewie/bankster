package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.pawelniewiadomski.budget.banks.AbstractPage;

import javax.annotation.Nonnull;

public class LoginPage extends AbstractPage {

    @ElementBy(name="customer")
    private PageElement customer;

    @ElementBy(name="password")
    private PageElement password;

    @ElementBy(id = "confirm")
    private PageElement confirm;

    @Override
    public String getUrl() {
        return "logon.aspx";
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

    public MainFramePage confirm() {
        confirm.click();
        return pageBinder.bind(MainFramePage.class);
    }
}
