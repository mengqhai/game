package mygame.material;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class LightingMain extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        LightingMain app = new LightingMain();
        app.setSettings(settings);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);


        Sphere sphereMesh = new Sphere(32, 32, 1f);

        Geometry sphere = new Geometry("Box2", sphereMesh);
        Material shpereMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        shpereMat.setBoolean("UseMaterialColors", true);
        shpereMat.setColor("Diffuse", ColorRGBA.Red);
        shpereMat.setColor("Ambient", ColorRGBA.Gray);
        sphere.setMaterial(shpereMat);
        sphere.setLocalTranslation(2, 3, 4);
        sphere.setLocalScale(0.5f);


        rootNode.attachChild(geom);
        rootNode.attachChild(sphere);

        initLight();
    }
    
    private void initLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, 0, -2));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
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
