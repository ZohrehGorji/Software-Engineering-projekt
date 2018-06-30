package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.AlertMaker;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;

public class ShowInvoiceTableController {

    ObservableList<Reservation> data = FXCollections.observableArrayList();

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ArrayList<Integer> ListOfCheckingForUniqueEntry = new ArrayList<>();

    @FXML
    private AnchorPane rootpane;
    @FXML
    private Label PaymentLabel;
    @FXML
    private Label pricelebel;
    @FXML
    private Label totalPricelabel;
    @FXML
    private Label vehicle_idlabel;
    @FXML
    private TableView<Reservation> InvoiceTable;
    @FXML
    private TableColumn<Reservation, String> clientName;
    @FXML
    private TableColumn<Reservation, String> start;
    @FXML
    private TableColumn<Reservation, String> end;
    @FXML
    private TableColumn<Reservation, String> startTime;
    @FXML
    private TableColumn<Reservation, String> endTime;
    @FXML
    private TableColumn<Reservation, String> bookingID;
    @FXML
    private TableColumn<Reservation, String> status;
    @FXML
    private Button editHandle;

    private ReservationsService reservationsService;
    private Reservation reservation;

    public ShowInvoiceTableController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;

    }

    @FXML
    public void initialize() {
        try {
            for (Reservation r : reservationsService.getInvoices()) {
                if (InvoiceTable.getItems().isEmpty()) {
                    ListOfCheckingForUniqueEntry.add(r.getReservation_secondID());
                    InvoiceTable.getItems().add(r);
                } else {
                    if (!ListOfCheckingForUniqueEntry.contains(r.getReservation_secondID())) {
                        ListOfCheckingForUniqueEntry.add(r.getReservation_secondID());
                        InvoiceTable.getItems().add(r);
                    }
                }
            }
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }


        bookingID.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getReservation_secondID())));
        clientName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientName()));
        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        start.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>("" + cellData.getValue().getStart().toLocalDateTime().toLocalDate()));
        end.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>("" + cellData.getValue().getEnd().toLocalDateTime().toLocalDate()));
        startTime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((cellData.getValue().getStart().toLocalDateTime().getHour()) + ":" + (cellData.getValue().getStart().toLocalDateTime().getMinute()) + ":" + (cellData.getValue().getStart().toLocalDateTime().getSecond())));
        endTime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((cellData.getValue().getEnd().toLocalDateTime().getHour()) + ":" + (cellData.getValue().getEnd().toLocalDateTime().getMinute()) + ":" + (cellData.getValue().getEnd().toLocalDateTime().getSecond())));

        InvoiceTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        InvoiceTable.getSelectionModel().selectedItemProperty().addListener((((observable, oldValue, newValue) -> {
            try {
                showInformation(newValue);
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
        })));


    }

    public void EditInformationen(ActionEvent actionEvent)  {

        if (InvoiceTable.getSelectionModel().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Warning");
            alert1.setHeaderText("Invalid selection!");
            alert1.setContentText("select a row !");
            alert1.showAndWait();
        } else {

            EditReservationController editReservationController = new EditReservationController(reservationsService,reservation);
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

    private void showInformation(Reservation reservation) throws ServiceException {
        //showing payment
        CreditCardValidator creditCardValidator = new CreditCardValidator();
        String art="";
        if (reservation.getArtOFPayment()==null){
            if (new IBANCheckDigit().isValid(reservation.getPayment())){
                art="IBAN";
            }else  if (creditCardValidator.isValid(reservation.getPayment())){
                art="Credit Crd";
            }
        }else{
            art= reservation.getArtOFPayment();
        }

        PaymentLabel.setText(art+":"+reservation.getPayment());
        //show all the vehicle in one invoice
        try {
            vehicle_idlabel.setText(reservationsService.listOFVehicleIDsWithTheSameBookingIDINVOICE(reservation.getReservation_secondID()).toString() + "");
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

//price and total price
        ArrayList<Integer> p=new ArrayList<>();

            for (Integer i:reservationsService.getPrice(reservation.getReservation_secondID())) {

                p.add(i);
            }
        pricelebel.setText(p.toString());
            int sum=0;
            for (Integer i:p){
                sum+=i;
            }
            totalPricelabel.setText(sum+"");

//edit unable for the closed and canceled.


        if (InvoiceTable.getSelectionModel().getSelectedItem().getStatus().equals("closed") || InvoiceTable.getSelectionModel().getSelectedItem().getStatus().equals("canceled")) {
            editHandle.setDisable(true);
        } else editHandle.setDisable(false);
        this.reservation = reservation;

    }

    /**
     * return to the main menu  by creating another instance of MainViewController.
     *  @param actionEvent is the event
     ***/
    public void backToMain(ActionEvent actionEvent)  {
        LOG.info("from ShowInvoiceTable enter MitarbeiterMainMenu");
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
     *  @param actionEvent is the event
     **/
    public void Exit(ActionEvent actionEvent) {
        reservationsService.close();
        Platform.exit();

    }


}
