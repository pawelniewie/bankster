package com.pawelniewiadomski.budget.banks.eurobank;

import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.elements.query.Poller;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pawelniewiadomski.budget.utils.OfxFactory;
import com.pawelniewiadomski.budget.utils.OfxWriter;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.banking.BankStatementResponse;
import net.sf.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.common.TransactionList;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EurobankClient {

    public ResponseEnvelope downloadOperationsHistory(@Nonnull String username, @Nonnull String password) throws IOException {
        EurobankTestedProduct bank = new EurobankTestedProduct(new EurobankProductInstance());
        try {
            LoginPage page = bank.gotoLoginPage().setCustomer(username).clickNext();
            Poller.waitUntilTrue(page.isPasswordVisible());
            MainPage bankPage = page.setPassword(password).confirm();
            Iterable<String> accounts = bankPage.getAccounts();
            List<BankStatementResponseTransaction> responses = Lists.newArrayList();

            for(String accountName : accounts) {
                TransactionHistoryPage historyPage = bankPage.openTransactionHistory(accountName).submit();
                List<Transaction> transactions = Lists.newArrayList();
                for (;;) {
                    Iterables.addAll(transactions, historyPage.getTransactions());
                    if (historyPage.isPreviousPage()) {
                        historyPage = historyPage.clickPreviousPage();
                    } else {
                        break;
                    }
                }

                if (!transactions.isEmpty()) {
                    TransactionList transactionList = OfxFactory.createTransactionList(transactions);

                    BankStatementResponse response = new BankStatementResponse();
                    response.setAccount(OfxFactory.createBankAccountDetails(StringUtils.replace(accountName, " ", ""), "EFGBPLPW"));
                    response.setCurrencyCode("PLN");
                    response.setTransactionList(transactionList);

                    responses.add(OfxFactory.createBankStatementResponseTransaction(response));
                }

                bankPage = historyPage.goToMain();
            }
            return OfxFactory.createResponseEnvelope(responses);
        } finally {
            bank.getTester().getDriver().quit();
        }
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
