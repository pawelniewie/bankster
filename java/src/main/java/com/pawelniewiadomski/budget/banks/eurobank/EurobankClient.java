package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pawelniewiadomski.budget.banks.mbank.MBankTestedProduct;
import com.pawelniewiadomski.budget.banks.mbank.MainFramePage;
import com.pawelniewiadomski.budget.banks.mbank.TransactionDescription;
import com.pawelniewiadomski.budget.banks.mbank.TransactionHistoryPage;
import com.pawelniewiadomski.budget.utils.Qif;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class EurobankClient {

    public Map<String, File> downloadOperationsHistory(@Nonnull String username, @Nonnull String password, @Nonnull String token) {
        EurobankTestedProduct bank = new EurobankTestedProduct(new EurobankProductInstance());

        Map<String, File> result = Maps.newLinkedHashMap();
        MainPage page = bank.gotoLoginPage().setCustomer(username).setPassword(password).setToken(token).confirm();
        Iterable<String> accounts = page.getAccounts();
        for(String accountName : accounts) {
//            TransactionHistoryPage historyPage = page.openTransactionHistory(accountName).clickLastDaysRadio().submit();
//            List<TransactionDescription> transactions = Lists.newArrayList();
//            for (;;) {
//                Iterables.addAll(transactions, historyPage.getTransactions());
//                if (historyPage.isPreviousPage()) {
//                    historyPage = historyPage.clickPreviousPage();
//                } else {
//                    break;
//                }
//            }
            try {
                File output = File.createTempFile("bank", ".qif");
//                PrintWriter out = new PrintWriter(output);
//                try {
//                    Qif.write(out, accountName, transactions);
//                } finally {
//                    IOUtils.closeQuietly(out);
//                }
                result.put(accountName, output);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            page = historyPage.goToFrames();
        }

        return result;
    }

    private static class EurobankProductInstance implements ProductInstance {
        @Override
        public String getBaseUrl() {
            return "https://online.eurobank.pl/";
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
            return "Eurobank";
        }
    }
}
