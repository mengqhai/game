/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.control;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author liuli
 */
public class CubeChaserControl extends AbstractControl{
    
    private Ray ray = new Ray();
    private final Camera cam;
    private final Node rootNode;

    public CubeChaserControl(Camera cam, Node rootNode) {
        this.cam = cam;
        this.rootNode = rootNode;
    }
    
    

    @Override
    protected void controlUpdate(float tpf) {
        CollisionResults results = new CollisionResults();
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        rootNode.collideWith(ray, results);
        if (results.size()>0) {
            Geometry target = results.getClosestCollision().getGeometry();
            if (target.equals(spatial)) {
                if (cam.getLocation().distance(spatial.getLocalTranslation())<10) {
                    spatial.move(cam.getDirection());
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
