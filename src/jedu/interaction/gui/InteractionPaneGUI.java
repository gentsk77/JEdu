import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InteractionPaneGUI extends Application  {

    private TextArea cmdTextArea;
    private TextArea outputTextArea;
    private Button compileButton;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Interaction Pane");
        final BorderPane pane = new BorderPane();

        // cmdTextArea is the text area for command input
        cmdTextArea = new TextArea();
        compileButton = new Button("Compile and Run");
        outputTextArea = new TextArea();

        compileButton.setMinWidth(50);

        compileButton.setOnAction(action -> {

            // TODO: change the line below to run jshell and evaluate
            
            outputTextArea.setText(cmdTextArea.getText());
        });

        pane.setCenter(compileButton);
        pane.setTop(cmdTextArea);
        pane.setBottom(outputTextArea);

        final Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}