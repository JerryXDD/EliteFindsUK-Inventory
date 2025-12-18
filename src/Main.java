import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.Connection;
/**
 * Main application class following MVC pattern
 * Initializes Model, View, and Controller and connects them
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database connection (makes it available project-wide)
        Connection connection = Connection.getInstance();
        
        // Load the Login View (FXML)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/features/login/login.fxml"));
        Parent root = loader.load();
        
        // Set application icon
        try {
            Image icon = new Image(getClass().getResourceAsStream("/features/login/6d521262-83dd-4cb4-ab11-a9962667551b_removalai_preview.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Could not load application icon: " + e.getMessage());
        }
        
        // Set up the stage
        primaryStage.setTitle("Elite Finds UK");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            // Close database connection on application shutdown
            Connection.getInstance().close();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
