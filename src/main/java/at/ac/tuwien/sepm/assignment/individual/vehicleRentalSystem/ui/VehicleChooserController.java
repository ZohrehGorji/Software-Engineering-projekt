package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.AlertMaker;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationServieceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.ReservationsService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;




public class VehicleChooserController {
   private ObservableList<Vehicle> data = FXCollections.observableArrayList();

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * A free filterfield to filter random, number, name...
     */
    @FXML
    private TextField filterField;
    @FXML
    private Label PriceLable;
    @FXML
    private Label YearLable;
    @FXML
    private Label DescriptionLable;
    @FXML
    private Label SeatsLable;
    @FXML
    private Label LicensePlateLable;
    @FXML
    private Label TypeOfDrivingLable;

    @FXML
    private Button ok;
    @FXML
    private ImageView imageView;
    @FXML
    private TableView<Vehicle> VehicleTable;
    @FXML
    private TableColumn<Vehicle, String> title;
    @FXML
    private TableColumn<Vehicle, String> year;
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
    private Vehicle vehicle;
    private VehicleService vehicleService;
    private AlertMaker alertMaker=new AlertMaker();
    private String imagePath;
    private ObservableList<Vehicle> selectedItems;
    private ReserveVehicleController reserveVehicleController;
    public VehicleChooserController(VehicleService vehicleService, ReserveVehicleController reserveVehicleController){
        this.vehicleService = vehicleService;

        this.reserveVehicleController = reserveVehicleController;
    }
    public VehicleChooserController(VehicleService vehicleService, ArrayList<String> str) {
        this.vehicleService = vehicleService;
        this.str = str;
    }
    //initialize
    @FXML
    public void initialize()  {
        LOG.info("Show Vehicle Table Controller opened !");

        LOG.info("Show Vehicle Table Controller opened !");
        if (str.isEmpty()) {
            try {
                for (Vehicle v : vehicleService.getVehicles()) {
                    VehicleTable.getItems().add(v);
                    data.add(v);
                }
            } catch (ServiceException e) {
                LOG.error(e.getMessage());
            } catch (NotFoundException e) {
                LOG.error(e.getMessage());
            }
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

        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        year.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getYear())));

        VehicleTable.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        VehicleTable.getSelectionModel().selectedItemProperty().addListener((((observable, oldValue, newValue) -> showInformation(newValue))));
        selectedItems = VehicleTable.getSelectionModel().getSelectedItems();
        filterField.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if (filterField.textProperty().get().isEmpty()) {
                    VehicleTable.setItems(data);
                    return;
                }
                ObservableList<Vehicle> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Vehicle, ?>> vehicleTableColumns = VehicleTable.getColumns();
                for (Vehicle d : data) {

                    for (TableColumn<Vehicle, ?> vehicleTableColumn : vehicleTableColumns) {
                        String cellValue;
                        if (d == null) {
                            cellValue = "";
                        } else {
                            cellValue = ((TableColumn) vehicleTableColumn).getCellData(d).toString();
                        }
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(filterField.textProperty().get().toLowerCase())) {
                            tableItems.add(d);
                            break;
                        }
                    }
                }
                VehicleTable.setItems(tableItems);

            }
        });

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);


                alert.setContentText(selectedItems.toString());

                alert.showAndWait();
                reserveVehicleController.getList(selectedItems);
                Node source = (Node)  event.getSource();
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();


            }
        });

        //  vehicleService.setInfo(selectedItems);
    }

/*show all the info*/

    private void showInformation(Vehicle vehicle) {

        PriceLable.setText(vehicle.getPrice()+"");
        YearLable.setText(vehicle.getYear() + "");
        DescriptionLable.setText(vehicle.getDescription());
        SeatsLable.setText(vehicle.getSeats() + "");
        LicensePlateLable.setText(vehicle.getLicensePlate());
        if (vehicle.getTypeOfDrive().equals("muscle")) {
            TypeOfDrivingLable.setText(vehicle.getTypeOfDrive());
        } else {
            TypeOfDrivingLable.setText(vehicle.getTypeOfDrive() + ":" + vehicle.getPower());
        }        imagePath = vehicle.getImgPath();


        /**/
        BufferedImage image = null;
        File f;
        if (imagePath != null) {
            try {
                f = new File(imagePath);
                LOG.info(imagePath);
                image = ImageIO.read(f);
            } catch (NullPointerException n) {
                LOG.error(n.getMessage());
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
            Image i = SwingFXUtils.toFXImage(image, null);
            imageView.setImage(i);
            imagePath = null;
        } else {
            imageView.setImage(null);
            LOG.info("no image available");
        }
        /**/
        this.vehicle = vehicle;

    }
/*ok send the chosen info to the reservation edit stage*/
    public void ok(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);


        alert.setContentText(selectedItems.toString());

        alert.showAndWait();
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public ObservableList<Vehicle> getData() {
        return selectedItems ;
    }
/*filtering will be beginning*/
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
              //  LOG.info("idList" + idList.toString());
                if (!idList.isEmpty()) {
                    if (command.size() == 1) {
                        command.clear();
                        for (int id : idList) {
                            command.add(id + "");
                        }
                    }
                    LOG.error("c:" + command + "");
                } else if (command.size() == 1) {
                    command.set(0, "select * from Vehicle");
                }

            }

            LOG.info(data.size() + "");
            VehicleChooserController vehicleChooserController = new VehicleChooserController(vehicleService,command);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/VehicleChooser.fxml"));
            fxmlLoader.setControllerFactory(param -> param.isInstance(vehicleChooserController) ? vehicleChooserController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);


        }
    }


