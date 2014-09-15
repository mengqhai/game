/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author qinghai
 */
public class PhyBulletManager extends AbstractBulletManager {

    private PhysicsSpace phySpace;
    private List<PhyBullet> bullets = new ArrayList<PhyBullet>();

    public PhyBulletManager(Camera cam, PhysicsSpace phySpace) {
        super(cam);
        this.phySpace = phySpace;
    }

    public void fire() {
        PhyBullet b = new PhyBullet(cam.getLocation().add(cam.getDirection().mult(1.8f)), cam.getDirection(), this.getBulletNode(), phySpace);
        b.setHitMark(hitMark);
        bullets.add(b);
    }

    @Override
    protected void controlUpdate(float tpf) {
        Iterator<PhyBullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            PhyBullet bullet = iter.next();
            if (!bullet.isAlive()) {
                Vector3f hitPoint = bullet.getHitPoint();
                if (hitPoint != null && hitSpark != null) {
                    doSpark(hitPoint);
                }

                bullet.clean();
                iter.remove();
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
