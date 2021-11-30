module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires lombok;

    opens com.pz35.carfactory.controller to javafx.fxml;
    opens com.pz35.carfactory.view to javafx.fxml;
    opens com.pz35.carfactory to javafx.fxml;
    opens com.pz35.carfactory.entities to javafx.base;
    opens com.pz35.carfactory.logic to javafx.base;
    opens com.pz35.carfactory.factories to javafx.base;
    exports com.pz35.carfactory;
}