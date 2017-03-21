package com.google.app;

import com.google.pages.Gmail;
import com.google.util.Pair;
import com.google.util.ResourcesLoader;

/**
 * Created by serg on 21.03.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        try (Gmail gmail = new Gmail()) {
            gmail.login();

            for (Pair receiver : ResourcesLoader.loadReceivers()) {
                gmail.sendEmail(receiver.getFirst(), receiver.getSecond());
                Thread.sleep(5000);
            }
        }
    }
}
