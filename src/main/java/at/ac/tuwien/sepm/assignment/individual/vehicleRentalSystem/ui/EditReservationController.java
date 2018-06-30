package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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

/**
 * controller class for the Edit reservation
 */
public class EditReservationController {
    /**
     * LOG is a Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @FXML
    private AnchorPane rootpane;
    /**
     * A TextField, which accept the clientName of a reservation.
     */
    @FXML
    private TextField clientName;
    /**
     * A DatePicker,shows the start date of a reservation..
     */
    @FXML
    private DatePicker startDate;
    /**
     * A DatePicker, shows the end date of a reservation.
     */
    @FXML
    private DatePicker endDate;
    /**
     * A TextField, which accept the start time of a reservation.
     */
    @FXML
    private TextField startTime;
    /**
     * A TextField, which accept the end time of a reservation.
     */
    @FXML
    private TextField endTime;
    /**
     * A TextField, which accept the Payment of a reservation.
     */
    @FXML
    private TextField payment;
    /**
     * A choicebox, which accept either IBAN or credit card
     */
    @FXML
    private ChoiceBox<String> choose = new ChoiceBox<String>();
    /**
     * A TableView of Vehicle, which every Vehicle will be showed in it.
     */
    @FXML
    private TableView<Vehicle> VehicleTable;
    /**
     * A column, shows the title of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> title;
    /**
     * A column, shows the year of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> year;
    /**
     * A column, shows the licenseClass of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> licenseClass;
    /**
     * A column, shows the typeOfDrive of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, String> typeOfDrive;
    /**
     * A column, shows the price of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, Integer> price;
    /**
     * A column, shows the seats of a vehicle.
     */
    @FXML
    private TableColumn<Vehicle, Integer> seats;
    /**
     * A TextArea are to hold information
     */
    @FXML
    private TextArea textArea;
    /**
     * A TextField are to hold LicenseNumber
     */
    @FXML
    private TextField LicenseNumber;
    /**
     * A DatePicker are to hold dateOfIssue
     */
    @FXML
    private DatePicker dateOfIssue;
    //all data
    private ObservableList<Vehicle> data = FXCollections.observableArrayList();
    private ObservableList<Vehicle> tesst = FXCollections.observableArrayList();
    private ObservableList<Vehicle> selectedItems;
    private ObservableList<Vehicle> addedVehicles = FXCollections.observableArrayList();
    ;
    private Reservation reservation;
    private VehicleService vehicleService = new VehicleServiceImpl();
    private ReservationsService reservationsService = new ReservationServieceImpl();
    private AlertMaker alertMaker = new AlertMaker();

    //constructor
    public EditReservationController() {

    }

    /**
     * constructor for EditReservationController
     *
     * @param reservationsService , instance of vehicle service
     * @param reservation         instance of reservation
     */
    public EditReservationController(ReservationsService reservationsService, Reservation reservation) {
        this.reservationsService = reservationsService;
        this.reservation = reservation;
    }

    /**
     * Initializes fills the table of reservation and the lable of informations.
     */
    @FXML
    public void initialize() {
        choose.getItems().addAll("IBAN", "Credit Card");
        choose.getSelectionModel().selectFirst();

        LOG.info("ReservationEditWindowController: initialize");
        clientName.setText(reservation.getClientName() + "");

        // if (reservation.getPayment())
        startDate.setValue(reservation.getStart().toLocalDateTime().toLocalDate());
        endDate.setValue(reservation.getEnd().toLocalDateTime().toLocalDate());
        startTime.setText(reservation.getStart().toLocalDateTime().getHour() + ":" + reservation.getStart().toLocalDateTime().getMinute() + ":" + reservation.getStart().toLocalDateTime().getSecond());
        endTime.setText(reservation.getEnd().toLocalDateTime().getHour() + ":" + reservation.getEnd().toLocalDateTime().getMinute() + ":" + reservation.getEnd().toLocalDateTime().getSecond());
        payment.setText(reservation.getPayment());
        try {
            textArea.setText(String.valueOf(vehicleService.getVehicleEvenIfIsDeleted(reservation.getVehicleID())));
        } catch (ServiceException e) {
            e.printStackTrace();
        }


        try {
            for (Vehicle v : vehicleService.getVehicles()) {
                if (!vehicleService.checkVehicleExistbyDate(reservation.getStart(), reservation.getEnd(), v.getId())) {
                    VehicleTable.getItems().add(v);
                    LOG.info(v.getId() + "");
                    data.add(v);
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

//initialize table columns
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        year.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getYear() + ""));
        licenseClass.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLicenseClass()));
        typeOfDrive.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTypeOfDrive()));
        price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        seats.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSeats()));


        VehicleTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );


    }

    /**
     * cancle a editing and goes back to reservation table .
     *
     * @param actionEvent is the event
     */
    public void CancelButton(ActionEvent actionEvent) {
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


    /*after ok, the selected rows will be added to reservation*/
    public void ok(ActionEvent actionEvent) {
        LOG.debug("Reservation Edit Window Controller: updateReservation {}");


        reservation.setClientName(clientName.getText());
        reservation.setPayment(payment.getText());
        //calculate the total price.
        if (!addedVehicles.isEmpty()) {
            Timestamp startTimeStamp = Timestamp.valueOf(startDate.getValue() + " " + startTime.getText());
            Timestamp endTimeStamp = Timestamp.valueOf(endDate.getValue() + " " + endTime.getText());

            int total = 0;
            for (Vehicle vehicle : addedVehicles) {
                total += reservationsService.priceCalculation(startTimeStamp, endTimeStamp, vehicle);
                LOG.info(vehicle.getId() + ":" + total);
            }

            LOG.info("last" + total + "");
            alertMaker.InformationAlert("Money total", total + reservation.getTotalPrice() + "");

            reservation.setTotalPrice(total + reservation.getTotalPrice());
        }


        try {
            reservationsService.editReservation(reservation);
        } catch (NotFoundException e) {
            LOG.error("NotFound." + e.getMessage());
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

    }

    public ObservableList<Vehicle> getList(ObservableList<Vehicle> ste) {
        String s = "";
        int counter = 1;
        for (Vehicle v : ste) {
            s += counter + ": " + v.toString();
            counter++;
        }
        textArea.setText(s);
        this.tesst = ste;
        return tesst;
    }

    /*after add, the selected rows will be added to reservation*/
    public void add(ActionEvent actionEvent) {
        ObservableList<Vehicle> selectedItems = VehicleTable.getSelectionModel().getSelectedItems();
        for (Vehicle v : selectedItems) {
            try {
                if (vehicleService.search(v.getId())) {
                    LOG.debug("found vehicle to be added: {}", v);
                    int vechID = v.getId();
                    //if vehicle needs driving license , this two field should be filled.

                    if (!checkForLicense(vechID).equals("") || checkForLicense(vechID) != null && checkForLicense(vechID).length() >= 1) {
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
                    addedVehicles.add(v);
                    //price and total
                    reservationsService.add(reservation, v);
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * check if vehicle needs driving license
     */
    private String checkForLicense(int id) {
        String s = "";
        LOG.info("Checking for License {}", id);
        try {
            s = vehicleService.getOne(id).getLicenseClass();
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        return s;
    }

}
