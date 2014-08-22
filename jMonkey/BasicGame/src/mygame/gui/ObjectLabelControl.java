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
public class ObjectLabelControl extends AbstractControl {

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
        // find the top of the spatial

        // do not show the lable if 
        float distance = cam.getLocation().distance(spatial.getLocalTranslation());
        if (distance >= 20 || Camera.FrustumIntersect.Outside.equals(cam.contains(spatial.getWorldBound()))) {
            guiNode.detachChild(label);
            return;
        }

        if (!guiNode.hasChild(label)) {
            guiNode.attachChild(label);
        }
        Vector3f sLoc = spatial.getLocalTranslation();
        Vector3f topLoc = sLoc.add(0, 1.5f + 0.6f / distance, 0);
        Vector3f location = cam.getScreenCoordinates(topLoc);
        label.setLocalTranslation(location);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
