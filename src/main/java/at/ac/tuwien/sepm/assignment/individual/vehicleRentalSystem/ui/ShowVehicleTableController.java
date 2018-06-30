package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.AlertMaker;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServieceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

/**
 * controller class for the Show Vehicle Table stage
 */
public class ShowVehicleTableController {
    //list of all data
    private ObservableList<Vehicle> data = FXCollections.observableArrayList();
    /**
     * LOG is a Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * A Label, which accept the id of a vehicle.
     */

    @FXML
    private Label idlabel;
    /**
     * A Label, which accept the description of a vehicle.
     */
    @FXML
    private Label descriptionLabel;
    /**
     * A Label, which accept the number of Seats of a vehicle.
     */
    @FXML
    private Label SeatsLabel;
    /**
     * A Label, which accept the licensePlate of a vehicle.
     */
    @FXML
    private Label licensePlateLabel;
    /**
     * A Label, which accept the typeOfDrive of a vehicle.
     */
    @FXML
    private Label typeOfDriveLabel;
    /**
     * A texLabeltfield, which accept the pice of a vehicle.
     */
    @FXML
    private Label picelabel;
    /**
     * A Label, which accept the  license of a vehicle.
     */
    @FXML
    private Label licenselabel;
    /**
     * A free filterfield to filter random, number, name...
     */
    @FXML
    private TextField filterField;
    /**
     * A ImageView, which accept/shows the image of a vehicle.
     */
    @FXML
    private ImageView imageView;
    /**
     * A CheckBox, if selected , it means that vehicle type of drive is muscle.
     */
    @FXML
    private CheckBox muscle;
    /**
     * A CheckBox, if selected , it means that vehicle type of drive is motorized.
     */
    @FXML
    private CheckBox motorized;
    /**
     * A TableView of vehicle, which every vehicle will be showed in it.
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
     * A Label, which shows the booking date.
     */
    @FXML
    private Label bookingDate;
    /**
     * A textfield, which is to be filled and searched for license class search.
     */

    @FXML
    private TextField licenseClassSearch;
    /**
     * A textfield, which is to be filled and searched for minimum price search.
     */
    @FXML
    private TextField minPriceSearch;
    /**
     * A textfield, which is to be filled and searched for maximum price search.
     */
    @FXML
    private TextField maxPriceSearch;
    /**
     * A textfield, which is to be filled and searched for seats number search.
     */
    @FXML
    private TextField seatsSearch;
    /**
     * A textfield, which is to be filled and searched for title of a vehicle search.
     */
    @FXML
    private TextField titleSearch;
    /**
     * A DatePicker, which is the start of the reservation.
     */

    @FXML
    private DatePicker startSearch;
    /**
     * A DatePicker, which is the end of the reservation.
     */
    @FXML
    private DatePicker endSearch;


    /**
     * An arraylist of string.
     */

    private ArrayList<String> str = new ArrayList<String>();

    @FXML
    private AnchorPane rootpane;

    /**
     * An instance of vehicle.
     */
    private Vehicle vehicle;
    /**
     * An instance of vehicle service and handel all service from vehicle service.
     */
    private VehicleService vehicleService;
    /**
     * An instance of reservation service and handel all service from reservation service.
     */
    private ReservationsService reservationsService = new ReservationServieceImpl();
    /**
     * A string, which ais for img path.
     */
    private String imagePath;
    /**
     * An instance of alermaker.
     */
    private AlertMaker alertMaker = new AlertMaker();

    /**
     * constructor for ShowVehicleTableController
     *
     * @param vehicleService , instance of vehicle service
     */

    public ShowVehicleTableController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * constructor for ShowVehicleTableController
     *
     * @param vehicleService , instance of vehicle service
     * @param str command holder.
     */
    public ShowVehicleTableController(VehicleService vehicleService, ArrayList<String> str) {
        this.vehicleService = vehicleService;
        this.str = str;
    }

