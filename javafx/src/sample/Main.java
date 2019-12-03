package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private TextArea cmdTextArea;
    private TextArea outputTextArea;
    /*
    private Button compileButton;
    private TextArea classPathArea;
    private Button loadClassButton;

     */

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Interaction Pane");
        final BorderPane pane = new BorderPane();

        // cmdTextArea is the text area for command input
        //input
        cmdTextArea = new TextArea();
        cmdTextArea.setPromptText("> ");

        /*
        compileButton = new Button("Compile and Run");
        classPathArea = new TextArea();
        loadClassButton = new Button("Load Class");
         */

        //output
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);

        // compileButton.setMinWidth(50);

        ExampleJShell es = new ExampleJShell();

        // TODO: change the content to the actual class path from jedit
        es.evaluate("D:\\GitHub\\EECS132\\Project2\\HW2.java");

        cmdTextArea.setOnKeyPressed(action -> {
            if (action.getCode() == KeyCode.ENTER) {
                String input = cmdTextArea.getText();
                // truncate the tailing line
                while (input.length() > 0 && input.charAt(input.length() - 1) == '\n') {
                    input = input.substring(0, input.length() - 1);
                }
                System.out.println("the input is: " + input);

                if (input.length() < 1) {
                    cmdTextArea.clear();
                    return;
                }

                outputTextArea.appendText("> " + input + "\n");
                String output = es.useJshell(input + ";");
                if (output.length() > 0) {
                    outputTextArea.appendText(output + "\n");
                }
                cmdTextArea.clear();
            }
        });

        /*
        compileButton.setOnAction(action -> {

            String input = cmdTextArea.getText();
            if (input.length() < 1) {
                return;
            }

            // make that users don't need to manually input semicolon
            if (input.charAt(input.length() - 1) != ';') {
                input += ";";
            }
            outputTextArea.appendText("> " + input + "\n");
            String output = es.useJshell(input);
            if (output.length() > 0) {
                outputTextArea.appendText(es.useJshell(input) + "\n");
            }
            cmdTextArea.clear();
        });

         */

        /*
        loadClassButton.setOnAction(action -> {
            es.evaluate(loadClassButton.getText());
            outputTextArea.setText("Load success\n");
        });
         */

        // pane.setRight(compileButton);
        pane.setCenter(cmdTextArea);
        pane.setBottom(outputTextArea);
        // pane.setLeft(classPathArea);
        // pane.setRight(loadClassButton);

        final Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}

