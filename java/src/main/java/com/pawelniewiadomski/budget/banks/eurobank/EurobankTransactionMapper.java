package com.pawelniewiadomski.budget.banks.eurobank;

import com.pawelniewiadomski.budget.banks.TransactionTypeMapper;
import net.sf.ofx4j.domain.data.common.TransactionType;

import javax.annotation.Nonnull;

/**
 * TODO: Document this class / interface here
 *
 * @since v5.0.1
 */
public class EurobankTransactionMapper implements TransactionTypeMapper {
    @Nonnull
    @Override
    public TransactionType getTransactionType(@Nonnull String description) {
        return TransactionType.DEBIT;
    }
}
