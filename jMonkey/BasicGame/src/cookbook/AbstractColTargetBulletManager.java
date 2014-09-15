/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.collision.Collidable;
import com.jme3.renderer.Camera;
import java.util.List;

/**
 *
 * @author liuli
 */
public abstract class AbstractColTargetBulletManager extends AbstractBulletManager {

    List<Collidable> targets;

    public AbstractColTargetBulletManager(Camera cam) {
        super(cam);
    }

    public List<Collidable> getTargets() {
        return targets;
    }

    public void setTargets(List<Collidable> targets) {
        this.targets = targets;
    }
}
