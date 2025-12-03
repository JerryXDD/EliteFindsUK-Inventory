# Model Layer

The Model holds your application's data and business logic. It's where the actual work happens, separate from how things look or how users interact with it.

## What It Does

The Model manages:

-   Application state (like counters, messages, user data)
-   Business rules and logic
-   Data validation and processing

## How It Works

The `Model.java` class uses JavaFX properties to automatically notify the UI when data changes. For example, when you update a message in the model, any UI element bound to that message will update automatically.

### Key Methods

-   `handleButtonClick()` - Contains the logic for what happens when a button is clicked
-   `getMessage()` / `setMessage()` - Get or set the current message
-   `messageProperty()` - Returns the property that the UI can bind to
-   `reset()` - Resets the model to its initial state

The model doesn't know about buttons or labels - it just manages data and logic. The controller connects it to the UI.
