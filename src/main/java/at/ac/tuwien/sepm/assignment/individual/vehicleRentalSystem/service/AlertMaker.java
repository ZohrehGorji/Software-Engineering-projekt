package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service;

import javafx.scene.control.Alert;

/*
* class alertMaker create different instance of Alert to be used in different classes and methodes.
* */
public class AlertMaker {
    /**
     * create an instance of Alert in form of ERROR.
     * @param context is the context showing in the alarmbox.
     * @param header   is header of alarmbox
     */
    public void ErrorAlert(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(context);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    /**
     * create an instance of Alert in form of WARNING.
     * @param context is the context showing in the alarmbox.
     * @param header   is header of alarmbox
     */
    public void WarningAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(context);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    /**
     * create an instance of Alert in form of INFORMATION.
     * @param context is the context showing in the alarmbox.
     * @param header   is header of alarmbox
     */
    public void InformationAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(context);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    /**
     * create an instance of Alert in form of CONFIRMATION.
     * @param context is the context showing in the alarmbox.
     * @param header   is header of alarmbox
     */
    public void ConfirmationAlert(String header, String context)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(context);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
