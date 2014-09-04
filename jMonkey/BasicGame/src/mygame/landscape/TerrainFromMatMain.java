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
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qinghai
 */
public class TerrainFromMatMain extends SimpleApplication {

    private DirectionalLight sun;
    private Node terrainNode;
    
    private AbstractHeightMap loadImageHeightMap() {
        Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/heightmap.png");
        AbstractHeightMap heightMap = new ImageBasedHeightMap(heightMapImage.getImage(), 0.5f);
        return heightMap;
    }
    
    private AbstractHeightMap loadRandomHeightMap() {
        try {
            // 500 -- number of hills
            return new HillHeightMap(1025, 500, 50, 100, 3l);
        } catch (Exception ex) {
            Logger.getLogger(TerrainFromMatMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Node loadTerrain() {
        Material terrainMat = assetManager.loadMaterial("Scenes/terrain.j3m");
        
        AbstractHeightMap heightMap = loadImageHeightMap();//loadRandomHeightMap();
        
        
        heightMap.load();
        TerrainQuad terrain = new TerrainQuad("terrain", 65, 513, heightMap.getHeightMap());
        terrain.setMaterial(terrainMat);
        
        TerrainLodControl lodControl = new TerrainLodControl(terrain, cam);
        terrain.addControl(lodControl);
        return terrain;
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
        TerrainFromMatMain app = new TerrainFromMatMain();
        app.setSettings(settings);
        app.start();
    }
}
