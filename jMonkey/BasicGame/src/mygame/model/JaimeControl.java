/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.model;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author qinghai
 */
public class JaimeControl extends AbstractControl {

    private Node player;
    private AnimControl control;
    private AnimChannel channel;
    private static final String ANI_IDLE = "Idle";
    private static final String ANI_WALK = "Walk";

    public JaimeControl() {
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.player = (Node)spatial;
        control = player.getControl(AnimControl.class);
        for (String anim : control.getAnimationNames()) {
            System.out.println(anim);
        }
        channel = control.createChannel();
        channel.setAnim(ANI_IDLE);
    }
    
    

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
