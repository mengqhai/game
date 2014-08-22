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
import mygame.towerdefence.data.DataService;
import mygame.towerdefence.data.PlayerData;

/**
 *
 * @author liuli
 */
public class PlayerLabelControl extends SpatialLabelControl{

    public PlayerLabelControl(Node guiNode, Camera cam, BitmapFont guiFont) {
        super(guiNode, cam, guiFont);
        this.visibleDistance = 200f;
    }

    public PlayerLabelControl(Node guiNode, Camera cam, BitmapText label) {
        super(guiNode, cam, label);
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        PlayerData pData = DataService.INSTANCE.getData("Player");
        label.setText(""+pData.getHealth());
    }
    
    
    
}
