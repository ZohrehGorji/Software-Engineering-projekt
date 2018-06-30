package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Vehicle;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.DAOException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.AlertMaker;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * controller class for the Vehicle Edit stage
 */
public class VehicleEditWindowController {
    /**
     * LOG is a Logger instance.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /**
     * an instance of vehicle class.
     */
    private Vehicle vehicle;
    /**
     * path of the img will be stored here.
     */
    private String imagePath;
    /**
     * an instance of  stage.
     */
    private Stage stage;
    /**
     * an instance of vehicleService class.
     */
    private VehicleService vehicleService;

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
     * checkboxes, for each class of driving license, A,B,C, which will be deactivated unless user click on yesorno checkbox.
     */
    @FXML
    private CheckBox classA;
    @FXML
    private CheckBox classB;
    @FXML
    private CheckBox classC;
    /**
     * A Choicebox, which can be either muscle or motorized. Each of them has different access.
     */
    @FXML
    private CheckBox changeTypeOfDrive;
    /**
     * An Imageview, which shows the uploaded picture .
     */
    @FXML
    ImageView imgPath;
    /**
     * An Label, which shows type of drive of a vehicle that is already created by .
     */
    @FXML
    private Label typeOfDriveLabel;
    /**
     * An instance of class AlertMaker.
     */
    private AlertMaker alertMaker = new AlertMaker();

    /** constructor for VehicleEditWindowController
     *@param vehicle the vehicle
     *@param vehicleService the vehicleservice
     **/
    public VehicleEditWindowController(Vehicle vehicle, VehicleService vehicleService) {
        this.vehicle = vehicle;
        this.vehicleService = vehicleService;
    }

    /**
     * initialize the fields with the information from selected vehicle to be deleted.
     **/
    @FXML
    public void initialize() {


        LOG.info("VehicleEditWindowController: initialize");
        title.setText(vehicle.getTitle());
        year.setText(vehicle.getYear() + "");
        price.setText(vehicle.getPrice() + "");
        description.setText(vehicle.getDescription());
        seats.setText(vehicle.getSeats() + "");
        licensePlate.setText(vehicle.getLicensePlate());
        typeOfDriveLabel.setText("  " + vehicle.getTypeOfDrive());
        if (vehicle.getTypeOfDrive().equals("motorized")) {
            changeTypeOfDrive.setText("muscle");
        } else {
            changeTypeOfDrive.setText("motorized");
        }

        power.setText(vehicle.getPower() + "");

        if (vehicle.getLicenseClass().contains("A")) {
            classA.setSelected(true);
        }
        if (vehicle.getLicenseClass().contains("B")) {
            classB.setSelected(true);
        }
        if (vehicle.getLicenseClass().contains("C")) {
            classC.setSelected(true);
        }

        //image anzeigen
        imagePath = vehicle.getImgPath();
        try {
            BufferedImage image = null;
            File f = null;
            f = new File(imagePath);
            try {
                image = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image i = SwingFXUtils.toFXImage(image, null);
            imgPath.setImage(i);
        } catch (NullPointerException n) {
            LOG.info("no picture to show !");
        }
    }
    /**
    * return to the vehicle table by creating another instance of ShowVehicleTableController.
    * @throws IOException incase of problem.
     *@param actionEvent is the event
    **/

    public void BackToTable(ActionEvent actionEvent) throws IOException {
        //Platform.exit();

        ShowVehicleTableController ShowVehicleTableController = new ShowVehicleTableController(vehicleService);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ShowVehicleTableDialog.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(ShowVehicleTableController) ? ShowVehicleTableController : null);
        AnchorPane pane = fxmlLoader.load();
        rootpane.getChildren().setAll(pane);

        //
    }
    /**
     * showAllTheReservations to edit the vehicle.
     * read information from all the field and edit the vehicle in table.
     * @throws ServiceException if a problem accured
     * @throws  NotFoundException incase of problem.
     *@param actionEvent is the event
     **/
    public void edit(ActionEvent actionEvent) throws ServiceException, NotFoundException {

        LOG.debug("VehicleEditWindowController: updateReservation {}");
        if (!isNumeric(price.getText())||!isNumeric(year.getText()) || !isNumeric(power.getText()) || !isNumeric(seats.getText())) {
            alertMaker.ErrorAlert("Converting Error", "Year, Performance price Seats must be numbers");
            return;
        }

        vehicle.setTitle(title.getText());

        vehicle.setYear(Integer.parseInt(year.getText()));
        vehicle.setDescription(description.getText());
        vehicle.setSeats(Integer.parseInt(seats.getText()));
        vehicle.setLicensePlate(licensePlate.getText());
        vehicle.setLastEdit(Timestamp.valueOf(LocalDateTime.now()));
        if (changeTypeOfDrive.isSelected()) {

            vehicle.setTypeOfDrive(changeTypeOfDrive.getText());

        }
        vehicle.setPrice(Integer.parseInt(price.getText()));
        vehicle.setPower(Integer.parseInt(power.getText()));
        vehicle.setLicenseClass(classe(classA, classB, classC));


        vehicleService.editVehicle(vehicle);

    }

