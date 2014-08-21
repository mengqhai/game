/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuli
 */
public class TowerData  implements Savable{
    public static final String KEY = "TowerData";
    
    private int index, chargesNum=20, range = 100, shootInterval = 2000;
    
    private List<Charge> charges = new ArrayList<Charge>();
    
    TowerData() {
        
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

//    public int getChargesNum() {
//        return chargesNum;
//    }
//
//    public void setChargesNum(int chargesNum) {
//        this.chargesNum = chargesNum;
//    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public List<Charge> getCharges() {
        return charges;
    }

    public int getShootInterval() {
        return shootInterval;
    }

    public void setShootInterval(int shootInterval) {
        this.shootInterval = shootInterval;
    }
    
    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
