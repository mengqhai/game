/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.input;

/**
 *
 * @author qinghai
 */
public class Selection {

    private volatile String selectedName;

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public boolean hasSelected() {
        return this.selectedName != null;
    }
}
