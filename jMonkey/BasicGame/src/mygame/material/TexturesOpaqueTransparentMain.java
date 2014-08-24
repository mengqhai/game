package mygame.material;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class TexturesOpaqueTransparentMain extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        TexturesOpaqueTransparentMain app = new TexturesOpaqueTransparentMain();
        app.setSettings(settings);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/mucha-window.png"));

        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        geom.setQueueBucket(Bucket.Transparent);
        geom.setMaterial(mat);
        
        Sphere monkeySphere = new Sphere(16,16,1);
        Geometry monkey = new Geometry("Geom2", monkeySphere);

        Material monkeyMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        monkeyMat.setTexture("DiffuseMap", assetManager.loadTexture("Interface/Images/Monkey.png"));
        monkeyMat.setColor("Diffuse", ColorRGBA.Blue);
        monkeyMat.setBoolean("UseMaterialColors", true);
//        mat2.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        monkeyMat.getAdditionalRenderState().setAlphaTest(true);
        monkeyMat.getAdditionalRenderState().setAlphaFallOff(0.5f);
        monkey.setQueueBucket(Bucket.Transparent);
        monkey.setMaterial(monkeyMat);
        monkey.setLocalTranslation(2, 1, 1);
        
        Sphere sphereMesh = new Sphere(32, 32, 1f);
        Geometry sphere = new Geometry("Box2", sphereMesh);
        Material sphereMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        sphereMat.setColor("Color", ColorRGBA.Blue);
        sphere.setMaterial(sphereMat);
        sphere.setLocalTranslation(2, 3, 4);



        rootNode.attachChild(geom);
        rootNode.attachChild(monkey);
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
