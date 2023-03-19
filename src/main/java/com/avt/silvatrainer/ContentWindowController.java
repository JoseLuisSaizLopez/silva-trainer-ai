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
        //[Obj 1]
        odsGoals.put("1.1", "Erradicar para todas las personas y en todo el mundo la pobreza extrema");
        odsGoals.put("1.2", "Reducir al menos a la mitad la proporción de hombres, mujeres y niños de todas las edades que viven en la pobreza en todas sus dimensiones con arreglo a las definiciones nacionales");
        odsGoals.put("1.3", "Implementar a nivel nacional sistemas y medidas apropiados de protección social para todos, incluidos niveles mínimos");
        odsGoals.put("1.4", "Garantizar que todos los hombres y mujeres, en particular los pobres y los vulnerables, tengan los mismos derechos a los recursos económicos y acceso a los servicios básicos, la propiedad y el control de la tierra y otros bienes, la herencia, los recursos naturales, las nuevas tecnologías apropiadas y los servicios financieros, incluida la microfinanciación");
        odsGoals.put("1.5", "Fomentar la resiliencia de los pobres y las personas que se encuentran en situaciones de vulnerabilidad y reducir su exposición y vulnerabilidad a los fenómenos extremos relacionados con el clima y otras perturbaciones y desastres económicos, sociales y ambientales");
        odsGoals.put("1.a", "Garantizar una movilización significativa de recursos procedentes de diversas fuentes, incluso mediante la mejora de la cooperación para el desarrollo, a fin de proporcionar medios suficientes y previsibles a los países en desarrollo, en particular los países menos adelantados, para que implementen programas y políticas encaminados a poner fin a la pobreza en todas sus dimensiones");
        odsGoals.put("1.b", "Crear marcos normativos sólidos en los planos nacional, regional e internacional, sobre la base de estrategias de desarrollo en favor de los pobres que tengan en cuenta las cuestiones de género, a fin de apoyar la inversión acelerada en medidas para erradicar la pobreza");
        //[Obj 2]
        odsGoals.put("2.1", "Poner fin al hambre y asegurar el acceso de todas las personas, en particular los pobres y las personas en situaciones de vulnerabilidad, a una alimentación sana, nutritiva y suficiente durante todo el año");
        odsGoals.put("2.2", "Poner fin a todas las formas de malnutrición, incluso logrando las metas convenidas internacionalmente sobre el retraso del crecimiento y la emaciación de los niños menores de 5 años, y abordar las necesidades de nutrición de las adolescentes, las mujeres embarazadas y lactantes y las personas de edad");
        odsGoals.put("2.3", "Duplicar la productividad agrícola y los ingresos de los productores de alimentos en pequeña escala, en particular las mujeres, los pueblos indígenas, los agricultores familiares, los ganaderos y los pescadores, entre otras cosas mediante un acceso seguro y equitativo a las tierras, a otros recursos e insumos de producción y a los conocimientos, los servicios financieros, los mercados y las oportunidades para añadir valor y obtener empleos no agrícolas");
        odsGoals.put("2.4", "Asegurar la sostenibilidad de los sistemas de producción de alimentos y aplicar prácticas agrícolas resilientes que aumenten la productividad y la producción, contribuyan al mantenimiento de los ecosistemas, fortalezcan la capacidad de adaptación al cambio climático, los fenómenos meteorológicos extremos, las sequías, las inundaciones y otros desastres, y mejoren progresivamente la calidad de la tierra y el suelo");
        odsGoals.put("2.5", "Mantener la diversidad genética de las semillas, las plantas cultivadas y los animales de granja y domesticados y sus correspondientes especies silvestres, entre otras cosas mediante una buena gestión y diversificación de los bancos de semillas y plantas a nivel nacional, regional e internacional, y promover el acceso a los beneficios que se deriven de la utilización de los recursos genéticos y los conocimientos tradicionales conexos y su distribución justa y equitativa, según lo convenido internacionalmente");
        odsGoals.put("2.a", "Aumentar, incluso mediante una mayor cooperación internacional, las inversiones en infraestructura rural, investigación y servicios de extensión agrícola, desarrollo tecnológico y bancos de genes de plantas y ganado a fin de mejorar la capacidad de producción agropecuaria en los países en desarrollo, particularmente en los países menos adelantados");
        odsGoals.put("2.b", "Corregir y prevenir las restricciones y distorsiones comerciales en los mercados agropecuarios mundiales, incluso mediante la eliminación paralela de todas las formas de subvención a las exportaciones agrícolas y todas las medidas de exportación con efectos equivalentes, de conformidad con el mandato de la Ronda de Doha para el Desarrollo");
        odsGoals.put("2.c", "Adoptar medidas para asegurar el buen funcionamiento de los mercados de productos básicos alimentarios y sus derivados y facilitar el acceso oportuno a la información sobre los mercados, incluso sobre las reservas de alimentos, a fin de ayudar a limitar la extrema volatilidad de los precios de los alimentos");
        //[Obj 3]
        odsGoals.put("3.1", "Reducir la tasa mundial de mortalidad materna a menos de 70 por cada 100.000 nacidos vivos");
        odsGoals.put("3.2", "Poner fin a las muertes evitables de recién nacidos y de niños menores de 5 años, logrando que todos los países intenten reducir la mortalidad neonatal al menos a 12 por cada 1.000 nacidos vivos y la mortalidad de los niños menores de 5 años al menos a 25 por cada 1.000 nacidos vivos");
        odsGoals.put("3.3", "Poner fin a las epidemias del SIDA, la tuberculosis, la malaria y las enfermedades tropicales desatendidas y combatir la hepatitis, las enfermedades transmitidas por el agua y otras enfermedades transmisibles");
        odsGoals.put("3.4", "Reducir en un tercio la mortalidad prematura por enfermedades no transmisibles mediante su prevención y tratamiento, y promover la salud mental y el bienestar");
        odsGoals.put("3.5", "Fortalecer la prevención y el tratamiento del abuso de sustancias adictivas, incluido el uso indebido de estupefacientes y el consumo nocivo de alcohol");
        odsGoals.put("3.6", "Reducir a la mitad el número de muertes y lesiones causadas por accidentes de tráfico en el mundo");
        odsGoals.put("3.7", "Garantizar el acceso universal a los servicios de salud sexual y reproductiva, incluidos los de planificación familiar, información y educación, y la integración de la salud reproductiva en las estrategias y los programas nacionales");
        odsGoals.put("3.8", "Lograr la cobertura sanitaria universal, incluida la protección contra los riesgos financieros, el acceso a servicios de salud esenciales de calidad y el acceso a medicamentos y vacunas inocuos, eficaces, asequibles y de calidad para todos");
        odsGoals.put("3.9", "Reducir considerablemente el número de muertes y enfermedades causadas por productos químicos peligrosos y por la polución y contaminación del aire, el agua y el suelo");
        odsGoals.put("3.a", "Fortalecer la aplicación del Convenio Marco de la Organización Mundial de la Salud para el Control del Tabaco en todos los países, según proceda");
        odsGoals.put("3.b", "Apoyar las actividades de investigación y desarrollo de vacunas y medicamentos contra las enfermedades transmisibles y no transmisibles que afectan primordialmente a los países en desarrollo y facilitar el acceso a medicamentos y vacunas esenciales asequibles de conformidad con la Declaración relativa al Acuerdo sobre los Aspectos de los Derechos de Propiedad Intelectual Relacionados con el Comercio y la Salud Pública, en la que se afirma el derecho de los países en desarrollo a utilizar al máximo las disposiciones del Acuerdo sobre los Aspectos de los Derechos de Propiedad Intelectual Relacionados con el Comercio respecto a la flexibilidad para proteger la salud pública y, en particular, proporcionar acceso a los medicamentos para todos");
        odsGoals.put("3.c", "Aumentar considerablemente la financiación de la salud y la contratación, el perfeccionamiento, la capacitación y la retención del personal sanitario en los países en desarrollo, especialmente en los países menos adelantados y los pequeños Estados insulares en desarrollo");
        odsGoals.put("3.d", "Reforzar la capacidad de todos los países, en particular los países en desarrollo, en materia de alerta temprana, reducción de riesgos y gestión de los riesgos para la salud nacional y mundial");
        //[Obj 4]
        odsGoals.put("4.1", "Asegurar que todas las niñas y todos los niños terminen la enseñanza primaria y secundaria, que ha de ser gratuita, equitativa y de calidad y producir resultados de aprendizaje pertinentes y efectivos");
        odsGoals.put("4.2", "Asegurar que todas las niñas y todos los niños tengan acceso a servicios de atención y desarrollo en la primera infancia y educación preescolar de calidad, a fin de que estén preparados para la enseñanza primaria");
        odsGoals.put("4.3", "Asegurar el acceso igualitario de todos los hombres y las mujeres a una formación técnica, profesional y superior de calidad, incluida la enseñanza universitaria");
        odsGoals.put("4.4", "Aumentar considerablemente el número de jóvenes y adultos que tienen las competencias necesarias, en particular técnicas y profesionales, para acceder al empleo, el trabajo decente y el emprendimiento");
        odsGoals.put("4.5", "Eliminar las disparidades de género en la educación y asegurar el acceso igualitario a todos los niveles de la enseñanza y la formación profesional para las personas vulnerables, incluidas las personas con discapacidad, los pueblos indígenas y los niños en situaciones de vulnerabilidad");
        odsGoals.put("4.6", "Asegurar que todos los jóvenes y una proporción considerable de los adultos, tanto hombres como mujeres, estén alfabetizados y tengan nociones elementales de aritmética");
        odsGoals.put("4.7", "Asegurar que todos los alumnos adquieran los conocimientos teóricos y prácticos necesarios para promover el desarrollo sostenible, entre otras cosas mediante la educación para el desarrollo sostenible y los estilos de vida sostenibles, los derechos humanos, la igualdad de género, la promoción de una cultura de paz y no violencia, la ciudadanía mundial y la valoración de la diversidad cultural y la contribución de la cultura al desarrollo sostenible");
        odsGoals.put("4.a", "Construir y adecuar instalaciones educativas que tengan en cuenta las necesidades de los niños y las personas con discapacidad y las diferencias de género, y que ofrezcan entornos de aprendizaje seguros, no violentos, inclusivos y eficaces para todos");
        odsGoals.put("4.b", "Aumentar considerablemente a nivel mundial el número de becas disponibles para los países en desarrollo, en particular los países menos adelantados, los pequeños Estados insulares en desarrollo y los países africanos, a fin de que sus estudiantes puedan matricularse en programas de enseñanza superior, incluidos programas de formación profesional y programas técnicos, científicos, de ingeniería y de tecnología de la información y las comunicaciones, de países desarrollados y otros países en desarrollo");
        odsGoals.put("4.c", "Aumentar considerablemente la oferta de docentes calificados, incluso mediante la cooperación internacional para la formación de docentes en los países en desarrollo, especialmente los países menos adelantados y los pequeños Estados insulares en desarrollo");
        //[Obj 5]
        odsGoals.put("5.1", "Poner fin a todas las formas de discriminación contra todas las mujeres y las niñas en todo el mundo");
        odsGoals.put("5.2", "Eliminar todas las formas de violencia contra todas las mujeres y las niñas en los ámbitos público y privado, incluidas la trata y la explotación sexual y otros tipos de explotación");
        odsGoals.put("5.3", "Eliminar todas las prácticas nocivas, como el matrimonio infantil, precoz y forzado y la mutilación genital femenina");
        odsGoals.put("5.4", "Reconocer y valorar los cuidados y el trabajo doméstico no remunerados mediante servicios públicos, infraestructuras y políticas de protección social, y promoviendo la responsabilidad compartida en el hogar y la familia, según proceda en cada país");
        odsGoals.put("5.5", "Asegurar la participación plena y efectiva de las mujeres y la igualdad de oportunidades de liderazgo a todos los niveles decisorios en la vida política, económica y pública");
        odsGoals.put("5.6", "Asegurar el acceso universal a la salud sexual y reproductiva y los derechos reproductivos según lo acordado de conformidad con el Programa de Acción de la Conferencia Internacional sobre la Población y el Desarrollo, la Plataforma de Acción de Beijing y los documentos finales de sus conferencias de examen");
        odsGoals.put("5.a", "Emprender reformas que otorguen a las mujeres igualdad de derechos a los recursos económicos, así como acceso a la propiedad y al control de la tierra y otros tipos de bienes, los servicios financieros, la herencia y los recursos naturales, de conformidad con las leyes nacionales");
        odsGoals.put("5.b", "Mejorar el uso de la tecnología instrumental, en particular la tecnología de la información y las comunicaciones, para promover el empoderamiento de las mujeres");
        odsGoals.put("5.c", "Aprobar y fortalecer políticas acertadas y leyes aplicables para promover la igualdad de género y el empoderamiento de todas las mujeres y las niñas a todos los niveles");
        //[Obj 6]
        odsGoals.put("6.1", "");
        odsGoals.put("6.2", "");
        odsGoals.put("6.3", "");
        odsGoals.put("6.4", "");
        odsGoals.put("6.5", "");
        odsGoals.put("6.a", "");
        odsGoals.put("6.b", "");
        odsGoals.put("6.c", "");
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

        //Get goal point
        String label = "";
        for (Map.Entry<String, String> objective:odsGoals.entrySet()) {
            if (goalChoiceBox.getSelectionModel().getSelectedItem().contains(objective.getValue())) {
                label = objective.getKey();
                break;
            }
        }

        //Replace fatal characters
        prompt = prompt.replace(";", "").replace("\"", "");
        label = label.replace(";", "").replace("\"", "");

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
        cmd[4] = "\""+label+"\"";

        ProcessBuilder pb = new ProcessBuilder(cmd);
        System.out.println(cmd[0]+" "+cmd[1]+" "+cmd[2]+" "+cmd[3]+" "+cmd[4]);
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
