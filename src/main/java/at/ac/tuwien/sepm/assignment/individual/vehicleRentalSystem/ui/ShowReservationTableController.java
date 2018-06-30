package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;


import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.AlertMaker;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * controller class for the Show reservation Table stage
 */
public class ShowReservationTableController {
    //all the data
    ObservableList<Reservation> data = FXCollections.observableArrayList();
    /**
     * LOG is a Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private AnchorPane rootpane;
    /**
     * A Label, which accept the Payment of a reservation.
     */
    @FXML
    private Label Paymentlabel_IBAN_Creditcart;
    /**
     * A Label, which accept the Price of a reservation.
     */
    @FXML
    private Label pricelebel;
    /**
     * A Label, which accept the totalPrice of a reservation.
     */
    @FXML
    private Label totalPricelabel;
    /**
     * A Label, which accept the vehicle_id of a reservation.
     */
    @FXML
    private Label vehicle_idlabel;
    /**
     * A TableView of reservation, which every reservation will be showed in it.
     */
    @FXML
    private TableView<Reservation> ReservationTable;
    /**
     * A column, shows the clientName of a reservation.
     */
    @FXML
    private TableColumn<Reservation, String> clientName;
    /**
     * A column, shows the start date of a reservation.
     */
    @FXML
    private TableColumn<Reservation, String> start;
    /**
     * A column, shows the end date of a reservation.
     */
    @FXML
    private TableColumn<Reservation, String> end;
    /**
     * A column, shows the start time of a reservation.
     */
    @FXML
    private TableColumn<Reservation, String> startTime;
    /**
     * A column, shows the end time of a reservation.
     */
    @FXML
    private TableColumn<Reservation, String> endTime;
    /**
     * A column, shows the bookingid of a reservation. (this id stays the same until the reservation end. that is how
     * user can make multiple vehicle reservation for the same time)
     */
    @FXML
    private TableColumn<Reservation, String> bookingID;
    /**
     * A column, shows the status of a reservation. (open,closed and canceled)
     */
    @FXML
    private TableColumn<Reservation, String> status;

    /**
     * an instance of reservationservice, handel all services.
     */
    private ReservationsService reservationsService;
    @FXML
    private Button canclebutton;
    @FXML
    private Button editbutton;
    /**
     * an instance of class alarmmaker
     */
    AlertMaker alertMaker = new AlertMaker();
    //array that help to show each reservation uniqe,
    private ArrayList<Integer> listofcheckingforuniqueentry = new ArrayList<>();
    //an instance of class reservation
    private Reservation reservation;

