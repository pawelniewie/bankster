package com.pawelniewiadomski.smsplay.send.pageobjects;

import com.atlassian.pageobjects.Page;

public class LoginPage implements Page {
    @Override
    public String getUrl() {
        return "/p4-idp2/LoginForm.do";
    }
}
