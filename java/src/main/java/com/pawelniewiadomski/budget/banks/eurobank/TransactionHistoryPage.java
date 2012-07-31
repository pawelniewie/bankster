package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.elements.ElementBy;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.pawelniewiadomski.budget.banks.AbstractPage;
import com.pawelniewiadomski.budget.utils.OfxFactory;
import net.sf.ofx4j.domain.data.common.Transaction;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

// http://code.google.com/p/mbank2ofx/source/browse/trunk/mBankToOFX/src/main/java/pl/sevencoins/mbanktools/MBankCSVToOFX.java
public class TransactionHistoryPage extends AbstractPage {

    @ElementBy (cssSelector = "#rachunekHistoriaForm .submit")
    protected PageElement submit;

    private final DateTimeFormatter dateFmt = DateTimeFormat.forPattern("dd-MM-YYYY").withZone(DateTimeZone.forID("Poland"));
    private final DecimalFormat amountFormat;

    public TransactionHistoryPage() {
        amountFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en"));
//        DecimalFormatSymbols dfs = amountFormat.getDecimalFormatSymbols();
//        dfs.setGroupingSeparator(' ');
//        amountFormat.setDecimalFormatSymbols(dfs);
    }

    @WaitUntil
    public TimedCondition isAt() {
        return submit.timed().isVisible();
    }

    @Nonnull
    public MainPage goToMain() {
        elementFinder.find(By.linkText("eurobank online")).click();
        elementFinder.find(By.linkText("produkty")).click();
        return pageBinder.bind(MainPage.class);
    }

    public TransactionHistoryPage submit() {
        submit.click();
        return pageBinder.bind(TransactionHistoryPage.class);
    }

    public Iterable<Transaction> getTransactions() {
        List<PageElement> messages = elementFinder.findAll(By.className("komunikat"));
        if (messages.size() > 0 && messages.get(0).isVisible()) {
            return Collections.emptyList();
        }

        return Iterables.transform(elementFinder.findAll(By.cssSelector("table.tabela_lista tbody tr")),
                new Function<PageElement, Transaction>() {
                    @Override
                    public Transaction apply(@Nullable PageElement from) {
                        List<PageElement> columns = from.findAll(By.tagName("td"));
                        DateTime opDate = dateFmt.parseDateTime(Iterables.get(columns, 1).getText());
                        DateTime acDate = dateFmt.parseDateTime(Iterables.get(columns, 0).getText());
                        String opDesc = Iterables.get(columns, 2).getText();
                        String amountStr = Iterables.get(columns, 3).getText();
                        return OfxFactory.createTransaction(opDate, acDate, opDesc, parseAmount(amountStr));
                    }
                });
    }

    public double parseAmount(@Nonnull String amountStr) {
        ParsePosition pp = new ParsePosition(0);
        Number amount =  amountFormat.parse(amountStr, pp);
        if (pp.getIndex() != amountStr.length()) {
            throw new RuntimeException(amountStr);
        }
        return amount.doubleValue();
    }

    public boolean isPreviousPage() {
        return !elementFinder.findAll(By.cssSelector(".stronicowanie_przyciski a.nastepna")).isEmpty();
    }

    public TransactionHistoryPage clickPreviousPage() {
        elementFinder.find(By.cssSelector(".stronicowanie_przyciski a.nastepna")).click();
        return pageBinder.bind(TransactionHistoryPage.class);
    }
}
