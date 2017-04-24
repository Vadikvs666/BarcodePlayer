/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            Process p = Runtime.getRuntime().exec(shell);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
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

    public BufferedReader getOutPut() throws IOException {
        String s;
        System.out.println("Here is the standard output of the command:\n");
        while ((s = stdOutput.readLine()) != null) {
            System.out.println(s);
        }
        return stdOutput;
    }

    public BufferedReader getError() throws IOException {
        String s;
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        return stdError;
    }
}
