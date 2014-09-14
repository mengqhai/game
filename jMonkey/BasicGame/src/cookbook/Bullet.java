/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bullet class can almost be seen as a slow ray. The Ray instance we have
 * in Bullet is mostly out of convenience, since it's already prepared to
 * collide with targets. By incrementing the position of the ray and having a
 * short limit for it, we have a Ray instance that takes little steps forward in
 * the game world, checking for collisions in each update.
 *
 * If a collision has occurred, the returned CollisionResult contains
 * information about where the collision has occurred, with what, and whether it
 * can be used to build further functionalities.
 *
 * @author liuli
 */
public class Bullet {

    private Vector3f worldPosition;
    private Vector3f direction;
    private float speed = 150;
    private Ray ray;
    private final static int RANGE = 1500;
    private float distance = 0;
    private boolean alive = true;

    public Bullet(Vector3f origin, Vector3f direction) {
        ray = new Ray(origin, direction);
        this.worldPosition = origin;
        this.direction = direction;
    }

    public void update(float tpf) {
        distance += speed * tpf;
        ray.setLimit(distance);
        //worldPosition.addLocal(direction.mult(ray.limit));
        if (distance >= RANGE) {
            alive = false;
        }
    }

    public CollisionResult checkCollision(List<Collidable> targets) {

        List<CollisionResults> resultsList = new ArrayList<CollisionResults>();

        for (Collidable g : targets) {
            CollisionResults collRes = new CollisionResults();
            g.collideWith(ray, collRes);
            if (collRes.size() > 0) {
                resultsList.add(collRes);
            }
        }
        float closestDistance = Float.MAX_VALUE;
        CollisionResults closest = null;
        System.out.println("-->hit count:"+resultsList.size());
        for (CollisionResults results : resultsList) {
            System.out.println("-->candidate: "+results.getClosestCollision().getGeometry().getName());
            float collisionDistance = results.getClosestCollision().getDistance();
            if (collisionDistance < closestDistance) {
                closestDistance = collisionDistance;
                closest = results;
            };
        }

        if (closest != null) {
            alive = false;
            return closest.getClosestCollision();
        }


//        CollisionResults collRes = new CollisionResults();
//        for (Collidable g : targets) {
//            g.collideWith(ray, collRes);
//            if (collRes.size() >0) {
//                System.out.println("-->"+collRes.getClosestCollision().getGeometry().getName());
//            }
//        }
//        if (collRes.size() > 0) {
//            alive = false;
//            for (CollisionResult res : collRes) {
//                System.out.println("----> hit name: "+res.getGeometry().getName()+" distance:"+res.getDistance());
//            }
//            
//            return collRes.getClosestCollision();
//        }

        return null;
    }

    public boolean isAlive() {
        return alive;
    }
}
