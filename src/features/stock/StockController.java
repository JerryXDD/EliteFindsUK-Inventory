package features.stock;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the stock feature
 * Handles user input and updates the view based on model changes
 */
public class StockController implements Initializable {
    private StockModel model;
    
    /**
     * Initialize the controller with the model
     */
    public void setModel(StockModel model) {
        this.model = model;
        // Bind UI elements to model properties here
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This is called after FXML loading
        // Model will be set separately by the main application
    }
    
    /**
     * Clean up when the scene is closed
     */
    public void cleanup() {
        // Add cleanup logic here if needed
    }
}

