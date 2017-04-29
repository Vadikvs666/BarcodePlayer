/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author vadim
 */
public class Ffmpeg implements Runnable {

    private Thread thread;
    private final File file;
    private final String barcode;
    private Settings settings;
    public Signal complete=new Signal();

    public Ffmpeg(File inputFile, String code) {
        file = inputFile;
        barcode = code;
        settings = new Settings();
       
    }

    public void start() {
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        String copyed_file = settings.getVideoPath() + File.separator + barcode + "-not_converted";
        String newFileName = settings.getVideoPath() + File.separator + barcode + ".mp4";
        File dest = new File(copyed_file);
        try {
            FileUtils.copyFile(file, dest);
        } catch (IOException ex) {
            Logger.getLogger(Ffmpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShellCommand shell = new ShellCommand();
        shell.setCommand(settings.getFfmpegPath());
        String[] options = {
            "-i",
            copyed_file,
            settings.getFfmpegOptions(),
            newFileName
        };
        shell.setOptions(options);
        shell.execute();
        shell.getError();
        complete.emit(new Object[]{barcode,file.getAbsolutePath(),newFileName});
        deleteFile(dest);
    }
    public void deleteFile(File file){
        file.delete();
    }

}
