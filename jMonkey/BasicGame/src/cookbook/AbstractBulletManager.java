/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author qinghai
 */
public abstract class AbstractBulletManager extends AbstractControl {

    public abstract void fire();

    public abstract void setHitMark(Spatial hitMark);
    
}
