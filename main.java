import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import features.welcome.WelcomeModel;
import features.welcome.WelcomeController;
import features.homepage.HomepageController;
import core.Connection;
/**
 * Main application class following MVC pattern
 * Initializes Model, View, and Controller and connects them
 */
public class main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database connection (makes it available project-wide)
        Connection connection = Connection.getInstance();
        
        // Load the View (FXML)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("src/features/homepage/homepage.fxml"));
        Parent root = loader.load();
        HomepageController controller = loader.getController();
        
        // Get the Controller and connect it to the Model
        //WelcomeController controller = loader.getController();
        //controller.setModel(model);
        
        // Set up the stage
        primaryStage.setTitle("Elite Finds UK Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> {
            if (controller != null) {
                controller.cleanup();
            }
            // Close database connection on application shutdown
            Connection.getInstance().close();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