    public ShowReservationTableController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    /**
     * Initializes fills the table of reservation and the side of informations.
     */
    @FXML
    public void initialize() {
        //update table on database and check if vehicle has changed to change it then refresh the database and after that show it.
        try {
            for (Reservation r : reservationsService.getReservation()) {
                if (ReservationTable.getItems().isEmpty()) {
                    listofcheckingforuniqueentry.add(r.getReservation_secondID());
                    ReservationTable.getItems().add(r);
                } else {
                    if (!listofcheckingforuniqueentry.contains(r.getReservation_secondID())) {
                        listofcheckingforuniqueentry.add(r.getReservation_secondID());
                        ReservationTable.getItems().add(r);
                    } else {
                        LOG.info("ELEMENT ALREADY EXIST");
                    }
                }
            }
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        } catch (NotFoundException e) {
            LOG.error(e.getMessage());
        }
        bookingID.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getReservation_secondID())));
        clientName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientName()));
        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        start.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>("" + cellData.getValue().getStart().toLocalDateTime().toLocalDate()));
        end.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>("" + cellData.getValue().getEnd().toLocalDateTime().toLocalDate()));
        startTime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((cellData.getValue().getStart().toLocalDateTime().getHour()) + ":" + (cellData.getValue().getStart().toLocalDateTime().getMinute()) + ":" + (cellData.getValue().getStart().toLocalDateTime().getSecond())));
        endTime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((cellData.getValue().getEnd().toLocalDateTime().getHour()) + ":" + (cellData.getValue().getEnd().toLocalDateTime().getMinute()) + ":" + (cellData.getValue().getEnd().toLocalDateTime().getSecond())));
        ReservationTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        ReservationTable.getSelectionModel().selectedItemProperty().addListener((((observable, oldValue, newValue) -> showInformation(newValue))));

    }

    /**
     * editing the reservation information, new instance of reservationEditWindowController will be created.
     *
     * @param actionEvent is the event
     ***/
    public void EditInformationen(ActionEvent actionEvent) {
        if (ReservationTable.getSelectionModel().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Warning");
            alert1.setHeaderText("Invalid selection!");
            alert1.setContentText("select a row !");
            alert1.showAndWait();
        } else {

            EditReservationController editReservationController = new EditReservationController(reservationsService, reservation);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditReservationWindow.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(editReservationController) ? editReservationController : null);
            AnchorPane pane = null;
            try {
                pane = fxmlLoader.load();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
            rootpane.getChildren().setAll(pane);

        }
    }

    /**
     * shows all the information of a given reservation
     *
     * @param reservation to show the information
     */
    private void showInformation(Reservation reservation) {

        //showing payment
        CreditCardValidator creditCardValidator = new CreditCardValidator();
        String art = "";
        if (reservation.getArtOFPayment() == null) {
            if (new IBANCheckDigit().isValid(reservation.getPayment())) {
                art = "IBAN";
            } else if (creditCardValidator.isValid(reservation.getPayment())) {
                art = "Credit Crd";
            }
        } else {
            art = reservation.getArtOFPayment();
        }

        Paymentlabel_IBAN_Creditcart.setText(art + ":" + reservation.getPayment());

        try {
            vehicle_idlabel.setText(reservationsService.listOFVehicleIDsWithTheSameBookingID(reservation.getReservation_secondID()).toString() + "");
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
//price getting

        //price and total price
        ArrayList<Integer> p = new ArrayList<>();

        try {
            for (Integer i : reservationsService.getPrice(reservation.getReservation_secondID())) {

                p.add(i);
            }
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        pricelebel.setText(p.toString());
        int sum = 0;
        for (Integer i : p) {
            sum += i;
        }
        totalPricelabel.setText(sum + "");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());

        if (ReservationTable.getSelectionModel().getSelectedItem().getStart().before(currentTimestamp)) {
            canclebutton.setDisable(true);
            editbutton.setDisable(true);
        } else {
            canclebutton.setDisable(false);
            editbutton.setDisable(false);
        }
        ;


        this.reservation = reservation;
    }

    /**
     * cancle a vehicle. and chang status from open to canceled.
     *
     * @param actionEvent is the event
     ***/
    public void cancleTheReservation(ActionEvent actionEvent) {
        ObservableList<Reservation> selectedItems = ReservationTable.getSelectionModel().getSelectedItems();
        String name = "";
        Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
        alert5.setTitle("Warning");
        for (int i = 0; i < selectedItems.size(); i++) {
            name += selectedItems.get(i).getClientName() + " " + selectedItems.get(i).getVehicleID() + "\n";
        }
        alert5.setHeaderText("found Reservation to be canceled:\n" + name);
        alert5.setContentText("Are you sure you want to cancele these reservation? \n" + name);
        Optional<ButtonType> result = alert5.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (Reservation r : selectedItems) {
                try {
                    if (reservationsService.search(r.getReservation_id())) {
                        LOG.info("found reservation to be canceled: {}", r);
                        if (!reservationsService.checkForCancleFree(reservation).equals("Reservation Cancellation is not possible!")) {

                            alertMaker.ErrorAlert("Warning", reservationsService.checkForCancleFree(reservation));
                            reservationsService.cancle(r);
                        } else {
                            alertMaker.ErrorAlert("Warning", reservationsService.checkForCancleFree(reservation));
                        }
                    }
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                } catch (NotFoundException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

        LOG.info("show ShowReservationTableController after cancelation, refresh table");
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

    /**
     * return to the main menu  by creating another instance of MainViewController.
     *
     * @param actionEvent is the event
     ***/
    public void backToMain(ActionEvent actionEvent) {
        LOG.info("from ShowReservationTable enter MitarbeiterMainMenu");
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
     * exit the aplication and send request to close the database.
     *
     * @param actionEvent is the event
     **/
    public void Exit(ActionEvent actionEvent) {

        reservationsService.close();
        Platform.exit();
    }

    /**
     * change the status of reservation from open to closed and from then the reservation will be treated as invoice.
     *
     * @param actionEvent is the event
     */
    public void finalizeReservation(ActionEvent actionEvent) {
        ObservableList<Reservation> selectedItems = ReservationTable.getSelectionModel().getSelectedItems();
        for (Reservation r : selectedItems) {
            try {
                if (reservationsService.search(r.getReservation_id())) {
                    LOG.info("found reservation to be invoiced: {}", r);
                    r.setFinalizationDate(Timestamp.valueOf(LocalDateTime.now()));
                    reservationsService.finalizeReservation(r);
                }
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            } catch (NotFoundException e) {
                LOG.error(e.getMessage());
            }

        }
        LOG.info("finalize Reservation, refresh table");
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

    /**
     * Handles the showing of statistics, an instance of StatisticController will be created and all the statistics will be showing up.
     *
     * @param actionEvent is the event
     */
    public void Showstatistic(ActionEvent actionEvent) {
        StatisticController statisticController = new StatisticController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Statistic.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(statisticController) ? statisticController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }
}
