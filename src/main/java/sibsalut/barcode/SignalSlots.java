/*
 *  Автор Вагин Вадим Сергеевич
 * e-mail: vadim@hoz.center
 */
package sibsalut.barcode;

import java.util.List;

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

    private List<SignalConnectorStruct> connects;

    public void add(SignalConnectorStruct scs) {
        connects.add(scs);
    }

    public void remove(SignalConnectorStruct scs) {
        connects.remove(scs);
    }

    public List<SignalConnectorStruct> find(Object sen, String signal) {
        List<SignalConnectorStruct> list = null;
        for (SignalConnectorStruct entry : connects) {
            if (entry.getSender() == sen && entry.getSignal() == signal) {
                list.add(entry);
            }
        }
        return list;

    }
}

public class SignalSlots {

    private Connections connections;

    public void connect(Object sender, String signal, Object reciever, String slot) {
        SignalConnectorStruct connector = new SignalConnectorStruct();
        connector.setSender(sender);
        connector.setSignal(signal);
        connector.setReciever(reciever);
        connector.setSlot(slot);
    }

    public void emit(Object sender, String signal) throws NoSuchMethodException {
        List<SignalConnectorStruct> conn = connections.find(sender, signal);
        if (conn != null) {
            for (SignalConnectorStruct entry : conn) {
                    invokeSlot(entry.getReciever(), entry.getSlot());
            }
        }
    }

    private void invokeSlot(Object rec, String slot) throws NoSuchMethodException {
        rec.getClass().getDeclaredMethod(slot);
    }
}
