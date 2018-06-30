package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * controller class for the main stage
 */
public class MainViewController {
    /**
     * LOG is a Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /*
     * imagePath contains the path for a image of a vehicle*/
    private String imagePath;
    private Stage stage;
    @FXML
    private AnchorPane rootpane;
    /**
     * A textfield, which accept the title of a vehicle.
     */
    @FXML
    private TextField title;
    /**
     * A textfield, which accept the year of creation of a vehicle.
     */
    @FXML
    private TextField year;
    /**
     * A textfield, which accept the description of a vehicle.
     */
    @FXML
    private TextField description;
    /**
     * A textfield, which accept the number of seats in a vehicle.
     */
    @FXML
    private TextField seats;
    /**
     * A textfield, which accept the license plate of a vehicle.
     */
    @FXML
    private TextField licensePlate;
    /**
     * A Choicebox, which can be either motorized or muscle.
     */
    @FXML
    ChoiceBox<String> typeOfDrive = new ChoiceBox<>();
    /**
     * A textfield, which accept the performance of a vehicle, incase it's motorized.
     */
    @FXML
    private TextField power;
    /**
     * A textfield, which accept the price of a vehicle pro hour.
     */
    @FXML
    private TextField price;
    /**
     * A Checkbox, which can be either selected or not, and by selected it means that vehicle needs a driving license.
     */
    @FXML
    private CheckBox yesORno;

    /**
     * A Button, which by clicking on it, user can upload an image for a vehicle, the path will be saving in imgpath.
     */
    @FXML
    private Button imgbutton;
    /**
     * A textflow, which shows important messages during application.
     */
    @FXML
    private TextFlow txtF;
    /**
     * An Imageview, which shows the uploaded picture .
     */
    @FXML
    ImageView imageView;
    /**
     * checkboxes, for each class of driving license, A,B,C, which will be deactivated unless user click on yesorno checkbox.
     */
    @FXML
    private CheckBox ClassA, ClassB, ClassC;
    /**
     * A Button, which by clicking on it, user can see a table of created vehicle.
     */
    @FXML
    private Button showVehicles;
    /**
     * A Choicebox, which can be either operator or employee. Each of them has different access.
     */
    @FXML
    private ChoiceBox<String> ChooseUser = new ChoiceBox<>();
    /**
     * An instance of Alertmaker.
     */
    private AlertMaker alertMaker = new AlertMaker();

    /**
     * A service for handling vehicle persistence methods.
     */
    private VehicleService vehicleService = new VehicleServiceImpl();
    /**
     * A service for handling reservation persistence methods.
     */
    private ReservationsService reservationsService = new ReservationServieceImpl();

    /**
     * Initializes chooseuser choicebox and yesorno checkbox .
     * in order to create a vehicle fields needs to be filled but they are
     * disable unless user choose operator in choicebox.
     * and driving license classes are also deactivated unless user select the yesorno checkbox.
     */
    @FXML
    public void initialize() {
        LOG.debug("Main View Controller opened !");
        txtF.getChildren().clear();
        Text text = new Text("Please Choose User: Employee or operator !");
        txtF.getChildren().add(text);
        showVehicles.setDisable(true);
        title.setDisable(true);
        year.setDisable(true);
        description.setDisable(true);
        seats.setDisable(true);
        licensePlate.setDisable(true);
        typeOfDrive.setDisable(true);
        power.setDisable(true);
        price.setDisable(true);
        yesORno.setDisable(true);
        ClassA.setDisable(true);
        ClassB.setDisable(true);
        ClassC.setDisable(true);
        imgbutton.setDisable(true);

        typeOfDrive.getItems().addAll("motorized", "muscle");
        typeOfDrive.getSelectionModel().selectFirst();
        ChooseUser.getItems().addAll("operator", "Employee");
        seats.setText("0");
        power.setText("0");
        price.setText("0");

        ChooseUser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (ChooseUser.getItems().get((Integer) newValue).equals("Employee")) {
                    showVehicles.setDisable(true);
                    title.setDisable(true);
                    year.setDisable(true);
                    description.setDisable(true);
                    seats.setDisable(true);
                    typeOfDrive.setDisable(true);
                    power.setDisable(true);
                    price.setDisable(true);
                    imgbutton.setDisable(true);
                    txtF.getChildren().clear();
                    txtF.getChildren().add(new Text("Employee Portal"));


                    EmployeeMenuController employeeMenuController = new EmployeeMenuController(reservationsService);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EmployeeMainMenu.fxml"));
                    fxmlLoader.setControllerFactory(param -> param.isInstance(employeeMenuController) ? employeeMenuController : null);
                    AnchorPane pane = null;
                    try {
                        pane = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    rootpane.getChildren().setAll(pane);

                } else if (ChooseUser.getItems().get((Integer) newValue).equals("operator")) {
                    showVehicles.setDisable(false);
                    title.setDisable(false);
                    year.setDisable(false);
                    description.setDisable(false);
                    seats.setDisable(false);
                    typeOfDrive.setDisable(false);
                    power.setDisable(false);
                    price.setDisable(false);
                    yesORno.setDisable(false);
                    imgbutton.setDisable(false);
                    txtF.getChildren().clear();
                    licensePlate.setDisable(false);
                    txtF.getChildren().add(new Text("Operator Portal"));

                    typeOfDrive.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (typeOfDrive.getItems().get((Integer) newValue).equals("muscle")) {
                                power.setDisable(true);
                            } else if (typeOfDrive.getItems().get((Integer) newValue).equals("motorized")) {
                                power.setDisable(false);
                            }
                        }
                    });


                }
            }
        });


        yesORno.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!yesORno.isSelected()) {
                    ClassA.setDisable(true);
                    ClassB.setDisable(true);
                    ClassC.setDisable(true);
                } else {
                    ClassA.setDisable(false);
                    ClassB.setDisable(false);
                    ClassC.setDisable(false);
                }
            }
        });
    }

    /**
     * closes the aplication and send vehicle service request to close the database.
     *  @param actionEvent is the event
     */
    public void finishprogram(ActionEvent actionEvent) {
        vehicleService.close();
        Platform.exit();
    }
