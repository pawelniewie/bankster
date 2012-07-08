package com.pawelniewiadomski.budget;

import com.atlassian.pageobjects.ProductInstance;

import javax.annotation.Nonnull;

public class Sender {

    public void send(@Nonnull String to, @Nonnull String msg) {
        MBankTestedProduct MBank = new MBankTestedProduct(new ProductInstance() {
            @Override
            public String getBaseUrl() {
                return "https://www.mbank.com.pl/";
            }

            @Override
            public int getHttpPort() {
                return 443;
            }

            @Override
            public String getContextPath() {
                return "";
            }

            @Override
            public String getInstanceId() {
                return "MBank";
            }
        });

        System.out.print(MBank.gotoLoginPage().setCustomer("53376795").setPassword("Croissant3k").confirm().getAccounts().toString());
    }
}
