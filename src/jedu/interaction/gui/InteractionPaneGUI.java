import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Window;

/** The interaction pane GUI
 * @author Yue Shu
 */
public class InteractionPaneGUI extends Application {
  /** The run button for interaction pane */
  private Button button;
  
  /** Tracks number of button clicks */
  private int clicks = 0;
  
  /** Tracks the primaryStage in use. */
  private Stage setPrimaryStage;
  
  private TextArea save;
  /**
   */
  private class ButtonAction implements EventHandler<ActionEvent>{
    /** React to a button click: Return the number of times the button has been clicked.
    * @param e  information about the button click event that occurred
    */
    public void handle(ActionEvent e){
      Button b = (Button) e.getSource();
      clicks = clicks + 1;
      b.setText("Click me");
      setPrimaryStage.sizeToScene();
      save.appendText("\n"+"Click count: "+ clicks);
    }
  }
  
  /** 
   * Overrides the start method of Application to create the GUI with one button in the center of the main window.
   * @param primaryStage the JavaFX main window
   */
  public void start(Stage primaryStage) {
    button = new Button("Click me");
    button.setOnAction(new ButtonAction());
    
    BorderPane pane = new BorderPane();// create a 5 region layout for the window
    save = new TextArea("Click count: 0");
    pane.setCenter(save);
    pane.setTop(button); // add the button to the middle
    setPrimaryStage = primaryStage;
    Scene scene = new Scene(pane);// Create a "scene" that contains this border area

    primaryStage.setTitle("Button Lab GUI");
    primaryStage.setScene(scene);            // Add the "scene" to the main window
    primaryStage.show();                     // Display the window
  }
    
  
  /**
   * The method to launch the program.
   * @param args  The command line arguments.  The arguments are passed on to the JavaFX application.
   */
   public static void main(String[] args) {
     Application.launch(args);
   }
  
}