    /**
     * Initializes fills the table of vehicle and the lable of informations.
     */
    @FXML
    public void initialize() {
        LOG.info("Show Vehicle Table Controller opened !");

        if (str.isEmpty()) {
            try {
                for (Vehicle v : vehicleService.getVehicles()) {
                    VehicleTable.getItems().add(v);
                    data.add(v);
                }
            } catch (NotFoundException e) {
                LOG.error(e.getMessage());
            } catch (ServiceException e) {
                LOG.error(e.getMessage());            }
        } else {
            data.clear();
            try {
                for (Vehicle v : vehicleService.filterTableVehicle(str)) {
                    VehicleTable.getItems().add(v);
                    data.add(v);
                }
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
        }
       // VehicleTable.setEditable(true);
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        year.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getYear() + ""));
        licenseClass.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLicenseClass()));
        typeOfDrive.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTypeOfDrive()));
        price.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrice()));
        seats.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getSeats()));


        VehicleTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        VehicleTable.getSelectionModel().selectedItemProperty().addListener((((observable, oldValue, newValue) -> {


                showInformation(newValue);

        })));

//random serach field
        filterField.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if (filterField.textProperty().get().isEmpty()) {
                    VehicleTable.setItems(data);
                    return;
                }
                ObservableList<Vehicle> VehicleTableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Vehicle, ?>> vehicleTableColumns = VehicleTable.getColumns();
                for (Vehicle aData : data) {

                    for (TableColumn<Vehicle, ?> vehicleTableColumn : vehicleTableColumns) {
                        String cellValue;
                        if (aData == null) {
                            cellValue = "";
                        } else {
                            cellValue = ((TableColumn) vehicleTableColumn).getCellData(aData).toString();
                        }
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(filterField.textProperty().get().toLowerCase())) {
                            VehicleTableItems.add(aData);
                            break;
                        }
                    }
                }
                VehicleTable.setItems(VehicleTableItems);

            }
        });


    }

    /**
     * shows all the information of a given vehicle
     *
     * @param vehicle to show the information
     */
    private void showInformation(Vehicle vehicle)  {

        descriptionLabel.setText(vehicle.getDescription());
        SeatsLabel.setText(vehicle.getSeats() + "");
        licensePlateLabel.setText(vehicle.getLicensePlate());
        if (vehicle.getTypeOfDrive().equals("muscle")) {
            typeOfDriveLabel.setText(vehicle.getTypeOfDrive());
        } else {
            typeOfDriveLabel.setText(vehicle.getTypeOfDrive() + ":" + vehicle.getPower());
        }
        imagePath = vehicle.getImgPath();
        picelabel.setText(vehicle.getPrice() + "");
        licenselabel.setText(vehicle.getLicenseClass());
        List<String> times = new ArrayList<>();
        try {
            times = reservationsService.searchBasedOnVehicleID(vehicle.getId());
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }
        if (!times.isEmpty()) {

            bookingDate.setText(times.toString());
            LOG.info(times.size() + "");
        } else {
            bookingDate.setText("not booked yet !");
        }


        /*reading and showing images*/
        BufferedImage image = null;
        File f;
        if (imagePath != null) {
            try {
                f = new File(imagePath);
                LOG.info(imagePath);
                image = ImageIO.read(f);
            } catch (NullPointerException n) {
                LOG.error("file is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image i = SwingFXUtils.toFXImage(image, null);
            imageView.setImage(i);
            imagePath = null;
        } else {
            imageView.setImage(null);//or some good picture in case of empty
            LOG.debug("no image to show !");
        }
        /**/
        this.vehicle = vehicle;

    }

    /**
     * editing the vehicle information, new instance of VehicleEditWindowController will be created.
     *  @param actionEvent is the event
     ***/

    public void EditInformationen(ActionEvent actionEvent)  {
        if (VehicleTable.getSelectionModel().isEmpty()) {
            alertMaker.ErrorAlert("Invalid selection!", "select a row !");

        } else {
            VehicleEditWindowController vehicleEditWindowController = new VehicleEditWindowController(vehicle, vehicleService);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditVehicleWindow.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(vehicleEditWindowController) ? vehicleEditWindowController : null);
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
     * exit the aplication and send request to close the database.
     *  @param actionEvent is the event
     **/

    public void Exit(ActionEvent actionEvent) {
        vehicleService.close();
        Platform.exit();
    }

    /**
     * delet a vehicle.
     *  @param actionEvent is the event
     ***/

    @FXML
    public void DeleteTheVehicle(ActionEvent actionEvent) {
        ObservableList<Vehicle> selectedItems = VehicleTable.getSelectionModel().getSelectedItems();
        String name = "";
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Warning");

        for (Vehicle selectedItem : selectedItems) {
            name += selectedItem.getTitle() + " " + selectedItem.getYear() + "\n";
        }
        a.setHeaderText("found vehicle to be deleted:\n" + name);
        a.setContentText("Are you sure you want to cancle these vehicles? \n" + name);
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            for (Vehicle v : selectedItems) {
                try {
                    if (vehicleService.search(v.getId())) {
                        LOG.debug("found vehicle to be deleted: {}", v);
                        vehicleService.delete(v);
                    }
                } catch (ServiceException e) {
                    LOG.error(e.getMessage());
                } catch (NotFoundException e) {
                    LOG.error(e.getMessage());
                }
            }
        }

        ShowVehicleTableController ShowVehicleTableController = new ShowVehicleTableController(vehicleService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowVehicleTableDialog.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(ShowVehicleTableController) ? ShowVehicleTableController : null);
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
     *  @param actionEvent is the event
     ***/

    public void backToMain(ActionEvent actionEvent) {
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
     * by clicking on ok button, filtering of the table will be beginning.
     * @param actionEvent is the event
     *
     */

    public void filterOK(ActionEvent actionEvent)  {
        ArrayList<String> command = new ArrayList<>();
        command.add("select * from Vehicle where ");


        if (!licenseClassSearch.getText().equals("")) {
            command.add("LICENSECLASS  like '%" + licenseClassSearch.getText().toUpperCase() + "%'");
        }

        if (!titleSearch.getText().equals("")) {
            command.add("title like '%" + titleSearch.getText().toLowerCase() + "%'");
        }
        if (muscle.isSelected()) {
            command.add("typeofdrive='muscle' ");
        }
        if (motorized.isSelected()) {
            command.add("typeofdrive='motorized' ");
        }

        if (minPriceSearch.getText().length() != 0) {
            command.add("price>=" + minPriceSearch.getText());
        }
        if (maxPriceSearch.getText().length() != 0) {
            command.add("price<=" + maxPriceSearch.getText());
        }
        if (seatsSearch.getText().length() != 0) {
            command.add("seats=" + seatsSearch.getText());
        }

        data.clear();
        // vehicleService.checkdate if not null then
        if (startSearch.getValue() == null && endSearch.getValue() == null) {
            try {
                if (vehicleService.filterTableVehicle(command).size() != 0) {

                    for (Vehicle v : vehicleService.filterTableVehicle(command)) {
                        VehicleTable.getItems().add(v);
                        LOG.info(v.toString());
                        data.add(v);

                    }
                } else {
                    alertMaker.WarningAlert("warning", "no entry found !");
                    return;
                }
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
        } else {


            if (command.size() == 1) {
                command.set(0, "select * from Vehicle");
            }
            List<Integer> idList = new ArrayList<>();
            try {
                for (Vehicle v : vehicleService.filterTableVehicle(command)) {
                    for (Vehicle vehicle1:vehicleService.ListOfFreeVehiclesByTime((startSearch.getValue() == null ? Timestamp.valueOf("2000-01-01 00:00:00.0") : Timestamp.valueOf(startSearch.getValue() + " " + "00:00:00")), (endSearch.getValue() == null ? Timestamp.valueOf("2050-01-01 00:00:00.0") : Timestamp.valueOf(endSearch.getValue() + " " + "00:00:00")), v.getId()))
                    {
                        if (vehicle1.getId()==v.getId()) {
                            VehicleTable.getItems().add(v);
                            idList.add(v.getId());
                            LOG.info("v.toString()" + v.toString());
                            data.add(v);
                        }
                    }
                }
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            }
            if (!idList.isEmpty()) {
                if (command.size() == 1) {
                    command.clear();
                    //command.set(0, "select * from Vehicle where");
                    for (int id : idList) {
                        //if (idList.get(0) == id) {

                        command.add(id + "");
                        //}

                    }
                }
            } else if (command.size() == 1) {
                command.set(0, "select * from Vehicle");
            }

        }

        LOG.info(data.size() + "");
        ShowVehicleTableController ShowVehicleTableController = new ShowVehicleTableController(vehicleService, command);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowVehicleTableDialog.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(ShowVehicleTableController) ? ShowVehicleTableController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);


    }


}