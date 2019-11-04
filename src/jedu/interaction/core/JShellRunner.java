package jedu.interaction.core;

import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import jdk.jshell.SnippetEvent;
import jdk.jshell.SourceCodeAnalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class JShellRunner {
    public static void main(String[] args) {
        // hard coded test file for testing result
        new JShellRunner().evaluate("./resources/cmds.jsh");
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

        // If there are any erros print and exit
        if (Snippet.Status.REJECTED.equals(snippetEvent.status())) {
            // should be updated to print the specific error
            System.out.println("Invalid Statement: " + snippetEvent.snippet().toString()
                    + "\nIgnoring execution of above statement.");
        }
    }

    private String trimNewlines(String s) {
        int b = 0;
        while (b < s.length() && s.charAt(b) == '\n') {
            ++b;
        }
        int e = s.length() - 1;
        while (e >= 0 && s.charAt(e) == '\n') {
            --e;
        }
        return s.substring(b, e + 1);
    }

}
