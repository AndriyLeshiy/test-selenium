package com.google.app;

import com.google.util.Pair;
import com.google.util.ResourcesLoader;

/**
 * Created by serg on 21.03.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        for (Pair receiver : ResourcesLoader.loadReceivers()) {
            try (MailSender sender = new MailSender()) {
                sender.sendEmail(receiver.getFirst(), receiver.getSecond());
                Thread.sleep(5000);
            }
        }
    }
}
