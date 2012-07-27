package com.pawelniewiadomski.budget.utils;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import net.sf.ofx4j.domain.data.common.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v5.0.1
 */
public class OfxFactory {

    public static Transaction createTransaction(DateTime opDate, DateTime acDate, String opDesc, double v) {
        Transaction t = new Transaction();
        t.setAmount(v);
        t.setDateInitiated(opDate.toDate());
        t.setDatePosted(opDate.toDate());
        t.setDateAvailable(acDate.toDate());
        t.setTransactionType(TransactionType.DEBIT);
        t.setName(opDesc);
        t.setId(createId(t));
        return t;
    }

    private static String createId(Transaction t) {
        String toSign = "" + t.getAmount() + t.getDateInitiated() + t.getDatePosted() + t.getDateAvailable() + t.getTransactionType() + t.getName();
        return DigestUtils.sha256Hex(toSign);
    }

    public static TransactionList createTransactionList(List<Transaction> transactions) {
        Date startDate = Ordering.natural().min(Iterables.transform(transactions, new Function<Transaction, Date>() {
            @Override
            public Date apply(@Nullable Transaction from) {
                return from.getDateInitiated();
            }
        }));
        Date endDate = Ordering.natural().max(Iterables.transform(transactions, new Function<Transaction, Date>() {
            @Override
            public Date apply(@Nullable Transaction from) {
                return from.getDateAvailable();
            }
        }));

        TransactionList tl = new TransactionList();
        tl.setEnd(endDate);
        tl.setStart(startDate);
        tl.setTransactions(transactions);
        return tl;
    }

    public static Status okStatus() {
        Status s = new Status();
        s.setCode(Status.KnownCode.SUCCESS);
        return s;
    }
}
