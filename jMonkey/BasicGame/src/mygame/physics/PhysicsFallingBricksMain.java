/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.physics;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class PhysicsFallingBricksMain extends SimpleApplication {

    private static final String SHOOT = "shoot";
    private static final float BRICK_LENGTH = 0.4f;
    private static final float BRICK_WIDTH = 0.3f;
    private static final float BRICK_HEIGHT = 0.25f;
    private static final float WALL_WIDTH_BRICK_COUNT = 12;
    private static final float WALL_HEIGHT_BRICK_COUNT = 6;
    private BulletAppState bulletAppState;
    private ActionListener shootListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                shootCannonBall();
            }
        }
    };
    
    /**
     * Matrials for brick, stone, wood
     */
    Material brickMat, stoneMat, woodMat;
    /**
     * Meshes
     */
    private static final Sphere ballMesh;
    private static final Box floorMesh;
    private static final Box brickMesh;
    private static Node wallNode;
    private RigidBodyControl floorPhy;

    static {
        floorMesh = new Box(10f, 0.5f, 5f);
        brickMesh = new Box(BRICK_LENGTH, BRICK_HEIGHT, BRICK_WIDTH);
        ballMesh = new Sphere(32, 32, 0.25f, true, false);
        ballMesh.setTextureMode(Sphere.TextureMode.Projected);
        floorMesh.scaleTextureCoordinates(new Vector2f(4, 4));
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        settings.setSettingsDialogImage(null);
        PhysicsFallingBricksMain app = new PhysicsFallingBricksMain();
        app.setSettings(settings);
        app.start();
    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(0, -2.5f, -1f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }

    private void initMaterial() {
        brickMat = assetManager.loadMaterial("Materials/brick.j3m");
        stoneMat = assetManager.loadMaterial("Materials/stone.j3m");
        woodMat = assetManager.loadMaterial("Materials/wood.j3m");
    }

    private void initFloor() {
        Geometry floorGeo = new Geometry("Floor", floorMesh);
        floorGeo.setMaterial(woodMat);
        floorGeo.move(0f, -BRICK_HEIGHT * 2f, 0);
        rootNode.attachChild(floorGeo);
        // PhysicsControl with zero mess
        // and default BoxCollisionShap:
        floorPhy = new RigidBodyControl(0f);
        floorGeo.addControl(floorPhy);
        bulletAppState.getPhysicsSpace().add(floorPhy);
    }

    private Geometry makeBrick(Vector3f position) {
        Geometry brickGeo = new Geometry("brick", brickMesh);
//        Material brickMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        brickMat.setColor("Color", ColorRGBA.Blue);
        brickGeo.setMaterial(brickMat);
        brickGeo.move(position);
        RigidBodyControl brickPhy = new RigidBodyControl(5f);
        brickGeo.addControl(brickPhy);
        bulletAppState.getPhysicsSpace().add(brickPhy);
        return brickGeo;
    }

    private void initWall() {
        wallNode = new Node("Wall");
        float offsetH = BRICK_HEIGHT / 3f;
        float offsetV = 0;
        for (int j = 0; j < WALL_HEIGHT_BRICK_COUNT; j++) {
            for (int i = 0; i < WALL_WIDTH_BRICK_COUNT; i++) {
                Vector3f brickPos = new Vector3f(offsetH + BRICK_LENGTH * 2.1f * i
                        - (BRICK_LENGTH * WALL_WIDTH_BRICK_COUNT), offsetV + BRICK_HEIGHT, 0f);
                wallNode.attachChild(makeBrick(brickPos));
            }
            offsetH = -offsetH;
            offsetV += 2 * BRICK_HEIGHT;
        }
        rootNode.attachChild(wallNode);
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        initMaterial();
        initLight();
        initFloor();
        initWall();
        initInput();
    }
    
    private void initInput() {
        inputManager.addMapping(SHOOT, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(SHOOT, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shootListener, SHOOT);
    }
    
    private void shootCannonBall() {
        Geometry ballGeo = new Geometry("Cannon ball", ballMesh);
        ballGeo.setMaterial(stoneMat);
        ballGeo.setLocalTranslation(cam.getLocation());
        rootNode.attachChild(ballGeo);
        RigidBodyControl ballPhy = new RigidBodyControl(5f);
        ballGeo.addControl(ballPhy);
        ballPhy.setCcdSweptSphereRadius(0.1f);
        ballPhy.setCcdMotionThreshold(0.001f);
        ballPhy.setLinearVelocity(cam.getDirection().mult(50));
        
        bulletAppState.getPhysicsSpace().add(ballPhy);
    }
}
