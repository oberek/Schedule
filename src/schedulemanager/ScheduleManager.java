/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and openDB the template in the editor.
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
/*
A.  Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.
 2
B.  Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
 1-2
C.  Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.
 2-4
D.  Provide the ability to view the calendar by month and by week.
 1
E.  Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time.
 1
F.  Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control.
•   scheduling an appointment outside business hours
•   scheduling overlapping appointments
•   entering nonexistent or invalid customer data
•   entering an incorrect username and password
 1-2
G.  Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
 0
H.  Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
 
I.  Provide the ability to generate each of the following reports:
•   number of appointment types by month
•   the schedule for each consultant
•   one additional report of your choice
 1
J.  Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists.

*/
public class ScheduleManager extends Application {
    public static Connection connection;
    public static PreparedStatement preparedStatement;

    private static void openDB() {  
       DBManager.openDB();
       connection = DBManager.getConnection();
    }

    private Stage primaryStage;
    private AnchorPane rootLayout;
    
    //*************Changes the locale
    
    //TODO: an attempt was made. String and Locale do not mix.
//    public String languageSelect(String language){
//        Locale locale = new Locale(System.getProperty(language));
//        if(locale.toString() == englishLocale){
//            return englishLocale;
//        }
//        return frenchLocale;
//    }
//    
//    ResourceBundle rb = ResourceBundle.getBundle(languageSelect(Locale.getDefault().toString()));
    
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
