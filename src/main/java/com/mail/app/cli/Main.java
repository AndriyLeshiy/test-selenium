package com.mail.app.cli;

import com.mail.sender.MailSender;
import com.mail.sender.Receiver;
import com.mail.sender.Template;
import com.mail.sender.gmail.Gmail;

import java.io.IOException;
import java.util.List;

/**
 * Created by serg on 21.03.17.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<Receiver> receivers = ConfLoader.loadReceivers();
        String username = ConfLoader.loadProperty("user.name");
        String password = ConfLoader.loadProperty("user.password");

        Template template = new Template(ConfLoader.loadTemplate());
        String subject = ConfLoader.loadProperty("email.subject");
        try (MailSender mailSender = new Gmail(username, password)) {
            for (Receiver receiver : receivers) {
                mailSender.send(receiver.getEmail(), subject, template.build(receiver));
                Thread.sleep(5000);
            }
        }
    }
}
