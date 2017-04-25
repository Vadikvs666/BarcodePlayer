/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.util.prefs.Preferences;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author vadim
 */
public class Settings {

    private StringProperty videoPath;
    private StringProperty ffmpegPath;
    private StringProperty ffmpegOptions;

    public String getFfmpegOptions() {
        return ffmpegOptions.get();
    }

    public String getVideoPath() {
        return videoPath.get();
    }

    public String getFfmpegPath() {
        return ffmpegPath.get();
    }

    public void setVideoPath(String video) {
        videoPath.set(video);
        save();
    }

    public void setFfmpegOptions(String opt) {
        videoPath.set(opt);
        save();
    }

    public void setFfmpegPath(String ffmpeg) {
        ffmpegPath.set(ffmpeg);
        save();
    }

    public Settings() {
        init();
        load();
    }

    public void save() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        prefs.put("videoPath", getVideoPath());
        prefs.put("ffmpegPath", getFfmpegPath());
        prefs.put("ffmpegOptions", getFfmpegOptions());
    }

    private Boolean load() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String video = prefs.get("videoPath", null);
        String ffmpeg = prefs.get("ffmpegPath", null);
        String opt = prefs.get("ffmpegOptions", null);
        if (video != null && ffmpeg != null && opt != null) {
            setFfmpegPath(ffmpeg);
            setVideoPath(video);
            setFfmpegOptions(opt);
            return true;
        } else {
            return false;
        }

    }
    
    public void reset(){
        init();
        save();
    }

    private void init() {
        videoPath = new SimpleStringProperty("video");
        ffmpegPath = new SimpleStringProperty("ffmpeg");
        ffmpegOptions = new SimpleStringProperty("-strict experimental -b:v 555k -b:a 32k -y -f mp4");
    }

}
