package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private TextArea cmdTextArea;
    private TextArea outputTextArea;
    private List<String> prevInput;
    private  int loc;
    /*
    private Button compileButton;
    private TextArea classPathArea;
    private Button loadClassButton;
    private TextFlow outputTextFlow;

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

        /*
        outputTextFlow = new TextFlow();
        outputTextFlow.maxHeight(500);

         */

        prevInput = new ArrayList<>();

        // compileButton.setMinWidth(50);

        ExampleJShell es = new ExampleJShell();

        // TODO: change the content to the actual class path from jedit
        es.evaluate("D:\\GitHub\\EECS132\\Project2\\HW2.java");
        loc = -1;

        cmdTextArea.setOnKeyPressed(action -> {
            if (action.getCode() == KeyCode.UP && loc >= 0) {
                cmdTextArea.setText(prevInput.get(loc));
                loc--;
            }
            if (action.getCode() == KeyCode.DOWN && loc < prevInput.size() - 1) {
                cmdTextArea.setText(prevInput.get(loc + 1));
                loc++;
            }
            if (action.getCode() == KeyCode.ENTER) {
                String input = cmdTextArea.getText();
                // truncate the leading line
                while (input.length() > 0 && input.charAt(0) == '\n') {
                    input = input.substring(1);
                }

                // truncate the tailing line
                while (input.length() > 0 && input.charAt(input.length() - 1) == '\n') {
                    input = input.substring(0, input.length() - 1);
                }
                System.out.println("the input is: " + input);

                if (input.length() < 1) {
                    cmdTextArea.clear();
                    return;
                }

                Text textInput = new Text("> " + input + "\n");
                // outputTextFlow.getChildren().add(textInput);

                outputTextArea.appendText("> " + input + "\n");
                String output = es.useJshell(input + ";");
                if (output.length() > 0) {
                    outputTextArea.appendText(output + "\n");

                    Text textOutput = new Text(output + "\n");
                    // TODO: also change color for error msg
                    switch (es.checkType(input + ";")) {
                        case "int":
                        case "short":
                        case "long":
                        case "float":
                        case "double":
                            textOutput.setFill(Color.BLUE);
                            break;
                        case "String":
                            textOutput.setFill(Color.RED);
                            break;
                        case "char":
                            textOutput.setFill(Color.DEEPPINK);
                            break;
                    }
                    // System.out.println("output type is " + es.checkType(input + ";"));
                    // outputTextFlow.getChildren().add(textOutput);
                }
                prevInput.add(input);
                cmdTextArea.clear();
                loc = prevInput.size() - 1;
                System.out.println("loc is " + loc);
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
        // pane.setBottom(outputTextFlow);
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

