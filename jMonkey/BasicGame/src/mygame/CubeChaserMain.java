package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import java.util.List;

/**
 * test
 * @author normenhansen
 */
public class CubeChaserMain extends SimpleApplication {
    
    private Geometry scaredCube;

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
        if (cam.getLocation().distance(scaredCube.getLocalTranslation())<10) {
            scaredCube.move(cam.getDirection());
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
