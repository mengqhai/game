/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook.fps;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qinghai
 */
public class FPSCharacterControlPlayMain extends SimpleApplication{

    private Node sceneNode;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private Node playerNode;
    private FPSCharacterControl playerControl;
    Material brickMat, stoneMat, woodMat;
    private Node crateNode = new Node("Crate Node");;
    RigidBodyControl cratePhy;
    Map<String, RigidBodyControl> cratePhyMap = new HashMap<String, RigidBodyControl>();
    
    
    private final String ELEVATOR = "ELEVATOR";
    private static final float TOPFLOOR = 20f;
    private boolean downward;

    private Geometry platformGeo;

    private Node loadScene() {
        assetManager.registerLocator("town.zip", ZipLocator.class);
        Node town = (Node) assetManager.loadModel("main.scene");
        rootNode.attachChild(town);
        return (Node) assetManager.loadModel("main.scene");
//        Node terrainGeo = (Node)assetManager.loadModel("Scenes/myTerrain.j3o");
//        rootNode.attachChild(terrainGeo);
//        
//        return (Node)terrainGeo.getChild("terrain-myTerrain");
    }

    @Override
    public void simpleInitApp() {
        sceneNode = (Node) loadScene();
        sceneNode.setLocalTranslation(0, 0, 0);
        //sceneNode.scale(1.5f);
        //rootNode.attachChild(sceneNode);
        
        

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(1.4f, -1.4f, -1.4f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        initMaterial();
        initPhysics();
        initPlayer();
        initInput();
        initPlatform();
        dropCrate();
    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(sceneNode);
        scenePhy = new RigidBodyControl(sceneShape, 0f);
        sceneNode.addControl(scenePhy);
        bulletAppState.getPhysicsSpace().add(sceneNode);
        bulletAppState.setDebugEnabled(true);
    }

    private void initMaterial() {
        brickMat = assetManager.loadMaterial("Materials/brick.j3m");
        stoneMat = assetManager.loadMaterial("Materials/stone.j3m");
        woodMat = assetManager.loadMaterial("Materials/wood.j3m");
    }

    private void dropCrate() {
        String name= "Crate_"+cratePhyMap.size();
        Vector3f size = new Vector3f(1, 1, 1);
        Box crateMesh = new Box(size.x, size.y, size.z);
        BoxCollisionShape cShap = new BoxCollisionShape(size);
        Geometry crateGeom = new Geometry(name, crateMesh);
        crateGeom.setLocalTranslation(9, 6, 4);
        crateGeom.move(9, 6, 4);
        crateGeom.setMaterial(woodMat);
        cratePhy = new RigidBodyControl(cShap, 5);
        crateGeom.addControl(cratePhy);
        
        cratePhyMap.put(name, cratePhy);
        crateNode.attachChild(crateGeom);
        rootNode.attachChild(crateNode);
        bulletAppState.getPhysicsSpace().add(cratePhy);
    }

    private void initPlayer() {
        playerNode = new Node("the player");
        playerNode.setLocalTranslation(new Vector3f(3, 5, 2));
        rootNode.attachChild(playerNode);

        playerControl = new FPSCharacterControl(1.2f, 3f, 40f);
        playerControl.setCamera(cam);
        playerNode.addControl(playerControl);
        playerControl.setJumpForce(new Vector3f(0, 300, 0));
        playerControl.setGravity(new Vector3f(0, -10, 0));
        bulletAppState.getPhysicsSpace().add(playerControl);
        
        
        FPSInputAppState iState = new FPSInputAppState();
        iState.setCharacter(playerControl);
        iState.getTargets().add(crateNode);
        iState.getTargets().add(sceneNode);
        //iState.getTargets().add(rootNode);
        stateManager.attach(iState);
    }
    
    private void initPlatform() {
        Box platformMesh = new Box(2f, 0.5f, 5f);
        platformGeo = new Geometry(ELEVATOR, platformMesh);
        platformGeo.setMaterial(stoneMat);
        platformGeo.move(80, 0, -10);
        rootNode.attachChild(platformGeo);
        RigidBodyControl platformPhy = new RigidBodyControl(100.0f);
        platformGeo.addControl(platformPhy);
        platformPhy.setFriction(800f);
        platformPhy.setKinematic(true);
        bulletAppState.getPhysicsSpace().add(platformPhy);
    }

    private void initInput() {
    }

    @Override
    public void simpleUpdate(float tpf) {
        movePlatform(tpf);
    }
    
    private void movePlatformUp(float tpf) {
        float platformHeight = platformGeo.getLocalTranslation().getY();
        if (platformHeight < TOPFLOOR) {
            platformGeo.move(0, tpf, 0);
            downward = false;
        } else {
            downward = true;
        }
    }
    private void movePlatformDown(float tpf) {
        float platformHeight = platformGeo.getLocalTranslation().getY();
        if (platformHeight > .5f) {
            platformGeo.move(0, -tpf * 4, 0);
            downward = true;
        } else {
            downward = false;
        }

    }
    
    private void movePlatform(float tpf) {
        if (!downward) {
            movePlatformUp(tpf);
        }
        if (downward) {
            movePlatformDown(tpf);
        }
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage(null);
        FPSCharacterControlPlayMain app = new FPSCharacterControlPlayMain();
        app.setSettings(settings);
        app.start();
    }

}
