/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.effect;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class BloomGlowMain extends SimpleApplication {

    private DirectionalLight sun;
    private Node townNode;

    private Node loadTown() {
        assetManager.registerLocator("town.zip", ZipLocator.class);
        return (Node) assetManager.loadModel("main.scene");
    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        sun = new DirectionalLight();
        sun.setDirection((new Vector3f(0.3f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }

    private void initBloomGlow() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        BloomFilter bloom = new BloomFilter();
        fpp.addFilter(bloom);
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        initLight();

        initBloomGlow();
        
        townNode = loadTown();
        rootNode.attachChild(townNode);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        townNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        BloomGlowMain app = new BloomGlowMain();
        app.setSettings(settings);
        app.start();
    }
}
