package com.avt.silvatrainer;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ContentWindowController {

    static final Map<String, String[]> odsGoalsPerObjective = new HashMap<>();
    static {
        odsGoalsPerObjective.put("1. Fin de la pobreza", new String[]{
            "1.1. Erradicar para todas las personas y en todo el mundo la pobreza extrema",
            "1.2. Reducir al menos a la mitad la proporción de hombres, mujeres y niños de todas las edades que viven en la pobreza en todas sus dimensiones con arreglo a las definiciones nacionales",
            "1.3. Implementar a nivel nacional sistemas y medidas apropiados de protección social para todos, incluidos niveles mínimos",
            "1.4. Garantizar que todos los hombres y mujeres, en particular los pobres y los vulnerables, tengan los mismos derechos a los recursos económicos y acceso a los servicios básicos, la propiedad y el control de la tierra y otros bienes, la herencia, los recursos naturales, las nuevas tecnologías apropiadas y los servicios financieros, incluida la microfinanciación",
            "1.5. Fomentar la resiliencia de los pobres y las personas que se encuentran en situaciones de vulnerabilidad y reducir su exposición y vulnerabilidad a los fenómenos extremos relacionados con el clima y otras perturbaciones y desastres económicos, sociales y ambientales",
            "1.a. Garantizar una movilización significativa de recursos procedentes de diversas fuentes, incluso mediante la mejora de la cooperación para el desarrollo, a fin de proporcionar medios suficientes y previsibles a los países en desarrollo, en particular los países menos adelantados, para que implementen programas y políticas encaminados a poner fin a la pobreza en todas sus dimensiones",
            "1.b. Crear marcos normativos sólidos en los planos nacional, regional e internacional, sobre la base de estrategias de desarrollo en favor de los pobres que tengan en cuenta las cuestiones de género, a fin de apoyar la inversión acelerada en medidas para erradicar la pobreza"
        });
        odsGoalsPerObjective.put("2. Hambre cero", new String[] {""});
        odsGoalsPerObjective.put("3. Salud y bienestar", new String[] {""});
        odsGoalsPerObjective.put("4. Educación de calidad", new String[] {""});
        odsGoalsPerObjective.put("5. Igualdad de género", new String[] {""});
        odsGoalsPerObjective.put("6. Agua limpia y saneamiento", new String[] {""});
        odsGoalsPerObjective.put("7. Energía asequible y no contaminante", new String[] {""});
        odsGoalsPerObjective.put("8. Trabajo decente y crecimiento económico", new String[] {""});
        odsGoalsPerObjective.put("9. Industria, innovación e infraestructura", new String[] {""});
        odsGoalsPerObjective.put("10. Reducción de las desigualdades", new String[] {""});
        odsGoalsPerObjective.put("11. Ciudades y comunidades sostenibles", new String[] {""});
        odsGoalsPerObjective.put("12. Producción y consumo responsables", new String[] {""});
        odsGoalsPerObjective.put("13. Acción por el clima", new String[] {""});
        odsGoalsPerObjective.put("14. Vida submarina", new String[] {""});
        odsGoalsPerObjective.put("15. Vida de ecosistemas terrestres", new String[] {""});
        odsGoalsPerObjective.put("16. Paz, justicia e instituciones sólidas", new String[] {""});
        odsGoalsPerObjective.put("17. Alianzas para lograr los objetivos", new String[] {""});
    }

    static final Map<String, String> odsGoals = new HashMap<>();
    static {
        odsGoals.put("1.1", "Erradicar para todas las personas y en todo el mundo la pobreza extrema");
        odsGoals.put("1.2", "Reducir al menos a la mitad la proporción de hombres, mujeres y niños de todas las edades que viven en la pobreza en todas sus dimensiones con arreglo a las definiciones nacionales");
        odsGoals.put("1.3", "Implementar a nivel nacional sistemas y medidas apropiados de protección social para todos, incluidos niveles mínimos");
        odsGoals.put("1.4", "Garantizar que todos los hombres y mujeres, en particular los pobres y los vulnerables, tengan los mismos derechos a los recursos económicos y acceso a los servicios básicos, la propiedad y el control de la tierra y otros bienes, la herencia, los recursos naturales, las nuevas tecnologías apropiadas y los servicios financieros, incluida la microfinanciación");
        odsGoals.put("1.5", "Fomentar la resiliencia de los pobres y las personas que se encuentran en situaciones de vulnerabilidad y reducir su exposición y vulnerabilidad a los fenómenos extremos relacionados con el clima y otras perturbaciones y desastres económicos, sociales y ambientales");
        odsGoals.put("1.a", "Garantizar una movilización significativa de recursos procedentes de diversas fuentes, incluso mediante la mejora de la cooperación para el desarrollo, a fin de proporcionar medios suficientes y previsibles a los países en desarrollo, en particular los países menos adelantados, para que implementen programas y políticas encaminados a poner fin a la pobreza en todas sus dimensiones");
        odsGoals.put("1.b", "Crear marcos normativos sólidos en los planos nacional, regional e internacional, sobre la base de estrategias de desarrollo en favor de los pobres que tengan en cuenta las cuestiones de género, a fin de apoyar la inversión acelerada en medidas para erradicar la pobreza");
        //[...]
    }

    @FXML
    TextField trainingPromptField;
    @FXML
    ChoiceBox<String> odsChoiceBox;
    @FXML
    Button trainingSendButton;
    @FXML
    VBox trainingChatPanel;
    @FXML
    ScrollPane trainingScrollPane;
    @FXML
    ChoiceBox<String> goalChoiceBox;


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

        /*
         * TRAINING
         */

        //Ponemos los objetivos ODS dentro del choice box
        odsChoiceBox.getItems().addAll(
                "1. Fin de la pobreza",
                "2. Hambre cero",
                "3. Salud y bienestar",
                "4. Educación de calidad",
                "5. Igualdad de género",
                "6. Agua limpia y saneamiento",
                "7. Energía asequible y no contaminante",
                "8. Trabajo decente y crecimiento económico",
                "9. Industria, innovación e infraestructura",
                "10. Reducción de las desigualdades",
                "11. Ciudades y comunidades sostenibles",
                "12. Producción y consumo responsables",
                "13. Acción por el clima",
                "14. Vida submarina",
                "15. Vida de ecosistemas terrestres",
                "16. Paz, justicia e instituciones sólidas",
                "17. Alianzas para lograr los objetivos"
        );
        odsChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Code to execute when the value of the ChoiceBox changes
            getGoalsByObjective(newValue);
        });
        odsChoiceBox.getSelectionModel().select(0);
        trainingSendButton.setOnAction(event -> sendTrainingSingle());
        trainingChatPanel.heightProperty().addListener((observable, oldValue, newValue) -> {
            // Desplazar el ScrollPane hasta el final
            trainingScrollPane.setVvalue(1.0);
        });
        trainingScrollPane.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 10; // Ajusta este valor para cambiar la velocidad
            double width = trainingScrollPane.getContent().getBoundsInLocal().getWidth();
            double hvalue = trainingScrollPane.getHvalue();
            trainingScrollPane.setHvalue(hvalue + -deltaY/width);
        });


        /*
         * PLAYGROUND
         */


        //Añadimos los controles para cuando se pulsa ENTER en los campos de texto
        playgroundPromptField.setOnAction(event -> playgroundSend());

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
        //Get message
        String message = playgroundPromptField.getText();

        //Create your message
        generateChatMessage(message, "you");

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
        cmd[3] = "\""+message+"\"";

        ProcessBuilder pb = new ProcessBuilder(cmd);
        System.out.println(cmd[0]+" "+cmd[1]+" "+cmd[2]+" "+cmd[3]);
        try {
            Process p = pb.start();

            BufferedReader inerror = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String errorline;
            while ((errorline = inerror.readLine()) != null) {
                System.err.println(errorline);
            }

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
            //Clear message field
            playgroundPromptField.setText("");

            //Create message
            HBox chatMessage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat-message.fxml")));
            Label text = (Label) chatMessage.lookup("#messageLabel");
            text.setText(message);
            //Set time
            Label messageTimeLabel = (Label) chatMessage.lookup("#playgroundMessageTimeLabel");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedDateTime = now.format(formatter);
            messageTimeLabel.setText(formattedDateTime);

            //Process message if is sent by the user or by the chat
            if (owner.equals("you")) {
                chatMessage.lookup("#chatLogoContainer").setVisible(false);
                chatMessage.lookup("#chatLogoContainer").setManaged(false);
                chatMessage.setAlignment(Pos.CENTER_RIGHT);
            } else {
                //Get objective in code
                String finalMessage = message+" - "+odsGoals.get(message);
                text.setText(finalMessage);

                VBox messagePanel = (VBox) chatMessage.lookup("#messagePanel");
                messagePanel.setStyle("-fx-background-color: #2271B3; -fx-background-radius: 10;");
                text.setStyle("-fx-text-fill: white;");
                //Typing animation
                animateText(text, finalMessage);
            }

            //Append message to chat
            playgroundChatPanel.getChildren().add(chatMessage);

            //Fade aimation
            FadeTransition ft = new FadeTransition(Duration.millis(2000), chatMessage);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            //Scale and rotate animation
            ImageView image = (ImageView) chatMessage.lookup("#playgroundLogoImage");
            // Crea una transición de escala para agrandar la imagen
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), image);
            scaleTransition.setFromX(0);
            scaleTransition.setFromY(0);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);

            // Crea una transición de rotación para rotar la imagen 360 grados
            image.setRotate(-45);
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5), image);
            rotateTransition.setByAngle(45);

            // Ejecuta ambas transiciones al mismo tiempo
            scaleTransition.play();
            rotateTransition.play();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void animateText(Label label, String text) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(50*text.length()));
            }
            protected void interpolate(double frac) {
                final int length = text.length();
                final int n = Math.round(length * (float) frac);
                label.setText(text.substring(0, n));
            }
        };
        animation.play();
    }

    private void sendTrainingSingle() {
        if (trainingPromptField.getText().equals("")) {
            return;
        }

        //Get prompt
        String prompt = trainingPromptField.getText();

        //Create your message
        generateTrainingMessage("Training model with >>>>> "+prompt+" <<<<< with label >>>>> "+odsChoiceBox.getSelectionModel().getSelectedItem(), "you");

        //Process response
        String pythonScriptPath = "src/main/python/silva/core/silva.py";
        String[] cmd = new String[5];
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            // Código específico para Windows
            cmd[0] = "src/main/python/silva/core/.venv/Scripts/python.exe";
        } else {
            // Código específico para Linux
            cmd[0] = "src/main/python/silva/core/.venv/bin/python3";
        }
        cmd[1] = pythonScriptPath;
        cmd[2] = "-t";
        cmd[3] = "\""+prompt+"\"";
        cmd[4] = "\""+odsChoiceBox.getSelectionModel().getSelectedItem()+"\"";

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
                    Platform.runLater(()->generateTrainingMessage(response.toString(), "chat"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generateTrainingMessage(String message, String owner) {
        try {
            //Clear message field
            trainingPromptField.setText("");

            //Create message
            HBox chatMessage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("chat-message.fxml")));
            Label text = (Label) chatMessage.lookup("#messageLabel");
            text.setText(message);
            //Set time
            Label messageTimeLabel = (Label) chatMessage.lookup("#playgroundMessageTimeLabel");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedDateTime = now.format(formatter);
            messageTimeLabel.setText(formattedDateTime);

            //Process message if is sent by the user or by the chat
            if (owner.equals("you")) {
                chatMessage.lookup("#chatLogoContainer").setVisible(false);
                chatMessage.lookup("#chatLogoContainer").setManaged(false);
                chatMessage.setAlignment(Pos.CENTER_RIGHT);
            } else {
                VBox messagePanel = (VBox) chatMessage.lookup("#messagePanel");
                messagePanel.setStyle("-fx-background-color: #2271B3; -fx-background-radius: 10;");
                text.setStyle("-fx-text-fill: white;");
                //Typing animation
                animateText(text, message);
            }
            //Append message to chat
            trainingChatPanel.getChildren().add(chatMessage);

            //Fade aimation
            FadeTransition ft = new FadeTransition(Duration.millis(2000), chatMessage);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            //Scale and rotate animation
            ImageView image = (ImageView) chatMessage.lookup("#playgroundLogoImage");
            // Crea una transición de escala para agrandar la imagen
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), image);
            scaleTransition.setFromX(0);
            scaleTransition.setFromY(0);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);

            // Crea una transición de rotación para rotar la imagen 360 grados
            image.setRotate(-45);
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5), image);
            rotateTransition.setByAngle(45);

            // Ejecuta ambas transiciones al mismo tiempo
            scaleTransition.play();
            rotateTransition.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getGoalsByObjective(String objective) {
        String[] goals = odsGoalsPerObjective.get(objective);
        goalChoiceBox.getItems().clear();
        for (String goal:goals) {
            goalChoiceBox.getItems().add(goal);
        }
        if (goalChoiceBox.getItems().size()>0) {
            goalChoiceBox.getSelectionModel().select(0);
        }
    }


}
