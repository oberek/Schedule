/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulemanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.DBManager;

/**
 *
 * @author mcken
 */
public class ScheduleManager extends Application {
    public static Connection connection;
    public static PreparedStatement preparedStatement;

    private static void openDB() {  
       DBManager.open();
       connection = DBManager.getConnection();
    }

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private String englishLocale = "resources/en_EN";
    private String frenchLocale = "resources/fr_FR";
    
    ResourceBundle rb = ResourceBundle.getBundle(englishLocale, Locale.getDefault());

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(rb.getString("app_title"));
        initRootLayout();

    }

    public void initRootLayout() {
        try {
            rootLayout = FXMLLoader.load(ScheduleManager.class.getResource("/view_controller/Login.fxml"));
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        openDB();
        launch(args);
        
    }
}
