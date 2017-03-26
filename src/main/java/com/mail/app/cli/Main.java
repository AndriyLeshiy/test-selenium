package com.mail.app.cli;

import com.mail.sender.MailSender;
import com.mail.sender.Receiver;
import com.mail.sender.Template;
import com.mail.sender.gmail.Gmail;
import com.mail.sender.util.ResourcesLoader;

import java.io.IOException;
import java.util.List;

/**
 * Created by serg on 21.03.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Receiver> receivers = ResourcesLoader.loadReceivers();
        Template template = new Template(ResourcesLoader.loadTemplate());
        try (MailSender mailSender = new Gmail()) {
            for (Receiver receiver : receivers) {
                mailSender.send(receiver.getEmail(), template.build(receiver));
                Thread.sleep(5000);
            }
        }
    }
}
