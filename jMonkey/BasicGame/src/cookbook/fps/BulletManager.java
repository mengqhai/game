/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author liuli
 */
public class BulletManager extends AbstractColTargetBulletManager{
    List<Bullet> bullets;

    public BulletManager(Camera cam) {
        super(cam);
        bullets = new ArrayList<Bullet>();
        targets = new ArrayList<Collidable>();
    }
    
    public BulletManager(Camera cam, Spatial hitMark) {
        this(cam);
        this.hitMark = hitMark;
    }
    
    public void fire() {
        bullets.add(new Bullet(cam.getLocation().clone(), cam.getDirection().clone()));
    }

    private void doUpdate(float tpf) {
        Iterator<Bullet> iter = bullets.iterator();
        while(iter.hasNext()) {
            Bullet b = iter.next();
            b.update(tpf);
            CollisionResult result = b.checkCollision(targets);
            if (result != null) {
                System.out.println("hit "+result.getGeometry().getName());
                if (hitMark!=null) {
                    hitMark.setLocalTranslation(result.getContactPoint());
                }
            }
            if (!b.isAlive()) {
                iter.remove();
                System.out.println("Bullet removed");
            }
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.doUpdate(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    
    
}
