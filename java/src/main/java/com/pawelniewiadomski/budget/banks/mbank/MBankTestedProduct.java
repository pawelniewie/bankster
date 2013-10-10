package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.*;
import com.pawelniewiadomski.budget.banks.AbstractTestedProduct;

import static com.google.common.base.Preconditions.checkNotNull;

public class MBankTestedProduct extends AbstractTestedProduct {

    public MBankTestedProduct(ProductInstance productInstance)
    {
        super(null, productInstance);
    }

    public LoginPage gotoLoginPage()
    {
        return getPageBinder().navigateToAndBind(SelectVersionPage.class).useOldVersion();
    }
}
