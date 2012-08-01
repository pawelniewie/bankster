package com.pawelniewiadomski.budget.banks.mbank;

import com.google.common.collect.ImmutableMap;
import com.pawelniewiadomski.budget.banks.TransactionTypeMapper;
import com.pawelniewiadomski.budget.utils.OfxFactory;
import net.sf.ofx4j.domain.data.common.TransactionType;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * TODO: Document this class / interface here
 *
 * @since v5.0.1
 */
public class MbankTransactionMapper implements TransactionTypeMapper {

    private static final Map<String, TransactionType> descriptions = ImmutableMap.<String, TransactionType>builder()
            .put("ZERWANIE LOKATY TERMINOWEJ", TransactionType.DEP)
            .put("ODSETKI", TransactionType.INT)
            .put("OPLATA", TransactionType.SRVCHG)
            .put("PODATEK", TransactionType.DEBIT)
            .put("KAPITALIZACJA", TransactionType.INT)
            .put("ABONAMENT", TransactionType.REPEATPMT)
            .put("PROWIZJE", TransactionType.SRVCHG)
            .put("PROWIZJA", TransactionType.SRVCHG)
            .put("PRZELEW", TransactionType.XFER)
            .put("WYPLATA", TransactionType.CASH)
            .put("ZAKUP", TransactionType.DEBIT)
            .put("OPL. ZA ZLECENIE STALE", TransactionType.SRVCHG)
            .put("KREDYT - SPLATA RATY", TransactionType.DEBIT)
            .put("KREDYT-SKLADKA ZA UBEZPIECZENIE", TransactionType.DEBIT)
            .put("POS ZWROT TOWARU", TransactionType.CREDIT)
            .put("SKLADKA", TransactionType.REPEATPMT)
            .put("RECZNA SPLATA KARTY KREDYT", TransactionType.DEBIT)
            .put("AUTOMATYCZNA SPLATA KARTY", TransactionType.DEBIT)
            .put("STORNO OBCIAZENIA", TransactionType.CREDIT)
            .put("KREDYT - WCZESNIEJSZA SPLATA", TransactionType.CREDIT)
            .put("WPLATA WE WPLATOMACIE", TransactionType.CREDIT)
            .put("KREDYT - UZNANIE", TransactionType.CREDIT)
            .put("KREDYT - PROW. URUCHOMIENIE", TransactionType.SRVCHG)
            .build();

    public TransactionType getTransactionType(@Nonnull String description) {
        description = OfxFactory.toAscii(description);
        for (Map.Entry<String, TransactionType> t : descriptions.entrySet()) {
            if (StringUtils.startsWith(description, t.getKey())) {
                return t.getValue();
            }
        }
        return TransactionType.DEBIT;
    }
}
