package com.avt.silvatrainer;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class ContentWindowController {

    @FXML
    ChoiceBox<String> odsChoiceBox;

    @FXML
    Button playgroundSendButton;
    @FXML
    TextField playgroundPromptField;
    @FXML
    VBox playgroundChatPanel;

    @FXML
    ScrollPane playgroundScrollPane;

    @FXML
    protected void initialize() {
        //Ponemos los objetivos ODS dentro del choice box
        odsChoiceBox.getItems().addAll("salud", "hambre cero");
        odsChoiceBox.getSelectionModel().select(0);

        //Añadimos los controles de los botones
        playgroundSendButton.setOnAction(event -> playgroundSend());
        playgroundChatPanel.heightProperty().addListener((observable, oldValue, newValue) -> {
            // Desplazar el ScrollPane hasta el final
            playgroundScrollPane.setVvalue(1.0);
        });
        playgroundScrollPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 10; // Ajusta este valor para cambiar la velocidad
            double width = playgroundScrollPane.getContent().getBoundsInLocal().getWidth();
            double hvalue = playgroundScrollPane.getHvalue();
            playgroundScrollPane.setHvalue(hvalue + -deltaY/width);
        });
    }

    private void playgroundSend() {
        //Create your message
        generateChatMessage(playgroundPromptField.getText(), "you");

        //Process response
        String pythonScriptPath = "src/main/python/silva/core/silva.py";
        String[] cmd = new String[4];
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            // Código específico para Windows
            cmd[0] = "src/main/python/silva/core/.venv/Scripts/python.exe";
        } else {
            // Código específico para Linux
            cmd[0] = "src/main/python/silva/core/.venv/bin/python3";
        }
        cmd[1] = pythonScriptPath;
        cmd[2] = "-p";
        cmd[3] = "\""+playgroundPromptField.getText()+"\"";

        ProcessBuilder pb = new ProcessBuilder(cmd);
        System.out.println(cmd[0]+" "+cmd[1]+" "+cmd[2]+" "+cmd[3]);
        try {
            Process p = pb.start();

            //BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            //String line;
            //while ((line = in.readLine()) != null) {
            //    System.err.println(line);
            //}

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
                System.out.println(line);
            }
            in.close();

            //Generate chat message
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(()->generateChatMessage(response.toString(), "chat"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateChatMessage(String message, String owner) {
        try {
            //Create message
            HBox chatMessage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat-message.fxml")));
            Label text = (Label) chatMessage.lookup("#messageLabel");
            text.setText(message);
            if (owner.equals("you")) {
                chatMessage.setAlignment(Pos.CENTER_RIGHT);
            } else {
                VBox messagePanel = (VBox) chatMessage.lookup("#messagePanel");
                messagePanel.setStyle("-fx-background-color: #2271B3; -fx-background-radius: 10;");
                text.setStyle("-fx-text-fill: white;");
            }
            //Append message to chat
            playgroundChatPanel.getChildren().add(chatMessage);
            FadeTransition ft = new FadeTransition(Duration.millis(2000), chatMessage);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
