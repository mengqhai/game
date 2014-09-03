/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.landscape;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

/**
 *
 * @author qinghai
 */
public class TeerrainFromSceneMain extends SimpleApplication {

    private DirectionalLight sun;
    private Node terrainNode;

    private Node loadTerrain() {
        Node terrainGeo = (Node)assetManager.loadModel("Scenes/myTerrain.j3o");
        return terrainGeo;
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
//        AmbientLight ambient = new AmbientLight();
//        ambient.setColor(ColorRGBA.White);
//        rootNode.addLight(ambient);
    }
    
    private void initJaime() {
        Node jaime = (Node)assetManager.loadModel("Models/Jaime/Jaime.j3o");
        jaime.setLocalTranslation(1, 0, 1);
        rootNode.attachChild(jaime);
    }

    private void initCartoon() {
        /* this shadow needs a directional light */
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);

        CartoonEdgeFilter toon = new CartoonEdgeFilter();
        fpp.addFilter(toon);

    }

    private void makeToonish(Spatial spatial) {
        if (spatial instanceof Node) {
            Node n = (Node)spatial;
            for (Spatial child:n.getChildren()) {
                makeToonish(child);
            }
        } else if (spatial instanceof Geometry) {
            Geometry g = (Geometry)spatial;
            Material m = g.getMaterial();
            //if (m.getMaterialDef().getName().equals("Phong Lighting")) {
                Texture t = assetManager.loadTexture("Textures/ColorRamp/toon.png");
                m.setTexture("ColorRamp", t);
                m.setBoolean("VertexLighting", true);
                m.setBoolean("UseMaterialColors", true);
                m.setColor("Specular", ColorRGBA.Black);
                m.setColor("Diffuse", ColorRGBA.White);
                g.setMaterial(m);
            //}
        }
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        initLight();


        //initCartoon();
        terrainNode = loadTerrain();
        rootNode.attachChild(terrainNode);
        initJaime();
        //makeToonish(rootNode);
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        TeerrainFromSceneMain app = new TeerrainFromSceneMain();
        app.setSettings(settings);
        app.start();
    }
}
