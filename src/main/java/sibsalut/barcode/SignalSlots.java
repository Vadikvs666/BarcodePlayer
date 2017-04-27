/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author vadim
 */
class SignalConnectorStruct {

    private Object sender;
    private Object reciever;
    private String signal;
    private String slot;

    public void setSender(Object sender) {
        this.sender = sender;
    }

    public void setReciever(Object reciever) {
        this.reciever = reciever;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Object getSender() {
        return sender;
    }

    public Object getReciever() {
        return reciever;
    }

    public String getSignal() {
        return signal;
    }

    public String getSlot() {
        return slot;
    }
}

class Connections {

    private ArrayList<SignalConnectorStruct> connects;

    public void add(SignalConnectorStruct scs) {
        connects.add(scs);
    }

    public void remove(SignalConnectorStruct scs) {
        connects.remove(scs);
    }

    public ArrayList<SignalConnectorStruct> find(Object sen, String signal) {
        ArrayList<SignalConnectorStruct> list = new ArrayList<>();
        for (SignalConnectorStruct entry : connects) {
            if (entry.getSender() == sen && entry.getSignal() == signal) {
                list.add(entry);
            }
        }
        return list;

    }

    public Connections() {
        connects = new ArrayList();
    }
}

public class SignalSlots {

    private static SignalSlots instance;
    private Connections connections = null;

    public static synchronized SignalSlots getInstance() {
        if (instance == null) {
            instance = new SignalSlots();

        }
        return instance;
    }

    public void connect(Object sender, String signal, Object reciever, String slot) {
        if (connections == null) {
            connections = new Connections();
        }
        SignalConnectorStruct connector = new SignalConnectorStruct();
        connector.setSender(sender);
        connector.setSignal(signal);
        connector.setReciever(reciever);
        connector.setSlot(slot);
        System.out.println("Connect signal: " + signal + " with slot " + slot);
        connections.add(connector);
    }

    public void emit(Object sender, String signal) {
        ArrayList<SignalConnectorStruct> conn = new ArrayList();
        if (connections != null) {
            conn = connections.find(sender, signal);
            if (conn != null) {
                for (SignalConnectorStruct entry : conn) {
                    System.out.println("emit signal: " + signal + " founded slot " + entry.getSlot());
                    invokeSlot(entry.getReciever(), entry.getSlot());
                }
            }else{
               System.out.println("emit signal: " + signal + " no slot founds " ); 
            }
        }else{
            System.out.println("emit signal: " + signal + "No connects");
        }

    }

    private void invokeSlot(Object rec, String slot) {
        try {
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    Object[] args = new Object[]{};
                    rec.getClass().getDeclaredMethod(slot).invoke(rec, args);
                    System.out.println("invoke slot " + slot);
                    return null;
                }
            };
            Platform.runLater(task);
        } catch (IllegalArgumentException | SecurityException ex) {
            Logger.getLogger(SignalSlots.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
