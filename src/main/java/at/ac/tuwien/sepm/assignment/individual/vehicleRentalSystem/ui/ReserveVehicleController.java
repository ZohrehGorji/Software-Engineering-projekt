package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.*;

public class ReserveVehicleController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private AnchorPane rootpane;
    /**
     * A TextField, which accept/shows the clientName of a reservation.
     */
    @FXML
    private TextField clientName;
    /**
     * A TextField, which accept/shows the payment of a reservation.
     */
    @FXML
    private TextField payment;
    /**
     * A DatePicker, which accept/shows the start date of a reservation.
     */
    @FXML
    private DatePicker startDate;
    /**
     * A DatePicker, which accept/shows the end date of a reservation.
     */
    @FXML
    private DatePicker endDate;
    /**
     * A TextField, which accept/shows the start time of a reservation.
     */
    @FXML
    private TextField startTime;
    /**
     * A TextField, which accept/shows the end time of a reservation.
     */
    @FXML
    private TextField endTime;
    /**
     * A ChoiceBox, which is either IBAN or credit card of a reservation.
     */
    @FXML
    private ChoiceBox<String> choose;
    /**
     * A TextArea, which is showing the information of choosed vehicles.
     */
    @FXML
    private TextArea textArea;
    /**
     * A TextArea, for the license number , in case the driving license is mandatory .
     */
    @FXML
    private TextField LicenseNumber;
    /**
     * A DatePicker, for the date of issue of driving license , in case the driving license is mandatory .
     */
    @FXML
    private DatePicker dateOfIssue;
    /**
     * An instance of vehicleService, for all the services from vehicle.
     */
    private VehicleService vehicleService = new VehicleServiceImpl();
    /**
     * An instance of reservationsService, for all the services from reservation.
     */
    private ReservationsService reservationsService = new ReservationServieceImpl();
    /**
     * An observationlist of vehicles that comes from filechooser (choosed vehicle to be added in reservation)
     */
    private ObservableList<Vehicle> listOfChoosenVehicles = FXCollections.observableArrayList();
    /**
     * An observationlist of vehicles that comes from filechooser (choosed vehicle to be added in reservation)
     */
    private ObservableList<Vehicle> selectedItems;
    /**
     * booking id stays the same through reservation, reservation id however changes after each vehicle
     */
    private int bookingID = 1;
    /**
     * an instance of alertmaker
     */
    private AlertMaker alertMaker = new AlertMaker();

    /**
     * Initializes fills the choose with IBAN or credit card options
     */
    @FXML
    public void initialize() {
        choose.getItems().addAll("IBAN", "Credit Card");
        choose.getSelectionModel().selectFirst();
    }

    /*constructor*/
    public ReserveVehicleController() {

    }

    /**
     * constructor
     *
     * @param reservationsService an instance of service
     */
    public ReserveVehicleController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    /**
     * reserv vehicles into the reservation with given information
     *  @param actionEvent is the event
     */
    public void draft(ActionEvent actionEvent) {
        LOG.info("draft a Vehicle");


        if (clientName.getText().equals("") || startTime.getText() == null || endTime.getText() == null || startDate.getValue() == null || endDate.getValue() == null) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Warning");
            alert1.setHeaderText("Invalid selection!");
            alert1.setContentText("please Fill all necessery Fields!");
            alert1.showAndWait();
            return;
        }
        try {

            Timestamp startTimeStamp = Timestamp.valueOf(startDate.getValue() + " " + startTime.getText());
            Timestamp endTimeStamp = Timestamp.valueOf(endDate.getValue() + " " + endTime.getText());


            if (selectedItems == null) {
                alertMaker.WarningAlert("", "choose autos! ");
            } else {
                for (Vehicle v : selectedItems) {
                    int vechID = v.getId();
                    //if vehicle needs driving license , this two field should be filled.

                    if (checkForLicense(vechID) != null && checkForLicense(vechID).length() >= 1) {
                        if (checkForLicense(vechID).contains("A") || checkForLicense(vechID).contains("C")) {
                            if (dateOfIssue.getValue() == null) {
                                alertMaker.WarningAlert("License", "fill both date of issue");
                                return;
                            }
                            if (LicenseNumber.getText() == null || LicenseNumber.getText().equals("")) {
                                alertMaker.WarningAlert("License", "fill Licence Number");
                            }
                            Timestamp issue = Timestamp.valueOf(dateOfIssue.getValue() + " " + "12:00:00");
                            int l_number = Integer.parseInt(LicenseNumber.getText());
                            int dreijahr = 365 * 24 * 60 * 60 * 1000;

                            if (System.currentTimeMillis() - issue.getTime() < dreijahr) {
                                alertMaker.WarningAlert("License", "issue Date not ok! ");
                                return;
                            }
                        }
                    }
                }
            }
            //calculate the total price.
            int total = 0;
            for (Vehicle v : listOfChoosenVehicles) {
                total += reservationsService.priceCalculation(startTimeStamp, endTimeStamp, v);
                LOG.info(v.getId() + ":" + total);
            }

            LOG.info("last" + total + "");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("total money");
            alert.setHeaderText(total + "");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int chech = 0;
                bookingID = reservationsService.getBookingID();
                bookingID++;
                for (Vehicle vehicle : selectedItems) {
                    chech++;
                    Reservation reservation = Reservation.builder()

                        .price(vehicle.getPrice())
                        .vehicleID(vehicle.getId())
                        .end(endTimeStamp)
                        .start(startTimeStamp)
                        .Payment(payment.getText())
                        .status("open")
                        .clientName(clientName.getText())
                        .build();
                   // Reservation reservation = new Reservation(clientName.getText(), payment.getText(), startTimeStamp, endTimeStamp, vehicle.getId(), vehicle.getPrice(), "open");
                   reservation.setArtOFPayment(choose.getValue());
                    LOG.error(startTimeStamp.toString() + endTimeStamp.toString());
                    reservation.setStatus(choose.getSelectionModel().getSelectedItem().toString());
                    reservation.setTotalPrice(total);
                    //alarm if a free cancellation is possible
                    if (!reservationsService.checkForCancleFree(reservation).equals("Reservation Cancellation is free of Cost!")) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setTitle("Cancelation Cost Information!");
                        a.setHeaderText("cancelation free of cost is not possible for this time !");
                        a.setContentText("Are you ok with this?");

                        Optional<ButtonType> r = a.showAndWait();
                        if (!(r.get() == ButtonType.OK)) {
                            return;
                        }
                    }

                    reservationsService.saveReservationonID(reservation, bookingID);
                }
            }
        } catch (NullPointerException n) {
            return;
        } catch (NumberFormatException n) {
            LOG.error(n.getLocalizedMessage());
        } catch (ServiceException e) {
            LOG.info(e.getMessage());
            alertMaker.ErrorAlert("Error", e.getMessage());
        }

    }

    /**
     * cancle button , change stage back to EmployeeMenuController.
     *  @param actionEvent is the event
     ***/
    public void CancelButton(ActionEvent actionEvent) {
        EmployeeMenuController employeeMenuController = new EmployeeMenuController(reservationsService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeMainMenu.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(employeeMenuController) ? employeeMenuController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }

    /**
     * check if vehicle needs driving license
     */
    private String checkForLicense(int id) throws ServiceException {
        String s = "";
        LOG.info("Checking for License {}", id);
        return vehicleService.getOne(id).getLicenseClass();
    }

    /**
     * opens the vehicle chooser so the user can get vehicles
     *  @param actionEvent is the event
     */
    public void vTableShow(ActionEvent actionEvent)  {

        Stage stageEdit = new Stage();
        stageEdit.setTitle("Vehicle");
        stageEdit.centerOnScreen();
        stageEdit.setOnCloseRequest(event -> LOG.debug(" Show Table"));
        stageEdit.setWidth(900);
        stageEdit.setHeight(500);
        VehicleChooserController VehicleChooserController = new VehicleChooserController(vehicleService, this);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/VehicleChooser.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(VehicleChooserController) ? VehicleChooserController : null);

        stageEdit.toFront();

        stageEdit.show();
        try {
            LOG.info("Opening Choosing Window!");
            stageEdit.setScene(new Scene(fxmlLoader.load()));
            selectedItems = VehicleChooserController.getData();
            int p = 0;


        } catch (IOException e) {
          LOG.error(  e.getMessage());
        }
    }

    /**
     * get the choosen vehicle and show them in textview
     * @return  observable list
     * @param ste is the parameter
     */
    public ObservableList<Vehicle> getList(ObservableList<Vehicle> ste) {
        String s = "";
        int counter = 1;
        LOG.info(ste.size() + "");
        for (Vehicle v : ste) {
            s += counter + ": " + v.toString();
            counter++;
        }
        textArea.setText(s);

        this.listOfChoosenVehicles = ste;
        return listOfChoosenVehicles;
    }


}