/**
 * saves the vehicle , send the vehicleservice a request with information to save a vehicle.
 *@param saveAll is the event
 **/
    public void saveAll(ActionEvent saveAll) {

        try {


            if ( !isNumeric(price.getText())) {
                alertMaker.ErrorAlert("Converting Error", "Price must be numbers");
                return;
            } if (!isNumeric(year.getText())){
                alertMaker.ErrorAlert("Converting Error", "Year must be numbers");
                return;
            }  if ( !isNumeric(power.getText())){
                alertMaker.ErrorAlert("Converting Error", "Power must be numbers");
                return;
            }if (  !isNumeric(seats.getText())){
                alertMaker.ErrorAlert("Converting Error", "Seats must be numbers");
                return;
            }
                  Vehicle vehicle = Vehicle.builder()

                .title(title.getText())
                .year(Integer.parseInt(year.getText()))
                .description(description.getText())
                .seats(Integer.parseInt(seats.getText()))
                .licensePlate(licensePlate.getText())
                .typeOfDrive( typeOfDrive.getValue())
                .power(Integer.parseInt(power.getText()))
                .price(Integer.parseInt(price.getText()))
                .imgPath(imagePath)
                .licenseClass( classe(ClassA, ClassB, ClassC))
                .build();

            vehicleService.createVehicle(vehicle);


            imagePath="";

            title.setText("");
            year.setText("0");
            description.setText("");
            seats.setText("0");
            licensePlate.setText("");
            power.setText("0");
            price.setText("0");
            ClassA.setSelected(false);
            ClassB.setSelected(false);
            ClassC.setSelected(false);
            yesORno.setSelected(false);
            yesORno.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!yesORno.isSelected()) {
                       // licensePlate.setDisable(true);
                        ClassA.setDisable(true);
                        ClassB.setDisable(true);
                        ClassC.setDisable(true);
                    } else {
                      //  licensePlate.setDisable(false);
                        ClassA.setDisable(false);
                        ClassB.setDisable(false);
                        ClassC.setDisable(false);
                    }
                }
            });
            typeOfDrive.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (typeOfDrive.getItems().get((Integer) newValue).equals("muscle")) {
                        power.setDisable(true);
                    } else if (typeOfDrive.getItems().get((Integer) newValue).equals("motorized")) {
                        power.setDisable(false);
                    }
                }
            });


        } catch (ServiceException n) {

            LOG.error(n.getMessage());

            alertMaker.ErrorAlert("Service Exception", n.getMessage());

        }
    }
    /**
     * the method to upload the image.
     *
     * @return imgpath of the uploaded image.
     *  @param ImageUpload is the event
     */
    @FXML
    private String ImageUpload(ActionEvent ImageUpload) {
        //uploading a picture
        try {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                    "Image files", "*.png", "*.tiff","*.gif","*.bmp","*.jpg", "*.jpeg");

            fileChooser.getExtensionFilters().add(fileExtensions);


            fileChooser.setTitle("Choose Vehicle Picture");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                LOG.debug("File chosen {}", file.getAbsoluteFile());
                imagePath = file.getAbsolutePath();
            }

        } catch (NullPointerException n) {
            LOG.error(" no file attached ");
        }
        BufferedImage image = null;
        File f = null;

        //read image file
        try {
            f = new File(imagePath);
            image = ImageIO.read(f);
            int imgLength = (int) f.length();
            if (imgLength > 5242880) {

                alertMaker.ErrorAlert("Invalid selection!", "The Image size is more that 5 MB, please choose another one !");
            }
            else if (!isPNGorJPGorJPEG(f)){
                alertMaker.ErrorAlert("Invalid selection!", "Image format is not acceptable !");

            }else if (image.getRaster().getHeight() < 500 || image.getRaster().getWidth() < 500) {
                alertMaker.ErrorAlert("Invalid selection!", "atleast 500 x 500 pixel! ");

            } else {
                if (formatFinder(f).equals("jpg")) {
                    txtF.getChildren().clear();
                    Text text = new Text("Image Format is jpg!, Image will be saved as jpeg! ");
                    txtF.getChildren().add(text);


                }
                Image i = SwingFXUtils.toFXImage(image, null);
                imageView.setImage(i);
                String format=(formatFinder(f).equals("jpg")||formatFinder(f).equals("jpeg")?"jpeg":(formatFinder(f).equals("png")?"png":""));

                f = new File("/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/" +(vehicleService.getvID()+1)+"." + format);

                imagePath = f.getAbsolutePath();
                ImageIO.write(image, format, f);
            }
        } catch (NullPointerException n) {
            alertMaker.ErrorAlert("Error, NO Image choosed !",n.getMessage());
        } catch (IOException e) {
           alertMaker.ErrorAlert("Error",e.getMessage());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return imagePath;
    }
    /**
     * find the extension of a file
     * @param filename is the file to be checked for the extension.
     * @return the extension in form of string.
     */
    private static String formatFinder(File filename) {
        String fileName = filename.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";

    }
    /**
     * check fot the format of a file
     * @param filename is the file to be checked for the extension.
     * @return true if the format is PNG OR JPEG OR JPG
     */
    private static boolean isPNGorJPGorJPEG(File filename) {
        if (formatFinder(filename).equals("jpg") ||formatFinder(filename).equals("jpeg") || formatFinder(filename).equals("png")) {
            return true;
        } else return false;
    }
    /**
     * if a checkbox is selected , the value of the checkbox will be stored in a string.
     * @param a,b,c are the checkboxes.
     * @return a string wich include all the selected license classes.
     */
    private String classe(CheckBox a, CheckBox b, CheckBox c) {
        if (a.isSelected() & b.isSelected() & c.isSelected()) {

            return  "A B C";
        } else if (a.isSelected() & b.isSelected()) {

            return "A B";
        } else if (b.isSelected() & c.isSelected()) {

            return "B C";
        } else if (a.isSelected() & c.isSelected()) {

            return "A C";
        } else if (a.isSelected()) {

            return "A";
        } else if (b.isSelected()) {

            return "B";
        } else if (c.isSelected()) {

            return "C";
        }
        return "";

    }
    /**
     * Handles the showing of Vehicles, an instance of ShowVehicleTableController will be created and all the Vehicles will be showing up in table.
     * @param actionEvent is the event
     */

    public void ShowVehicles(ActionEvent actionEvent) {
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
     * check if a given string is an integer number.
     * @param s is the string to be checked.
     * @return true if s is an integer and false otherwise.
     */

    private boolean isNumeric(String s) {
        LOG.info("IsNumeric Method: Checking if Input is a number!");
        return java.util.regex.Pattern.matches("\\d+", s);
    }
    /**
     * Handles the showing of Reservations, an instance of ShowReservationTableController
     * will be created and all the Reservations will be showing up in table.
     *  @param actionEvent is the event
     */

    public void ShowReservations(ActionEvent actionEvent)  {
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
     * Handles the showing of invoices, an instance of ShowInvoiceTableController will
     * be created and all the invoices will be showing up in table.
     *  @param actionEvent is the event
     */
    public void ShowInvoices(ActionEvent actionEvent)  {
        ShowInvoiceTableController showInvoiceTableController = new ShowInvoiceTableController(reservationsService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowInvoiceTable.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(showInvoiceTableController) ? showInvoiceTableController : null);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        rootpane.getChildren().setAll(pane);
    }
}

