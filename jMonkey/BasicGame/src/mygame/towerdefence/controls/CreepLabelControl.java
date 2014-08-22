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
import mygame.towerdefence.data.CreepData;
import mygame.towerdefence.data.DataService;

/**
 *
 * @author liuli
 */
public class CreepLabelControl extends SpatialLabelControl {

    public CreepLabelControl(Node guiNode, Camera cam, BitmapFont guiFont) {
        super(guiNode, cam, guiFont);
        this.visibleDistance=200;
    }

    public CreepLabelControl(Node guiNode, Camera cam, BitmapText label) {
        super(guiNode, cam, label);
        this.visibleDistance=200;
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        CreepData cData = DataService.INSTANCE.getData(spatial.getName());
        label.setText(""+cData.getHealth());
    }    
}
