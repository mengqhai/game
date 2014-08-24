package mygame.material;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class HoverTankGlowMain extends SimpleApplication {
    
    private final static String HOVER_TANK_MODEL = "Models/HoverTank/Tank.mesh.j3o";

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        HoverTankGlowMain app = new HoverTankGlowMain();
        app.setSettings(settings);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        // Diffuse Map, Normal Map, Glow Map, Specular Map and Shininess are all set in the model by SDK
        Spatial tank = assetManager.loadModel(HOVER_TANK_MODEL);
        rootNode.attachChild(tank);

        // To see the effect of Glow Map:
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.SceneAndObjects);
        fpp.addFilter(bloom);
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
