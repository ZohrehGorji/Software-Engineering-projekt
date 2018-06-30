package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

/**
 * controller class for the Employee Menu Controller stage
 */
public class EmployeeMenuController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private AnchorPane rootpane;
    /**
     * A service for handling ReservationsService persistence methods.
     */
    private ReservationsService reservationsService;

    /**
     * constructor for the EmployeeMenuController
     *
     * @param reservationsService is the instance of service.
     */
    EmployeeMenuController(ReservationsService reservationsService) {

        this.reservationsService = reservationsService;
    }

    /**
     * closes the aplication and send a request to service to send to the dao to close the Database.
     * @param actionEvent is the event
     */
    public void Exit(ActionEvent actionEvent) {
        reservationsService.close();
        Platform.exit();

    }

    /**
     * return to the main menu  by creating another instance of MainViewController.
     * @param actionEvent is the event
     * **/

    public void backToMain(ActionEvent actionEvent)  {
        MainViewController mainViewController = new MainViewController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(mainViewController) ? mainViewController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }

    /**
     * Handles the making of the new Reservation, an instance of ReserveVehicleController will be created and all the possibility to make a new reservation.
     * @param actionEvent is the event
     * */

    public void makeAReservation(ActionEvent actionEvent) {

        ReserveVehicleController reserveVehicleController = new ReserveVehicleController(reservationsService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ReserveVehicle.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(reserveVehicleController) ? reserveVehicleController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }

    /**
     * Handles the showing of Reservations, an instance of ShowReservationTableController will be created and all the Reservations will be showing up in table.
     * @param actionEvent is the event
     * */
    public void showAllTheReservations(ActionEvent actionEvent)   {
        ShowReservationTableController showReservationTableController = new ShowReservationTableController(reservationsService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowReservationTable.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(showReservationTableController) ? showReservationTableController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }
}
