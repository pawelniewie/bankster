package com.pawelniewiadomski.smsplay.send;

import com.atlassian.pageobjects.ProductInstance;

import javax.annotation.Nonnull;

public class Sender {

    public void send(@Nonnull String to, @Nonnull String msg) {
        PlayTestedProduct play = new PlayTestedProduct(new ProductInstance() {
            @Override
            public String getBaseUrl() {
                return "https://logowanie.play.pl";
            }

            @Override
            public int getHttpPort() {
                return 80;
            }

            @Override
            public String getContextPath() {
                return "";
            }

            @Override
            public String getInstanceId() {
                return "play";
            }
        });

        play.gotoLoginPage();
    }
}
