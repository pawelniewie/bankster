package com.pawelniewiadomski.budget;

import com.atlassian.pageobjects.ProductInstance;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pawelniewiadomski.budget.pageobjects.mbank.MainFramePage;
import com.pawelniewiadomski.budget.pageobjects.mbank.TransactionDescription;
import com.pawelniewiadomski.budget.pageobjects.mbank.TransactionHistoryPage;
import com.pawelniewiadomski.budget.utils.Qif;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MBankClient {

    public Map<String, File> downloadOperationsHistory(@Nonnull String username, @Nonnull String password) {
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

        Map<String, File> result = Maps.newLinkedHashMap();
        MainFramePage page = MBank.gotoLoginPage().setCustomer(username).setPassword(password).confirm();
        Iterable<String> accounts = page.getAccounts();
        for(String accountName : accounts) {
            TransactionHistoryPage historyPage = page.openTransactionHistory(accountName).clickLastDaysRadio().submit();
            List<TransactionDescription> transactions = Lists.newArrayList();
            for (;;) {
                Iterables.addAll(transactions, historyPage.getTransactions());
                if (historyPage.isPreviousPage()) {
                    historyPage = historyPage.clickPreviousPage();
                } else {
                    break;
                }
            }
            try {
                File output = File.createTempFile("MBank", ".qif");
                PrintWriter out = new PrintWriter(output);
                try {
                    Qif.write(out, accountName, transactions);
                } finally {
                    IOUtils.closeQuietly(out);
                }
                result.put(accountName, output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            break;
//            page = historyPage.goToFrames();
        }

        return result;
    }
}
