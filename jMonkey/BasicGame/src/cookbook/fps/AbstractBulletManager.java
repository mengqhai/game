/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author qinghai
 */
public abstract class AbstractBulletManager extends AbstractControl {
    protected Camera cam;
    protected Spatial hitMark;
    protected Spatial hitSpark;
    
    public AbstractBulletManager(Camera cam) {
        this.cam = cam;
    }

    public abstract void fire();

    public void setHitMark(Spatial hitMark) {
        this.hitMark = hitMark;
    }
    
    public void doSpark(Vector3f hitPoint) {
        Spatial spark = getHitSparkClone();
        spark.addControl(new HitSparkControl());
        getBulletNode().attachChild(spark);
        spark.setLocalTranslation(hitPoint);
    }
    
    public Spatial getHitSparkClone() {
        return this.hitSpark.clone();
    }
    
    public void setHitSpark(Spatial hitSpark) {
        this.hitSpark = hitSpark;
    }
    
    public Node getBulletNode() {
        return (Node)spatial;
    }
 
}
