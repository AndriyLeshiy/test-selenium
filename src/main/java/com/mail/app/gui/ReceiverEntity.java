package com.mail.app.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.mail.sender.Receiver;

/**
 * Created by serg on 26.03.17.
 */
public class ReceiverEntity extends Receiver {
    private StringProperty email;
    private StringProperty name;
    private BooleanProperty isNotified;

    public ReceiverEntity(String email, String name) {
        super(email, name);
        isNotified = new SimpleBooleanProperty(this, "isNotified");
        isNotified.setValue(false);
    }

    public StringProperty getNameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(this, "name");
            name.setValue(getName());
        }
        return name;
    }

    public StringProperty getEmailProperty() {
        if (email == null) {
            email = new SimpleStringProperty(this, "email");
            email.setValue(getEmail());
        }
        return email;
    }

    public void markNotified() {
        isNotified.setValue(true);
    }

    public BooleanProperty isNotified() {
        return isNotified;
    }
}
