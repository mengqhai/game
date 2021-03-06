/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 *
 * @author liuli
 */
public class RayCastingBulletManager extends AbstractColTargetBulletManager {

    public RayCastingBulletManager(Camera cam) {
        super(cam);
    }

    @Override
    public void fire() {
        Ray r = new Ray(cam.getLocation(), cam.getDirection());
        CollisionResults collRes = new CollisionResults();
        for (Collidable g : targets) {
            g.collideWith(r, collRes);
        }
        if (collRes.size() > 0) {
            Vector3f hitPoint = collRes.getClosestCollision().getContactPoint();
            System.out.println("hit " + collRes.getClosestCollision().getGeometry().getName());
            if (hitMark != null) {
                hitMark.setLocalTranslation(hitPoint);
            }
            if (hitSpark != null) {
                this.doSpark(hitPoint);
            }
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
