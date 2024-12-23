package controller;

import view.Ui;

/**
 * The App class is the main entry point for the application. It initializes the
 * user interface (Ui), sets up the controller, and initiates the running of the
 * application.
 */
public class App {

  /**
   * The main method serves as the entry point of the application. It creates an
   * instance of the user interface (Ui), initializes test data, and configures
   * the
   * controller to manage the application flow.
   *
   * @param args Command-line arguments, if any, provided when launching the
   *             application.
   */
  public static void main(String[] args) {

    // Initialize the user interface (view)
    Ui ui = new Ui();
    // DataInitializer dataInitializer = new DataInitializer();
    // Initialize the controller with the view
    Controller controller = new Controller(ui);

    controller.initializeData();
    // Start the application
    controller.run();
  }
}
