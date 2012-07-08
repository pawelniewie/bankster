package com.pawelniewiadomski.budget.pageobjects;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class LoginPage implements Page {

    @ElementBy(name="customer")
    private PageElement customer;

    @ElementBy(name="password")
    private PageElement password;

    @ElementBy(id = "confirm")
    private PageElement confirm;

    @Inject
    private PageBinder pageBinder;

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

    public FramesPage confirm() {
        confirm.click();
        return pageBinder.bind(FramesPage.class);
    }
}
