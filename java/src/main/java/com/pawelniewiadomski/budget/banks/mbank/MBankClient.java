package com.pawelniewiadomski.budget.banks.mbank;

import com.atlassian.pageobjects.ProductInstance;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pawelniewiadomski.budget.utils.OfxFactory;
import com.pawelniewiadomski.budget.utils.Qif;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.domain.data.ResponseMessageSet;
import net.sf.ofx4j.domain.data.banking.*;
import net.sf.ofx4j.domain.data.common.Transaction;
import net.sf.ofx4j.domain.data.common.TransactionList;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MBankClient {

    public ResponseEnvelope downloadOperationsHistory(@Nonnull String username, @Nonnull String password) {
        MBankTestedProduct MBank = new MBankTestedProduct(new MBankProductInstance());

        Map<String, File> result = Maps.newLinkedHashMap();
        MainFramePage page = MBank.gotoLoginPage().setCustomer(username).setPassword(password).confirm();
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

            if (transactions.isEmpty()) {
                continue;
            }

            TransactionList transactionList = OfxFactory.createTransactionList(transactions);
            BankAccountDetails account = new BankAccountDetails();
            account.setAccountNumber(accountName);
            account.setAccountType(AccountType.CHECKING);
            account.setBankId("BREXPLPWMBK");

            BankStatementResponse response = new BankStatementResponse();
            response.setAccount(account);
            response.setCurrencyCode("PLN");
            response.setTransactionList(transactionList);

            BankStatementResponseTransaction responseTransaction = new BankStatementResponseTransaction();
            responseTransaction.setMessage(response);
            responseTransaction.setStatus(OfxFactory.okStatus());
            responseTransaction.setUID(UUID.randomUUID().toString());

            responses.add(responseTransaction);
            page = historyPage.goToFrames();
            break;
        }

        BankingResponseMessageSet messageSet = new BankingResponseMessageSet();
        messageSet.setStatementResponses(responses);

        ResponseEnvelope envelope = new ResponseEnvelope();
        envelope.setMessageSets(ImmutableSortedSet.<ResponseMessageSet>of(messageSet));

        MBank.getTester().getDriver().quit();
        return envelope;
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
