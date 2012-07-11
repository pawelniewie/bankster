package com.pawelniewiadomski.budget.utils;

import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Predicate;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;

public class LinkTextPredicate implements Predicate<PageElement> {
    private final String linkText;

    public LinkTextPredicate(String linkText) {
        this.linkText = linkText;
    }

    @Override
    public boolean apply(@Nullable PageElement input) {
        return StringUtils.equalsIgnoreCase(linkText, input.getText());
    }
}