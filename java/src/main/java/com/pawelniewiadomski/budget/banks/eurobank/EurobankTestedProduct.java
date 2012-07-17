package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.ProductInstance;
import com.pawelniewiadomski.budget.banks.AbstractTestedProduct;

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
