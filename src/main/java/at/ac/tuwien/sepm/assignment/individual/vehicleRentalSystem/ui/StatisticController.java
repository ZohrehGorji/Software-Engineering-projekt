package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities.Reservation;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class StatisticController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public NumberAxis yAxis2;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE ;

    @FXML
    private AnchorPane rootpane;
    @FXML
    private CheckBox a, b, c;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private CategoryAxis xAxis2;

    private ObservableList<String> days = FXCollections.observableArrayList();
    private ObservableList<String> ids = FXCollections.observableArrayList();
    private ReservationsService reservationsService = new ReservationServieceImpl();
    private VehicleService vehicleService = new VehicleServiceImpl();
    private ObservableList<String> weekNames = FXCollections.observableArrayList();
    private ObservableList<String> licenseClass = FXCollections.observableArrayList();


    @FXML
    private LineChart<Number, Number> chart_line;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private DatePicker dp_barFrom;

    @FXML
    private DatePicker dp_barTo;
    private ObservableList<String> selectedCategories = FXCollections.observableArrayList();


    @FXML
    private void initialize()  {
        initializeLineChart();
        initializeBarChart();
    }

    @FXML
    private void show() {

        if (!barChart.getData().isEmpty())
            barChart.getData().clear();


        // barChart.getData().addAll(reservationsService.mostbooked(ids));
        int[] weekCounter = new int[8];
        if (dp_barTo.getValue()==null ||dp_barFrom.getValue()==null){
            AlertMaker alertMaker=new AlertMaker();
            alertMaker.WarningAlert("Alarm","choose a date !");
            return;
        }
        Timestamp startTimeStamp = Timestamp.valueOf(dp_barFrom.getValue() + " " + "00:00:00");
        Timestamp endTimeStamp = Timestamp.valueOf(dp_barTo.getValue() + " " + "00:00:00");

        try {
            for (Reservation r : reservationsService.ListOfreservationsByTime(startTimeStamp,endTimeStamp)) {
                {
                    if (a.isSelected() && !(b.isSelected()) && !(c.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("A")) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (b.isSelected() && !(c.isSelected()) && !(a.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("B")) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (c.isSelected() && !(b.isSelected()) && !(a.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("C")) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (c.isSelected() && b.isSelected() && !(a.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("C") || vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("B") ) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (c.isSelected() && a.isSelected() && !(b.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("C") || vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("A") ) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (a.isSelected() && b.isSelected() && !(c.isSelected())) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("B") || vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("A") ) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (c.isSelected() && a.isSelected() && b.isSelected()) {
                        if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("C") || vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("A")|| vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("B") ) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;
                        }
                    }else  if (!(c.isSelected()) && !(a.isSelected()) && !(b.isSelected())) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(r.getStart());
                            int x = calendar.get(Calendar.DAY_OF_WEEK);
                            weekCounter[x]++;

                    }

                }
            }
        } catch (ServiceException e) {
            LOG.error(e.getMessage());

        }


        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < weekCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(weekNames.get(i), weekCounter[i]));
        }

        barChart.getData().add(series);


        //linechart
        int[] classcounter = new int[3];
        try {
            for (Reservation r : reservationsService.getInvoices()) {

                try {
                    if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("A")) {
                        classcounter[0] += r.getTotalPrice();
                    }
                    if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("B")) {
                        classcounter[1] += r.getTotalPrice();
                    }
                    if (vehicleService.getVehicleEvenIfIsDeleted(r.getVehicleID()).getLicenseClass().contains("C")) {
                        classcounter[2] += r.getTotalPrice();
                    }

                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        } catch (ServiceException e) {
            LOG.error(e.getMessage());
        }

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data(licenseClass.get(0), classcounter[0]));
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data(licenseClass.get(1), classcounter[1]));
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data(licenseClass.get(2), classcounter[2]));
        chart_line.getData().addAll(series1, series2, series3);



    }

    private void initializeLineChart() {
      //  Axis<Number> yAxis = chart_line.getYAxis();
        xAxis2.setLabel("License class");
        yAxis2.setLabel("sale");

        chart_line.setTitle("Sales per License class");

        String[] licenseC = {"A", "B", "C"};
        licenseClass.addAll(Arrays.asList(licenseC));
        xAxis2.setCategories(licenseClass);


    }

    private void initializeBarChart() {
        Axis<Number> yAxis = barChart.getYAxis();
        xAxis.setLabel("Week");
        yAxis.setLabel("occurrence\n");

        barChart.setTitle("rental occurrence per week days");

        String[] week = DateFormatSymbols.getInstance(Locale.ENGLISH).getShortWeekdays();
        weekNames.addAll(Arrays.asList(week));
        xAxis.setCategories(weekNames);

    }


    public void gobackto(ActionEvent actionEvent) {
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
}
