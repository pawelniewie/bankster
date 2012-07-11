package com.pawelniewiadomski.budget.pageobjects.mbank;

import com.atlassian.pageobjects.elements.PageElement;
import com.google.common.base.Predicate;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;

class HeaderOrFooterPredicate implements Predicate<PageElement> {
    @Override
    public boolean apply(@Nullable PageElement input) {
        return input != null && StringUtils.indexOfAny(input.getAttribute("class"), new String[]{"header", "footer", "noDataMessage"}) == StringUtils.INDEX_NOT_FOUND;
    }
}
