package com.mail.sender;

import java.io.Closeable;

/**
 * Created by serg on 26.03.17.
 */
public interface MailSender extends Closeable {
    void send(String email, String body);
}
