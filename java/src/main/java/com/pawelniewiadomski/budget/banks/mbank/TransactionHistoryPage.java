package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.pageobjects.elements.*;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.*;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryPage extends AbstractMBankPage {

    @ElementBy(id = "lastdays_radio")
    private PageElement lastDaysRadio;

    @ElementBy(id = "export_oper_history_check")
    private CheckboxElement exportHistoryCheckbox;

    @ElementBy(id = "export_oper_history_format")
    private SelectElement exportFormatSelect;

    @ElementBy(id = "Submit")
    private PageElement submit;

    @ElementBy (linkText = "Rachunki")
    private PageElement rachunki;

    @WaitUntil
    public TimedCondition isAt() {
        return submit.timed().isVisible();
    }

    private final DateTimeFormatter dateFmt = DateTimeFormat.forPattern("dd-MM-YYYY");
    private final DecimalFormat amountFormat;

    public TransactionHistoryPage() {
        amountFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("pl"));
        DecimalFormatSymbols dfs = amountFormat.getDecimalFormatSymbols();
        dfs.setGroupingSeparator(' ');
        amountFormat.setDecimalFormatSymbols(dfs);
    }

    @Nonnull
    public MainFramePage goToFrames() {
        rachunki.click();
        return pageBinder.bind(MainFramePage.class);
    }

    @Nonnull
    public TransactionHistoryPage clickLastDaysRadio() {
        lastDaysRadio.click();
        return this;
    }

    @Nonnull
    public TransactionHistoryPage clickExportHistoryCheckbox() {
        exportHistoryCheckbox.click();
        return this;
    }

    @Nonnull
    public TransactionHistoryPage setExportFormat(@Nonnull String format) {
        exportFormatSelect.select(Options.text(format));
        return this;
    }

    public TransactionHistoryPage submit() {
        submit.click();
        return pageBinder.bind(TransactionHistoryPage.class);
    }

    public Iterable<TransactionDescription> getTransactions() {
        return Iterables.transform(Iterables.filter(elementFinder.findAll(By.cssSelector("#account_operations ul.table li")), new HeaderOrFooterPredicate()),
                new Function<PageElement, TransactionDescription>() {
            @Override
            public TransactionDescription apply(@Nullable PageElement from) {
                List<PageElement> dates = from.findAll(By.cssSelector("p.Date span"));
                DateTime opDate = DateTime.parse(Iterables.get(dates, 0).getText(), dateFmt);
                DateTime acDate = DateTime.parse(Iterables.get(dates, 1).getText(), dateFmt);
                String opDesc = from.find(By.className("OperationDescription")).getText();
                String amountStr = from.find(By.cssSelector("p.Amount span")).getText();
                return new TransactionDescription(opDate, acDate, opDesc, parseAmount(amountStr));
            }
        });
    }

    public double parseAmount(@Nonnull String amountStr) {
        ParsePosition pp = new ParsePosition(0);
        Number amount =  amountFormat.parse(amountStr, pp);
        if (pp.getIndex() + 4 != amountStr.length()) { // +4 for " PLN"
            throw new RuntimeException(amountStr);
        }
        return amount.doubleValue();
    }

    public boolean isPreviousPage() {
        return !elementFinder.findAll(By.id("PrevPage")).isEmpty();
    }

    public TransactionHistoryPage clickPreviousPage() {
        elementFinder.find(By.id("PrevPage")).click();
        return pageBinder.bind(TransactionHistoryPage.class);
    }
}

