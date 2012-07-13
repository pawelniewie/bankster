package com.pawelniewiadomski.budget.banks;

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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO: Document this class / interface here
 *
 * @since v5.0.1
 */
public abstract class AbstractTestedProduct implements TestedProduct<WebDriverTester> {
    private static final String TIMEOUTS_PATH = "com/pawelniewiadomski/budget/banks/pageobjects-timeouts.properties";

    private WebDriverTester webDriverTester;
    private ProductInstance productInstance;
    private InjectPageBinder pageBinder;

    public AbstractTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        this.webDriverTester = testerFactory != null ? testerFactory.create() : new DefaultWebDriverTester();
        this.productInstance = checkNotNull(productInstance);
        this.pageBinder = new InjectPageBinder(productInstance, webDriverTester,
                new StandardModule(this),
                new AtlassianWebDriverModule(this),
                new ElementModule(),
                new TimeoutsModule(PropertiesBasedTimeouts.fromClassPath(TIMEOUTS_PATH)));
    }

    @Override
    public <P extends Page> P visit(Class<P> pageClass, Object... args) {
        return pageBinder.navigateToAndBind(pageClass, args);
    }

    public <P extends Page> DelayedBinder<P> visitDelayed(Class<P> pageClass, Object... args)
    {
        DelayedBinder<P> binder = pageBinder.delayedBind(pageClass, args);
        webDriverTester.gotoUrl(productInstance.getBaseUrl() + binder.get().getUrl());
        return binder;
    }

    public Injector injector()
    {
        return pageBinder.injector();
    }

    @Override
    public PageBinder getPageBinder() {
        return pageBinder;
    }

    @Override
    public ProductInstance getProductInstance() {
        return productInstance;
    }

    @Override
    public WebDriverTester getTester() {
        return webDriverTester;
    }

}
