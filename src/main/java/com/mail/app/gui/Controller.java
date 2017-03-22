package com.mail.app.gui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import com.mail.sender.Receiver;
import com.mail.sender.Template;
import com.mail.sender.gmail.Gmail;
import com.mail.sender.util.ResourcesLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField username;
    public TextField password;

    public ObservableList<Receiver>             receivers;
    public TableView<ReceiverEntity>            receiversTable;
    public TableColumn<ReceiverEntity, String>  receiversEmails;
    public TableColumn<ReceiverEntity, Boolean> isNotifiedColumn;
    public TableColumn<ReceiverEntity, String>  receiversNames;

    public ObservableList<ReceiverEntity> receiversList;

    public String templatePath;
    public Button loadReceiversButton;
    public Button loadTemplateButton;

    public Button sendButton;

    public WebView templatePreview;

    public TextField subjectInput;
    public TextField testReceiverNameInput;
    public TextField testReceiverEmailInput;
    public Button    testSend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        receiversEmails.setCellValueFactory(p -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return p.getValue().getEmailProperty();
        });
        receiversNames.setCellValueFactory(p -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return p.getValue().getNameProperty();
        });

        isNotifiedColumn.setCellValueFactory(f -> f.getValue().isNotified());
        isNotifiedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        receiversList = receiversTable.getItems();

        loadReceiversButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберіть отримувачів");
            Node source = (Node)event.getSource();
            Window theStage = source.getScene().getWindow();
            File selected = fileChooser.showOpenDialog(theStage);
            if (selected != null) {
                Properties props = new Properties();
                try {
                    props.load(new FileInputStream(selected));
                } catch (IOException e) {
                    throw new RuntimeException("");
                }

                for (String email : props.stringPropertyNames()) {
                    String name = props.getProperty(email);
                    receiversList.add(new ReceiverEntity(email, name));
                }
            }
        });

        loadTemplateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберіть шаблон");
            Node source = (Node)event.getSource();
            Window theStage = source.getScene().getWindow();
            File selected = fileChooser.showOpenDialog(theStage);
            if (selected != null) {
                templatePath = selected.getAbsolutePath();
                templatePreview.getEngine().loadContent(ResourcesLoader.loadFile(templatePath));
            }
        });

        sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Template template = new Template(ResourcesLoader.loadFile(templatePath));
                try (Gmail gmail = new Gmail(username.getText(), password.getText())) {
                    for (ReceiverEntity receiver : receiversList) {
                        gmail.send(receiver.getEmail(), subjectInput.getText(), template.build(receiver));
                        receiver.markNotified();
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        testSend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Template template = new Template(ResourcesLoader.loadFile(templatePath));
                try (Gmail gmail = new Gmail(username.getText(), password.getText())) {
                    Receiver testReceiver = new Receiver(testReceiverEmailInput.getText(),
                                                         testReceiverNameInput.getText());
                    gmail.send(testReceiver.getEmail(), subjectInput.getText(), template.build(testReceiver));
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}