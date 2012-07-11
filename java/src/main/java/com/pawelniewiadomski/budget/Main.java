package com.pawelniewiadomski.budget;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, File> files = new MBankClient().downloadOperationsHistory("53376795", "Croissant3k");
        for(Map.Entry<String, File> file : files.entrySet()) {
            System.out.print(file.getKey());
            System.out.print('!');
            System.out.println(file.getValue());
        }
    }
}
