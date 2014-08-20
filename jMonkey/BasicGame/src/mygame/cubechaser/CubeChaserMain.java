package mygame.cubechaser;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import java.util.List;
import mygame.Utils;

/**
 * test
 * @author normenhansen
 */
public class CubeChaserMain extends SimpleApplication {
    
    private Geometry scaredCube;
    
    private Ray ray = new Ray();

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Cube chaser");
        CubeChaserMain app = new CubeChaserMain();
        app.setSettings(settings);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        List<Geometry> boxes = Utils.createBoxGeom1s(assetManager, 40);
        for (Geometry box: boxes) {
            rootNode.attachChild(box);
        }
        scaredCube = Utils.createBoxGeom1(assetManager, "Scared Cube", Vector3f.ZERO, ColorRGBA.White);
        rootNode.attachChild(scaredCube);
        flyCam.setMoveSpeed(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // chase the scared cube
//        if (cam.getLocation().distance(scaredCube.getLocalTranslation())<10) {
//            scaredCube.move(cam.getDirection());
//        }
        
        // chase all the cubes
        CollisionResults results = new CollisionResults();
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        rootNode.collideWith(ray, results);
        if (results.size()>0) {
            Geometry target = results.getClosestCollision().getGeometry();
            if (cam.getLocation().distance(target.getLocalTranslation())<10) {
                target.move(cam.getDirection());
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
