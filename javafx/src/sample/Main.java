package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private TextArea cmdTextArea;
    private TextArea classPathArea;
    private TextArea outputTextArea;
    private Button compileButton;
    private Button loadClassButton;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Interaction Pane");
        final BorderPane pane = new BorderPane();

        // cmdTextArea is the text area for command input
        //input
        cmdTextArea = new TextArea();
        compileButton = new Button("Compile and Run");
        classPathArea = new TextArea();
        loadClassButton = new Button("Load Class");

        //output
        outputTextArea = new TextArea();

        compileButton.setMinWidth(50);

        ExampleJShell es = new ExampleJShell();
        compileButton.setOnAction(action -> {

            String input = cmdTextArea.getText();
            outputTextArea.setText(es.useJshell(input));
        });

        loadClassButton.setOnAction(action -> {
            es.evaluate(classPathArea.getText());
            outputTextArea.setText("Load success");
        });

        pane.setCenter(compileButton);
        pane.setTop(cmdTextArea);
        pane.setBottom(outputTextArea);
        pane.setLeft(classPathArea);
        pane.setRight(loadClassButton);

        final Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}

