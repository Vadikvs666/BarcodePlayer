/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author vadim
 */
public class ShellCommand{

    private String command;
    private String[] options;
    private BufferedReader stdOutput;
    private BufferedReader stdError;

    public void setCommand(String shellCommand) {
        command = shellCommand;
    }

    public void setOptions(String[] opt) {
        options = opt;
    }

    public Boolean execute() {
        try {
            String shell = build();
            System.out.println("Executed command: "+shell);
            Process p = Runtime.getRuntime().exec(shell);
            stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            return true;

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            return false;
        }
    }

    private String build() {
        String shellCommand = command;
        for (int i = 0; i < options.length; i++) {
            shellCommand += " ";
            shellCommand += options[i];
        }
        return shellCommand;
    }

    public void getOutPut() throws IOException {
        String s;
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdOutput.readLine()) != null) {
            System.out.println(s);
        }
        
    }

    public void getError() throws IOException {
        String s;
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        
    }
}
