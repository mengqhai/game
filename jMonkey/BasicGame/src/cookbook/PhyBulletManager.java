/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author qinghai
 */
public class PhyBulletManager extends AbstractBulletManager{
    private Camera cam;
    private Spatial hitMark;
    private PhysicsSpace phySpace;
    private List<PhyBullet> bullets = new ArrayList<PhyBullet>();
    
    public PhyBulletManager(Camera cam, PhysicsSpace phySpace) {
        this.cam = cam;
        this.phySpace = phySpace;
    }

    public void setHitMark(Spatial hitMark) {
        this.hitMark = hitMark;
    }
    
    public void fire() {
        PhyBullet b = new PhyBullet(cam.getLocation().add(cam.getDirection().mult(3f)), cam.getDirection(), this.getBulletNode(), phySpace);
        b.setHitMark(hitMark);
        bullets.add(b);
    }
    
    public Node getBulletNode() {
        return (Node)this.getSpatial();
    }

    @Override
    protected void controlUpdate(float tpf) {
        Iterator<PhyBullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            PhyBullet bullet = iter.next();
            if (!bullet.isAlive()) {
                bullet.clean();
                iter.remove();
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
