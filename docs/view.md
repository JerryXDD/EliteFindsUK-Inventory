# View Layer

The View defines what the user sees - the buttons, labels, and layout of your application.

## What It Does

Views are written in FXML, which is an XML format for describing JavaFX UIs. Instead of writing Java code to create buttons and arrange them, you describe the layout in FXML.

## How It Works

The `sample.fxml` file contains:

-   Layout containers (like `VBox` for vertical arrangement)
-   UI components (buttons, labels, text fields, etc.)
-   Connections to the controller (using `fx:id` and `fx:controller`)

### Connecting to the Controller

-   `fx:controller="controller.Controller"` - Tells FXML which controller to use
-   `fx:id="clickButton"` - Gives the button a name so the controller can reference it
-   `onAction="#handleButtonClick"` - Links the button click to a method in the controller

When the application starts, `main.java` loads this FXML file and creates the actual UI components from it.

## Tips

-   Keep FXML focused on layout and appearance
-   Use meaningful `fx:id` names that match your controller field names
-   Don't put business logic in FXML - that belongs in the model
