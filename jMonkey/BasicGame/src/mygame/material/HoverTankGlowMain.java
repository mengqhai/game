package mygame.material;

import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture.WrapMode;
import java.util.HashMap;

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

    private void initFloor() {
        Box floorMesh = new Box(new Vector3f(-20,-2,-20), new Vector3f(20,-3,20));
        floorMesh.scaleTextureCoordinates(new Vector2f(8, 8 ));
        Geometry floorGeo = new Geometry("floor", floorMesh);
        Material floorMat = assetManager.loadMaterial("Textures/BrickWall/brickwall.j3m");//assetManager.loadMaterial("Textures/BrickWall/BrickWall.j3m")
//        Material floorMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//        floorMat.setTexture("DiffuseMap", assetManager.loadTexture(
//                "Textures/BrickWall/BrickWall_diffuse.jpg"));
//        floorMat.setTexture("NormalMap", assetManager.loadTexture(
//                "Textures/BrickWall/BrickWall_normal.jpg"));
//        floorMat.getTextureParam("NormalMap").getTextureValue().
//                setWrap(WrapMode.Repeat);
//        floorMat.getTextureParam("DiffuseMap").getTextureValue().
//                setWrap(WrapMode.Repeat);
        floorGeo.setMaterial(floorMat);
        rootNode.attachChild(floorGeo);

    }

    @Override
    public void simpleInitApp() {
        // Diffuse Map, Normal Map, Glow Map, Specular Map and Shininess are all set in the model by SDK
        Spatial tank = assetManager.loadModel(HOVER_TANK_MODEL);
        rootNode.attachChild(tank);

        // To see the effect of Glow Map:
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        viewPort.addProcessor(fpp);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        fpp.addFilter(bloom);
        initLight();
        initFloor();
    }

    private void initLight() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -2));
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

//        AmbientLight ambient = new AmbientLight();
//        ambient.setColor(ColorRGBA.White);
//        rootNode.addLight(ambient);
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
