package com.pawelniewiadomski.budget.pageobjects.mbank;

import com.atlassian.pageobjects.binder.Init;
import com.pawelniewiadomski.budget.MBankTestedProduct;
import com.pawelniewiadomski.budget.pageobjects.AbstractPage;
import org.openqa.selenium.NoSuchFrameException;

import javax.inject.Inject;

public class AbstractMBankPage extends AbstractPage {

    @Inject
    protected MBankTestedProduct product;

    @Init
    @SuppressWarnings("unused")
    protected void init() {
        try {
            product.getTester().getDriver().switchTo().frame("MainFrame");
        } catch (NoSuchFrameException e) {
            // ignore
        }
    }
}
