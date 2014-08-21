/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.input;

import com.jme3.input.controls.ActionListener;
import mygame.towerdefence.data.Charge;
import mygame.towerdefence.data.DataService;
import mygame.towerdefence.data.PlayerData;
import mygame.towerdefence.data.TowerData;

/**
 *
 * @author qinghai
 */
public class ChargeListener implements ActionListener{
    
    private Selection selection;

    public ChargeListener(Selection selection) {
        this.selection = selection;
    }
    

    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed && selection.hasSelected()) {
            PlayerData pData = DataService.INSTANCE.getData("Player");
            Object data = DataService.INSTANCE.getData(selection.getSelectedName());
            if (data instanceof TowerData) {
                TowerData towerData = (TowerData)data;
                Charge charge = new Charge();
                if (charge.getPrice()<pData.getBudget()) {
                    towerData.getCharges().add(new Charge());
                    pData.descreaseBudget(charge.getPrice());
                    System.out.println(name+" is changed.");
                } else {
                    System.out.println("Not enough budget.");
                }
            }
        }
    }
}
