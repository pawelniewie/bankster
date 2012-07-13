package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.*;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.binder.StandardModule;
import com.atlassian.pageobjects.elements.ElementModule;
import com.atlassian.pageobjects.elements.timeout.PropertiesBasedTimeouts;
import com.atlassian.pageobjects.elements.timeout.TimeoutsModule;
import com.atlassian.webdriver.AtlassianWebDriverModule;
import com.atlassian.webdriver.pageobjects.DefaultWebDriverTester;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.google.inject.Injector;
import com.pawelniewiadomski.budget.banks.AbstractTestedProduct;

import static com.google.common.base.Preconditions.checkNotNull;

public class EurobankTestedProduct extends AbstractTestedProduct {

    public EurobankTestedProduct(ProductInstance productInstance)
    {
        super(null, productInstance);
    }

    public LoginPage gotoLoginPage()
    {
        return getPageBinder().navigateToAndBind(LoginPage.class);
    }
}
