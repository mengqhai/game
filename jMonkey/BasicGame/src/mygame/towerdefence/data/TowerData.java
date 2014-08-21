/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

/**
 *
 * @author liuli
 */
public class TowerData {
    public static final String KEY = "TowerData";
    
    private int index, chargesNum;
    
    TowerData() {
        
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getChargesNum() {
        return chargesNum;
    }

    public void setChargesNum(int chargesNum) {
        this.chargesNum = chargesNum;
    }
}
