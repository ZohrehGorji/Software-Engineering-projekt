package at.ac.tuwien.sepm.assignment.individual.application;

import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleServiceImpl;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.service.VehicleService;
import at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.ui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public final class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void start(Stage primaryStage) throws Exception {


        // setup application
        primaryStage.setTitle("Vehicle Rental");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(720);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> LOG.debug("Application shutdown initiated"));

        // initiate service and controller
        MainViewController MainViewController = new MainViewController();

        // prepare fxml loader to inject controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        fxmlLoader.setControllerFactory(param -> param.isInstance(MainViewController) ? MainViewController : null);
        primaryStage.setScene(new Scene(fxmlLoader.load()));

        // show application
        primaryStage.show();
        primaryStage.toFront();
        LOG.debug("Application startup complete");


    }


    public static void main(String[] args) {
        LOG.debug("Application starting with arguments={}", (Object) args);
        Application.launch(MainApplication.class, args);
    }

}
