package com.pawelniewiadomski.budget.pageobjects;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.PageElementFinder;
import com.atlassian.webdriver.AtlassianWebDriver;

import javax.inject.Inject;

public abstract class AbstractPage implements Page {
    @Inject
    protected PageBinder pageBinder;

    @Inject
    protected PageElementFinder elementFinder;

    @Inject
    protected AtlassianWebDriver driver;

    @Override
    public String getUrl() {
        return null;
    }
}
