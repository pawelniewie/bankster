package com.pawelniewiadomski.budget.banks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import net.sf.ofx4j.domain.data.common.TransactionType;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * TODO: Document this class / interface here
 *
 * @since v5.0.1
 */
public interface TransactionTypeMapper {

    @Nonnull
    TransactionType getTransactionType(@Nonnull String description);

}
