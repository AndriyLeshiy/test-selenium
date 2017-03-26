package com.mail.sender;

import java.util.Objects;

/**
 * Created by serg on 26.03.17.
 */
public class Receiver {
    private String email;
    private String name;

    public Receiver(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Receiver)) return false;

        Receiver receiver = (Receiver)o;

        return Objects.equals(email, receiver.email)
               && Objects.equals(name, receiver.name);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Receiver{" +
               "email='" + email + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