    /**
     * Method to upload to the image.
     *
     **/
    @FXML
    private String ImageUpload(ActionEvent ImageUpload) {
        //uploading a picture
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Vehicle Picture");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                LOG.info("File chosen {}", file.getAbsoluteFile());
                imagePath = file.getAbsolutePath();
            }
        } catch (NullPointerException n) {
            LOG.error(n.getMessage());
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
            } else if (!isPNGorJPGorJPEG(f)) {
                alertMaker.ErrorAlert("Invalid selection!", "Image format is not acceptable !");

            } else if (image.getRaster().getHeight() < 500 || image.getRaster().getWidth() < 500) {
                alertMaker.ErrorAlert("Invalid selection!", "atleast 500 x 500 pixel! ");

            } else {

                Image i = SwingFXUtils.toFXImage(image, null);
                imgPath.setImage(i);
                String format = (formatFinder(f).equals("jpg") || formatFinder(f).equals("jpeg") ? "jpeg" : (formatFinder(f).equals("png") ? "png" : ""));
                f = new File("/Users/zi/Desktop/sepm-individual-assignment-java/src/main/resources/imgFolder/" + vehicle.getId() + "." + format);
                imagePath = f.getAbsolutePath();
                if (!f.getAbsolutePath().isEmpty()) {
                    Files.delete(f.toPath().toAbsolutePath());
                }
                ImageIO.write(image, format, f);
            }
        } catch (NullPointerException n) {
            LOG.error(n.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        vehicle.setImgPath(imagePath);
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
     * if a checkbox is selected , the value of the checkbox will be stored in a string.
     * @param a,b,c are the checkboxes.
     * @return a string wich include all the selected license classes.
     */
    private String classe(CheckBox a, CheckBox b, CheckBox c) {
        if (a.isSelected() & b.isSelected() & c.isSelected()) {
            return "A B C";
        } else if (a.isSelected() & b.isSelected()) {
            return "A B";
        } else if (b.isSelected() & c.isSelected()) {
            return "B C";
        } else if (a.isSelected() & c.isSelected()) {
            return "A C";
        } else if (a.isSelected()) {
            return "A";
        } else if (a.isSelected()) {
            return "B";
        } else if (a.isSelected()) {
            return "C";
        }
        return "";
    }
    /**
     * check fot the format of a file
     * @param filename is the file to be checked for the extension.
     * @return true if the format is PNG OR JPEG OR JPG
     */
    private static boolean isPNGorJPGorJPEG(File filename) {
        if (formatFinder(filename).equals("jpg") || formatFinder(filename).equals("jpeg") || formatFinder(filename).equals("png")) {
            return true;
        } else return false;
    }
    /**
     * check if a given string is an integer number.
     * @param s is the string to be checked.
     * @return true if s is an integer and false otherwise.
     */
    private boolean isNumeric(String s) {
        return java.util.regex.Pattern.matches("\\d+", s);
    }

}
