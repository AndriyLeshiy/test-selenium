package com.mail.app.gui;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller implements Initializable {
    private Executor executor = Executors.newFixedThreadPool(4,
                                                             r -> {
                                                                 Thread t = Executors.defaultThreadFactory().newThread(r);
                                                                 t.setDaemon(true);
                                                                 return t;
                                                             });
    public TextField     username;
    public PasswordField password;
    public TextField     publicPassword;
    public CheckBox      showPasswordButton;

    public WebView   templatePreview;
    public String    templateBody;
    public TextField subjectInput;

    public TextField testReceiverNameInput;
    public TextField testReceiverEmailInput;
    public Button    testSend;

    public TableView<ReceiverEntity>            receiversTable;
    public TableColumn<ReceiverEntity, String>  receiversEmails;
    public TableColumn<ReceiverEntity, Boolean> isNotifiedColumn;
    public TableColumn<ReceiverEntity, String>  receiversNames;

    public ObservableList<ReceiverEntity> receiversList;

    public Button loadReceiversButton;
    public Button loadTemplateButton;
    public Button sendButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set initial state
        publicPassword.setManaged(false);
        publicPassword.setVisible(false);

        publicPassword.managedProperty().bind(showPasswordButton.selectedProperty());
        publicPassword.visibleProperty().bind(showPasswordButton.selectedProperty());

        password.managedProperty().bind(showPasswordButton.selectedProperty().not());
        password.visibleProperty().bind(showPasswordButton.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        publicPassword.textProperty().bindBidirectional(password.textProperty());

        subjectInput.setText("Hello");

        loadTemplateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберіть шаблон");
            Node source = (Node)event.getSource();
            Window theStage = source.getScene().getWindow();
            File selected = fileChooser.showOpenDialog(theStage);
            if (selected != null) {
                templateBody = ResourcesLoader.loadFile(selected.getAbsolutePath());
                templatePreview.getEngine().loadContent(templateBody);
            }
        });

        templateBody = "Hello $name$.";
        templatePreview.getEngine().loadContent(templateBody);

        testSend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Template template = new Template(templateBody);
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

        receiversEmails.setCellValueFactory(p -> p.getValue().getEmailProperty());
        receiversNames.setCellValueFactory(p -> p.getValue().getNameProperty());

        isNotifiedColumn.setCellFactory(tc -> new CheckBoxTableCell<>());
        isNotifiedColumn.setCellValueFactory(f -> f.getValue().isNotified());

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

        receiversList.add(new ReceiverEntity("blabla@test.com", "Dear"));

        sendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                executor.execute(() -> {
                    Template template = new Template(templateBody);

                    try (Gmail gmail = new Gmail(username.getText(), password.getText())) {
                        for (ReceiverEntity receiver : receiversList) {
                            gmail.send(receiver.getEmail(), subjectInput.getText(), template.build(receiver));
                            executor.execute(receiver::markNotified);
                            Thread.sleep(5000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
