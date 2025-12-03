# Project Guide

This guide explains how the application is structured and how to add new features.

## How MVC Works Here

The application follows a simple flow:

1. **User clicks a button** (View)
2. **Controller receives the click** and calls the model
3. **Model updates its data** and business logic runs
4. **UI automatically updates** because it's bound to model properties

This separation makes the code easier to understand, test, and modify.

## The Main Application

`main.java` is the entry point. It:

-   Creates a model instance
-   Loads the FXML view
-   Gets the controller from the FXML loader
-   Connects the controller to the model
-   Shows the window

Think of it as the setup crew that puts all the pieces together.

## Adding New Features

### Adding a New Model

1. Create a new file in `src/model/` (e.g., `UserModel.java`)
2. Add `package model;` at the top
3. Put your data and business logic here
4. Use JavaFX properties if you want automatic UI updates

### Adding a New View

1. Create a new FXML file in `src/view/` (e.g., `settings.fxml`)
2. Design your UI using FXML elements
3. Set `fx:controller` to your controller class
4. Add `fx:id` attributes to elements you need to access from the controller
5. Update `main.java` to load this FXML if it's a new window/scene

### Adding a New Controller

1. Create a new file in `src/controller/` (e.g., `SettingsController.java`)
2. Add `package controller;` at the top
3. Add `@FXML` fields for UI elements you need to access
4. Add `@FXML` methods for handling user actions
5. Add a `setModel()` method to receive the model
6. Update your FXML's `fx:controller` attribute to point to this controller

### Wiring Everything Together

After creating new components, update `main.java`:

-   Create instances of your models
-   Load the appropriate FXML files
-   Connect controllers to models using `setModel()`

## File Naming

-   Model and Controller classes: `PascalCase.java` (e.g., `UserModel.java`)
-   FXML files: `lowercase_with_underscores.fxml` (e.g., `user_settings.fxml`)
-   Main class: `main.java` (lowercase)

## Project Structure

```
project/
├── main.java              # Starts everything
├── src/
│   ├── model/            # Your data and logic
│   ├── view/             # Your UI layouts
│   └── controller/       # Connects UI to logic
├── bin/                  # Compiled code (ignore this)
├── lib/javafx/          # JavaFX libraries
└── docs/                 # This documentation
```
