/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.landscape;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.renderer.queue.RenderQueue;
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
import com.jme3.util.SkyFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qinghai
 */
public class SunLightMain extends SimpleApplication {

    private DirectionalLight sun;
    private TerrainQuad terrain;
    private Vector3f lightDir = new Vector3f(-0.39f, -0.32f, -0.74f);
    private FilterPostProcessor fpp;
    private LightScatteringFilter sunLightFilter;

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
            Logger.getLogger(SunLightMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private TerrainQuad loadTerrain() {
        Material terrainMat = assetManager.loadMaterial("Scenes/terrain.j3m");

        AbstractHeightMap heightMap = loadRandomHeightMap(); //loadImageHeightMap();//;


        heightMap.load();
        TerrainQuad t = new TerrainQuad("terrain", 65, 513, heightMap.getHeightMap());
        t.setMaterial(terrainMat);

        TerrainLodControl lodControl = new TerrainLodControl(t, cam);
        t.addControl(lodControl);
        return t;
    }

    private Node loadTree(TerrainQuad t) {
        Node treeGeo = (Node) assetManager.loadModel("Models/Tree/Tree.mesh.j3o");
        treeGeo.scale(5);
        treeGeo.setQueueBucket(RenderQueue.Bucket.Translucent);
        Vector3f treeLoc = new Vector3f(-30, 0, -30);
        treeLoc.setY(t.getHeight(new Vector2f(treeLoc.x, treeLoc.y)));
        treeGeo.setLocalTranslation(treeLoc);
        return treeGeo;
    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        sun = new DirectionalLight();
        sun.setDirection(lightDir);
        sun.setColor(ColorRGBA.White.clone().multLocal(2));
        rootNode.addLight(sun);

        /**
         * A white ambient light source.
         */
//        AmbientLight ambient = new AmbientLight();
//        ambient.setColor(ColorRGBA.White);
//        rootNode.addLight(ambient);
        
        
        fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        
        sunLightFilter = new LightScatteringFilter(lightDir.mult(-3000));
        fpp.addFilter(sunLightFilter);
        
        cam.lookAtDirection(lightDir.negate(), Vector3f.UNIT_Y);
    }

    private void initJaime() {
        Node jaime = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
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
            Node n = (Node) spatial;
            for (Spatial child : n.getChildren()) {
                makeToonish(child);
            }
        } else if (spatial instanceof Geometry) {
            Geometry g = (Geometry) spatial;
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

    private Node loadSky() {
        Node mySky = (Node) assetManager.loadModel("Scenes/mySky.j3o");
        return mySky;
    }

    private Spatial loadSkyFromFactory() {
        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false);
        return sky;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(50);
        initLight();


        //initCartoon();
        terrain = loadTerrain();
        rootNode.attachChild(terrain);
        initJaime();
        Node tree = loadTree(terrain);
        rootNode.attachChild(tree);
        //makeToonish(rootNode);

        //rootNode.attachChild(loadSky());
        rootNode.attachChild(loadSkyFromFactory());
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        SunLightMain app = new SunLightMain();
        app.setSettings(settings);
        app.start();
    }
}
