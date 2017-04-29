/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vadim
 */
public class Slot {
    private String func;
    private SignalSlots ss= SignalSlots.getInstance();
    private Object owner;

    public Object getOwner() {
        return owner;
    }
    public Slot(String func){
        this.func=func;
    }
    public Slot(Object owner,String func){
        this.func=func;
        this.owner=owner;
    }
    public String get(){
        return func;
    }

    public void connect(Signal signal, Slot slot){
        ss.connect(signal, owner,slot);
    }
    
   
}
