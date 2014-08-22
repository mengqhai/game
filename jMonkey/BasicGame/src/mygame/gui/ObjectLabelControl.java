/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.gui;

import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author qinghai
 */
public class ObjectLabelControl extends AbstractControl{
    
    private Node guiNode;
    private Camera cam;
    private BitmapText label;

    public ObjectLabelControl(Node guiNode, Camera cam, BitmapText label) {
        this.guiNode = guiNode;
        this.cam = cam;
        this.label = label;
    }
    
    

    @Override
    protected void controlUpdate(float tpf) {
        // http://hub.jmonkeyengine.org/forum/topic/draw-2d-text-in-3d-world/
        Vector3f location = cam.getScreenCoordinates(spatial.getLocalTranslation());
        label.setLocalTranslation(location);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
