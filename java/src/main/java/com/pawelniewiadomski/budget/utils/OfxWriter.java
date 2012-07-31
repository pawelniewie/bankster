package com.pawelniewiadomski.budget.utils;

import net.sf.ofx4j.domain.data.ResponseEnvelope;
import net.sf.ofx4j.io.AggregateMarshaller;
import net.sf.ofx4j.io.v2.OFXV2Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OfxWriter {

    public static void writeOfx(File output, ResponseEnvelope response) throws IOException {
        final OFXV2Writer ofxWriter = new OFXV2Writer(new FileWriter(output));
        ofxWriter.setWriteAttributesOnNewLine(true);
        AggregateMarshaller marshaller = new AggregateMarshaller();
        try {
            marshaller.marshal(response, ofxWriter);
        } finally {
            ofxWriter.close();
        }
    }

}
