/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.effect;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class DepthOfFieldBlurMain extends SimpleApplication {

    private DirectionalLight sun;
    private Node townNode;
    private DepthOfFieldFilter dofFilter;

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
    
    private void initDepthOfField() {
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        dofFilter = new DepthOfFieldFilter();
        fpp.addFilter(dofFilter);
   }

    @Override
    public void simpleUpdate(float tpf) {
        // We need to determine what the player is looking at in the scene, so we
        // can update the focus distance.
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        CollisionResults results = new CollisionResults();
        int numCollisions = townNode.collideWith(ray, results);
        if (numCollisions>0) {
            CollisionResult hit = results.getClosestCollision();
            dofFilter.setFocusDistance(hit.getDistance() / 10.0f);
        }
    }
    
    

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        initLight();

        initDepthOfField();
        
        townNode = loadTown();
        rootNode.attachChild(townNode);
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        DepthOfFieldBlurMain app = new DepthOfFieldBlurMain();
        app.setSettings(settings);
        app.start();
    }
}
