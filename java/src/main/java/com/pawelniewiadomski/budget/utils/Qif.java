package com.pawelniewiadomski.budget.utils;

import com.pawelniewiadomski.budget.pageobjects.mbank.TransactionDescription;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.util.Locale;

public class Qif {

    public static void write(@Nonnull PrintWriter os, @Nonnull String accountName, @Nonnull Iterable<TransactionDescription> transactions) {
        writeHeader(os, accountName);
        for(TransactionDescription transaction : transactions) {
            os.print('D'); os.println(transaction.getAccountDate().toString("dd/MM/YY", Locale.ENGLISH));
            os.print('T'); os.println(Double.toString(transaction.getAmount()));
            os.print('M'); os.println(StringUtils.replace(transaction.getOperationDescription(), "\n", " "));
            os.println('^');
        }
    }

    private static void writeHeader(PrintWriter os, String accountName) {
        os.println("!Account");
        os.print('N'); os.println(accountName);
        os.println('^');
        os.println("!Type:Cash");
    }

}
