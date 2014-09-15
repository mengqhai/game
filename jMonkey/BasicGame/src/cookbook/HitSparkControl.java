/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author liuli
 */
public class HitSparkControl extends AbstractControl {

    private static final float LIEF_TIME = 1f;
    private float lifeTime = 0;

    public HitSparkControl() {
    }
    
    public Node getSpark() {
        return (Node)spatial;
    }

    public void doSpark() {
        ParticleEmitter emitter = (ParticleEmitter)getSpark().getChild("Emitter");
        emitter.emitAllParticles();
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (lifeTime == 0) {
            doSpark();
        } else if (lifeTime > LIEF_TIME) {
            getSpark().getParent().detachChild(spatial);
        }
        lifeTime += tpf;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
