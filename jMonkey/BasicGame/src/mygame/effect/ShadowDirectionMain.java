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
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class ShadowDirectionMain extends SimpleApplication {

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

    private void initEffectRenderer() {
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 1024, 2);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);
    }

    private void initEffectFilter() {
//        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, 1024, 2);
//        dlsf.setLight(sun);
//        dlsf.setEnabled(true);
//        
//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        fpp.addFilter(dlsf);
//        viewPort.addProcessor(fpp);



        /* this shadow needs a directional light */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, 1024, 2);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);

    }

    /**
     * Slow
     */
    private void initAmbientOcclusion() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.93f, 0.33f, 0.60f);
        fpp.addFilter(ssaoFilter);
    }
    
    private void initCartoonEdge() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        CartoonEdgeFilter cartoon = new CartoonEdgeFilter();
        fpp.addFilter(cartoon);
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        initLight();
        //initEffectRenderer();
        //initEffectFilter();
        //initAmbientOcclusion();
        initCartoonEdge();
        townNode = loadTown();
        rootNode.attachChild(townNode);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        townNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        ShadowDirectionMain app = new ShadowDirectionMain();
        app.setSettings(settings);
        app.start();
    }
}
