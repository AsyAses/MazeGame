module com.example.labirint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labirint to javafx.fxml;
    exports com.example.labirint;
}