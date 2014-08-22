/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.model;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
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
    private AnimEventListener aListener = new AnimEventListener() {
        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
            System.out.println("A loop of "+animName+" ended.");
        }

        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
            if (ANI_WALK.equals(animName)) {
                System.out.println("Jaime started walking");
            } else if (ANI_IDLE.equals(animName)) {
                System.out.println("Jaime started being idle.");
            }
        }
    };

    public JaimeControl() {
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.player = (Node) spatial;
        control = player.getControl(AnimControl.class);
        for (String anim : control.getAnimationNames()) {
            System.out.println(anim);
        }
        channel = control.createChannel();
        channel.setAnim(ANI_IDLE);
        control.addListener(aListener);
    }

    public void toggleWalk() {
        if (!ANI_WALK.equals(channel.getAnimationName())) {
            channel.setAnim(ANI_WALK);
        } else {
            channel.setAnim(ANI_IDLE);
        }
    }

    public void move(float x, float y, float z) {
        player.move(x, y, z);
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
