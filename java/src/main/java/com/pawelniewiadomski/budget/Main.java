package com.pawelniewiadomski.budget;

import com.pawelniewiadomski.budget.banks.eurobank.EurobankClient;
import com.pawelniewiadomski.budget.banks.mbank.MBankClient;

import java.io.File;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
//        Map<String, File> files = new MBankClient().downloadOperationsHistory("53376795", "Croissant3k");
        Map<String, File> files = new EurobankClient().downloadOperationsHistory("660836822", "Croissant3k", "714729");
        for(Map.Entry<String, File> file : files.entrySet()) {
            System.out.print(file.getKey());
            System.out.print('!');
            System.out.println(file.getValue());
        }
    }
}
