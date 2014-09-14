/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author liuli
 */
public class BulletManager{
    
    List<Collidable> targets;
    List<Bullet> bullets;
    Camera cam;
    Spatial hitMark;

    public BulletManager(Camera cam) {
        this.cam = cam;
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

    public void update(float tpf) {
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

    public List<Collidable> getTargets() {
        return targets;
    }

    public void setTargets(List<Collidable> targets) {
        this.targets = targets;
    }

    public Spatial getHitMark() {
        return hitMark;
    }

    public void setHitMark(Spatial hitMark) {
        this.hitMark = hitMark;
    }
    
    
}
