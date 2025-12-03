# Controller Layer

The Controller is the middleman between the View (what users see) and the Model (the data and logic). It handles user actions and coordinates updates.

## What It Does

When a user clicks a button or interacts with the UI:

1. The controller receives the event
2. It calls the appropriate method on the model
3. The model updates its data
4. The controller binds the UI to model properties so the view updates automatically

## How It Works

The `Controller.java` class:

-   Has `@FXML` fields that reference UI elements from the FXML file
-   Has `@FXML` methods that handle user actions (like button clicks)
-   Receives a model instance from `main.java` via `setModel()`
-   Binds UI elements to model properties so they update automatically

### Example

When a button is clicked:

```java
@FXML
private void handleButtonClick() {
    model.handleButtonClick();  // Tell the model what happened
}
```

The model updates its data, and because the label is bound to the model's message property, it updates automatically - no manual refresh needed.

## Keep It Simple

Controllers should be thin - they just pass messages between the view and model. The actual logic lives in the model, and the appearance lives in the view.
