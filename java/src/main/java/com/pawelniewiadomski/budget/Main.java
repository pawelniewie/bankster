package com.pawelniewiadomski.budget;

import com.pawelniewiadomski.budget.banks.eurobank.EurobankClient;
import com.pawelniewiadomski.budget.banks.mbank.MBankClient;
import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.io.AggregateMarshaller;
import net.sf.ofx4j.io.OFXWriter;
import net.sf.ofx4j.io.v2.OFXV2Writer;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, File> results;
        if (args.length == 2) {
            results = new MBankClient().downloadOperationsHistory(args[0], args[1]);
        } else {
            results = new EurobankClient().downloadOperationsHistory(args[0], args[1], args[2]);
        }

        System.out.println(results.toString());
    }
}
