package sample;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.*;
import java.util.List;

import jdk.jshell.*;
import jdk.jshell.Snippet.Status;

import java.nio.file.Files;
import java.nio.file.Paths;

class ExampleJShell {
    JShell js = JShell.create();

    public String useJshell(String fileAsString) {


        /*
        try {
            String fileAsString = "";
            InputStream is = new FileInputStream("C:\\Users\\guo\\Desktop\\Order.java");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Not Found");
        } catch (IOException e) {
            System.out.println("IO");
        }
        */

        Console console = System.console();
        //String input = console.readLine();
        String input = fileAsString;
        List<SnippetEvent> events = js.eval(input);
        //System.out.println(events);
        for (SnippetEvent e : events) {
            StringBuilder sb = new StringBuilder();
            if (e.causeSnippet() == null) {
                // We have a snippet creation event
                switch (e.status()) {
                    case VALID:
                        sb.append("Successful ");
                        break;
                    case RECOVERABLE_DEFINED:
                        sb.append("With unresolved references ");
                        break;
                    case RECOVERABLE_NOT_DEFINED:
                        sb.append("Possibly reparable, failed ");
                        break;
                    case REJECTED:
                        sb.append("Failed ");
                        break;
                }
                if (e.previousStatus() == Status.NONEXISTENT) {
                    sb.append("addition");
                } else {
                    sb.append("modification");
                }
                sb.append(" of ");
                sb.append(e.snippet().source());
                //System.out.println(sb);
                if (e.value() != null) {
                    sb.append(" Value is: " + e.value());
                }
                return sb.toString();

            }
        }
        return "";
    }

    public void evaluate(String scriptFileName) {
        try (JShell jshell = JShell.create()) {
            // Handle snippet events. We can print value or take action if evaluation
            // failed.
            jshell.onSnippetEvent(snippetEvent -> snippetEventHandler(snippetEvent));

            String scriptContent = new String(Files.readAllBytes(Paths.get(scriptFileName)));
            String s = scriptContent;
            while (true) {
                // Read source line by line till semicolin
                // notice that unlike DrJava, all expressions and statements are required to end
                // with ;
                SourceCodeAnalysis.CompletionInfo an = jshell.sourceCodeAnalysis().analyzeCompletion(s);
                if (!an.completeness().isComplete()) {
                    break;
                }
                // If there are any method declaration or class declaration in new lines,
                // resolve it
                // otherwise execution errors will be thrown
                jshell.eval(trimNewlines(an.source()));
                // Exit if there are no more expressions to evaluate. EOF
                if (an.remaining().isEmpty()) {
                    break;
                }
                // If there is semicolon, execute next seq
                s = an.remaining();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void snippetEventHandler(SnippetEvent snippetEvent) {
        String value = snippetEvent.value();
        if (value != null && value.trim().length() > 0) {
            // Prints output of code evaluation if the value is not "null"
            if (value.equals("null")) {
                System.out.println();
            } else {
                System.out.println(value);
            }
        }

        // If there are any errors, print and exit
        if (Snippet.Status.REJECTED.equals(snippetEvent.status())) {
            // should be updated to print the specific error
            System.out.println("Invalid Statement: " + snippetEvent.snippet().toString()
                    + "\nIgnoring execution of above statement.");
        }
    }

    private String trimNewlines(String s) {
        int b = 0;
        while (b < s.length() && s.charAt(b) == '\n') {
            b++;
        }
        int e = s.length() - 1;
        while (e >= 0 && s.charAt(e) == '\n') {
            e--;
        }
        return s.substring(b, e + 1);
    }
}
