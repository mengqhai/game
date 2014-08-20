/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.List;
import mygame.Utils;

/**
 *
 * @author liuli
 */
public class CubeChaserState extends AbstractAppState {

    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AssetManager assetManager;
    private Ray ray = new Ray();
    private static Box mesh = new Box(Vector3f.ZERO, 1, 1, 1);

    public CubeChaserState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        List<Geometry> boxes = Utils.createBoxGeom1s(assetManager, 40);
        for (Geometry box : boxes) {
            rootNode.attachChild(box);
        }

        super.initialize(stateManager, app);
    }

    @Override
    public void update(float tpf) {
        CollisionResults results = new CollisionResults();
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            Geometry target = results.getClosestCollision().getGeometry();
            if (cam.getLocation().distance(target.getLocalTranslation()) < 10) {
                float rotateRad = tpf * 10;
                target.rotate(rotateRad, rotateRad, rotateRad);
                target.move(cam.getDirection());
            }
        }
    }
}
