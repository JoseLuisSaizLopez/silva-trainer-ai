module com.avt.silvatrainer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.avt.silvatrainer to javafx.fxml;
    exports com.avt.silvatrainer;
}