package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        Main app = new Main();
        app.setSettings(settings);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        
        Box b2 = new Box(1, 1, 1);
        Geometry geom2 = new Geometry("Box2", b2);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        geom2.setMaterial(mat2);
        geom2.setLocalTranslation(2, 3, 4);
        geom2.setLocalScale(0.5f);
//        float r = FastMath.DEG_TO_RAD * 45f;
//        geom2.rotate(r, 0, 0);
        Quaternion roll045 = new Quaternion().fromAngleAxis(45f * FastMath.DEG_TO_RAD, Vector3f.UNIT_X);
        geom2.rotate(roll045);
        

        rootNode.attachChild(geom);
        rootNode.attachChild(geom2);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
