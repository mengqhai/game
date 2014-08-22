/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.controls;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import mygame.gui.SpatialLabelControl;
import mygame.towerdefence.data.Charge;
import mygame.towerdefence.data.DataService;
import mygame.towerdefence.data.TowerData;

/**
 *
 * @author liuli
 */
public class TowerLabelControl extends SpatialLabelControl {

    public TowerLabelControl(Node guiNode, Camera cam, BitmapFont guiFont) {
        super(guiNode, cam, guiFont);
        this.visibleDistance=200;
    }

    public TowerLabelControl(Node guiNode, Camera cam, BitmapText label) {
        super(guiNode, cam, label);
        this.visibleDistance=200;
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        TowerData tData = DataService.INSTANCE.getData(spatial.getName());
        StringBuilder sb = new StringBuilder("b:");
        Charge top = tData.getCharges().peek();
        if (top == null) {
            sb.append("0");
        } else {
            sb.append(top.getBullets());
        }
        sb.append("  c:");
        sb.append(tData.getCharges().size());
        label.setText(sb.toString());
    }
    
    
    
}
