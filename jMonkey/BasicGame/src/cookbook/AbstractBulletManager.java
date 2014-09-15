/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author qinghai
 */
public abstract class AbstractBulletManager extends AbstractControl {
    protected Camera cam;
    protected Spatial hitMark;
    
    public AbstractBulletManager(Camera cam) {
        this.cam = cam;
    }

    public abstract void fire();

    public void setHitMark(Spatial hitMark) {
        this.hitMark = hitMark;
    }
 
}
