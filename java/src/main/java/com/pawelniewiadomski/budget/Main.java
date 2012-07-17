package com.pawelniewiadomski.budget;

import com.pawelniewiadomski.budget.banks.eurobank.EurobankClient;
import com.pawelniewiadomski.budget.banks.mbank.MBankClient;

import java.io.File;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, File> files;
        if (args.length == 2) {
            files = new MBankClient().downloadOperationsHistory(args[0], args[1]);
        } else {
            files = new EurobankClient().downloadOperationsHistory(args[0], args[1], args[2]);
        }
        for(Map.Entry<String, File> file : files.entrySet()) {
            System.out.print(file.getKey());
            System.out.print('!');
            System.out.println(file.getValue());
        }
    }
}
