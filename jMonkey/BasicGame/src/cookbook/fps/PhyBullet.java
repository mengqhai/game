/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.UUID;

/**
 *
 * @author qinghai
 */
public class PhyBullet implements PhysicsCollisionListener, PhysicsTickListener {

    private Vector3f velocity;
    private Vector3f origin;
    private Spatial bullet;
    private Node parent;
    private PhysicsSpace phySpace;
    private RigidBodyControl phyCtrl;
    private volatile boolean alive = true;
    private Spatial hitMark;
    private float speed = 800f;
    private final static int RANGE = 1500;
    private float distance = 0;
    private Vector3f hitPoint;
    
    public PhyBullet(Vector3f origin, Vector3f dir, Node parent, PhysicsSpace phySpace) {
        this.origin = origin.clone();
        this.velocity = dir.mult(speed);
        this.parent = parent;
        this.phySpace = phySpace;
        init();
    }
    
    private void init() {
        bullet = new Node("Bullet_" + UUID.randomUUID());
        BoxCollisionShape box = new BoxCollisionShape(new Vector3f(0.2f, 0.2f, 0.2f));
        phyCtrl = new RigidBodyControl(box, 0.001f);
        bullet.setLocalTranslation(origin);
        bullet.addControl(phyCtrl);
        phyCtrl.setLinearVelocity(velocity);
        phyCtrl.setCcdMotionThreshold(0.001f);
        phyCtrl.setCcdSweptSphereRadius(0.1f);
        phySpace.add(phyCtrl);
        parent.attachChild(bullet);
        phySpace.addCollisionListener(this);
        phySpace.addTickListener(this);
    }
    
    public void clean() {
        alive = false;
        bullet.getParent().detachChild(bullet);
        phySpace.remove(phyCtrl);
        phySpace.removeCollisionListener(this);
        phySpace.removeTickListener(this);
        System.out.println("Bullet removed:"+bullet.getName());
    }
    
    public Vector3f getHitPoint() {
        return hitPoint;
    }
    
    public void collision(PhysicsCollisionEvent event) {
        Spatial target = null;
        Vector3f point =null;
        if (bullet.getName().equals(event.getNodeA().getName())) {
            target = event.getNodeB();
            point = event.getPositionWorldOnB();
        } else if (bullet.getName().equals(event.getNodeB().getName())){
            target = event.getNodeA();
            point = event.getPositionWorldOnA();
        }
        if (target!=null) {
            System.out.println("Hit "+target.getName()+" at "+point);
            if (hitMark!=null) {
                hitMark.setLocalTranslation(point);
                hitPoint = point;
            }
            alive = false;
        }
        
    }

    public boolean isAlive() {
        return alive;
    }

    public void setHitMark(Spatial hitMark) {
        this.hitMark = hitMark;
    }

    public void prePhysicsTick(PhysicsSpace space, float tpf) {
       distance += tpf * speed;
       if (distance > RANGE) {
           alive = false;
       } 
    }

    public void physicsTick(PhysicsSpace space, float tpf) {
    }

}
