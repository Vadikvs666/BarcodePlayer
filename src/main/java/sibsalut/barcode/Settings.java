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

    private final StringProperty videoPath;
    private final StringProperty ffmpegPath;

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

    public void setFfmpegPath(String ffmpeg) {
        ffmpegPath.set(ffmpeg);
        save();
    }

    public Settings() {
        videoPath = new SimpleStringProperty("video");
        ffmpegPath = new SimpleStringProperty("ffmpeg");
        load();
    }    

    public void save() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        prefs.put("videoPath", getVideoPath());
        prefs.put("ffmpegPath", getFfmpegPath());
    }

    private Boolean load() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String video = prefs.get("videoPath", null);
        String ffmpeg = prefs.get("ffmpegPath", null);
        if (video != null&& ffmpeg!=null) {
            setFfmpegPath(ffmpeg);
            setVideoPath(video);
            return true;
        } else {
            return false;
        }
        
    }
    
   

}
