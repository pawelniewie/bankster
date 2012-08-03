package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.ProductInstance;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pawelniewiadomski.budget.utils.OfxFactory;
import com.pawelniewiadomski.budget.utils.OfxWriter;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.banking.*;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.common.TransactionList;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MBankClient {

    public ResponseEnvelope downloadOperationsHistory(@Nonnull String username, @Nonnull String password) throws IOException {
        MBankTestedProduct bank = new MBankTestedProduct(new MBankProductInstance());
        try {
            MainFramePage page = bank.gotoLoginPage().setCustomer(username).setPassword(password).confirm();
            Iterable<String> accounts = page.getAccounts();
            List<BankStatementResponseTransaction> responses = Lists.newArrayList();

            for(String accountName : accounts) {
                TransactionHistoryPage historyPage = page.openTransactionHistory(accountName).clickLastDaysRadio().submit();
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
                    response.setAccount(OfxFactory.createBankAccountDetails(getAccountNumber(accountName), "BREXPLPWMBK"));
                    response.setCurrencyCode("PLN");
                    response.setTransactionList(transactionList);

                    responses.add(OfxFactory.createBankStatementResponseTransaction(response));
                }

                page = historyPage.goToFrames();
            }

            return OfxFactory.createResponseEnvelope(responses);
        } finally {
            bank.getTester().getDriver().quit();
        }
    }

    @Nonnull
    private String getAccountNumber(@Nonnull String accountName) {
        return accountName;
    }

    private static class MBankProductInstance implements ProductInstance {
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
    }
}
