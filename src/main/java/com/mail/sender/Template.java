package com.mail.sender;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

import java.util.Objects;

/**
 * Created by serg on 26.03.17.
 */
public class Template {
    private String body;

    public Template(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String build(Receiver receiver) {
        StringTemplate hello = new StringTemplate(body, DefaultTemplateLexer.class);
        hello.setAttribute("name", receiver.getName());
        return hello.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template)o;
        return Objects.equals(body, template.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public String toString() {
        return "Template{" +
               "body='" + body + '\'' +
               '}';
    }
}
