/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.physics;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author qinghai
 */
public class PhysicsKinematicMain extends SimpleApplication {

    private BulletAppState bulletAppState;
    /**
     * Matrials for brick, stone, wood
     */
    Material brickMat, stoneMat, woodMat;
    private final String ELEVATOR = "elevator";
    private final String BALL = "ball";
    private static final float TOPFLOOR = 6f;
    private volatile boolean downward = false;
    private volatile boolean isBallOnPlatform = false;
    private Geometry platformGeo;
    private RigidBodyControl ballPhy;
    private PhysicsCollisionListener collisionListener = new PhysicsCollisionListener() {
        public void collision(PhysicsCollisionEvent event) {
            String nameA = event.getNodeA().getName();
            String nameB = event.getNodeB().getName();
            if ((BALL.equals(nameA) && ELEVATOR.equals(nameB))
                    || (BALL.equals(nameB) && ELEVATOR.equals(nameA))) {
                isBallOnPlatform = true;
            } else if (BALL.equals(nameA) || BALL.equals(nameB)) {
                isBallOnPlatform = false;
            }
        }
    };
    
    private PhysicsTickListener tickListener = new PhysicsTickListener() {

        public void prePhysicsTick(PhysicsSpace space, float tpf) {
            if (isBallOnPlatform) {
                ballPhy.applyImpulse(new Vector3f(.3f, .1f, 0), Vector3f.ZERO);
            }
        }

        public void physicsTick(PhysicsSpace space, float tpf) {
        }
        
    };

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        settings.setSettingsDialogImage(null);
        PhysicsKinematicMain app = new PhysicsKinematicMain();
        app.setSettings(settings);
        app.start();
    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-3, -8f, -5f)).normalizeLocal());
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

    private void initStatic() {
        Node sceneNode = new Node("Scene");
        Box floorMesh = new Box(10f, 0.5f, 10f);
        Geometry floorGeo = new Geometry("Floor", floorMesh);
        floorGeo.setMaterial(stoneMat);
        floorGeo.move(0, -.5f, 0);
        sceneNode.attachChild(floorGeo);

        Box slopeMesh = new Box(10f, 0.1f, 5f);
        TangentBinormalGenerator.generate(slopeMesh);
        Geometry slopeGeo = new Geometry("Slope", slopeMesh);
        slopeGeo.setMaterial(brickMat);
        slopeGeo.rotate(0, 0, FastMath.DEG_TO_RAD * 50);
        slopeGeo.move(5f, 5f, 0);
        sceneNode.attachChild(slopeGeo);

        Box wallMesh = new Box(5f, 0.4f, 5f);
        TangentBinormalGenerator.generate(wallMesh);
        Geometry wallGeo = new Geometry("Wall", wallMesh);
        wallGeo.setMaterial(brickMat);
        wallGeo.rotate(0, 0, FastMath.DEG_TO_RAD * 90);
        wallGeo.move(-3.5f, 2, 0);
        sceneNode.attachChild(wallGeo);

        RigidBodyControl scenePhy = new RigidBodyControl(0);
        sceneNode.addControl(scenePhy);
        scenePhy.setFriction(800);
        bulletAppState.getPhysicsSpace().add(scenePhy);
        rootNode.attachChild(sceneNode);
    }

    private void initPlatform() {
        Box platformMesh = new Box(2f, 0.5f, 5f);
        platformGeo = new Geometry(ELEVATOR, platformMesh);
        platformGeo.setMaterial(woodMat);
        platformGeo.move(-1, 10, 0);
        rootNode.attachChild(platformGeo);
        RigidBodyControl platformPhy = new RigidBodyControl(100.0f);
        platformGeo.addControl(platformPhy);
        platformPhy.setFriction(800f);
        platformPhy.setKinematic(true);
        bulletAppState.getPhysicsSpace().add(platformPhy);
    }

    public void dropBall() {
        Sphere ballMesh = new Sphere(32, 32, .75f, true, false);
        ballMesh.setTextureMode(Sphere.TextureMode.Projected);
        TangentBinormalGenerator.generate(ballMesh);
        Geometry ballGeo = new Geometry(BALL, ballMesh);
        ballGeo.setMaterial(stoneMat);
        rootNode.attachChild(ballGeo);

        ballPhy = new RigidBodyControl(5f);
        ballGeo.addControl(ballPhy);
        bulletAppState.getPhysicsSpace().add(ballPhy);
        ballPhy.setPhysicsLocation(new Vector3f(0, 10, 0));
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        initMaterial();
        initLight();
        initStatic();
        initPlatform();
        dropBall();
        
        flyCam.setMoveSpeed(10);
        
        
        bulletAppState.getPhysicsSpace().addCollisionListener(collisionListener);
        bulletAppState.getPhysicsSpace().addTickListener(tickListener);
    }

    @Override
    public void simpleUpdate(float tpf) {
//        float platformHeight = platformGeo.getLocalTranslation().getY();
        if (isBallOnPlatform) {
            movePlatform(tpf);
        } else {
            movePlatformDown(tpf);
        }
        
        
//        if (isBallOnPlatform && platformHeight < TOPFLOOR) {
//            platformGeo.move(0, tpf, 0);
//        }
//        if (isBallOnPlatform && platformHeight >= TOPFLOOR) {
//            isPlatformOnTop = true;
//        }
//        if (!isBallOnPlatform && platformHeight > 0.5f) {
//            isPlatformOnTop = false;
//            platformGeo.move(0, -tpf * 4, 0);
//        }
    }
    
    
    private void movePlatformUp(float tpf) {
        float platformHeight = platformGeo.getLocalTranslation().getY();
        if (platformHeight < TOPFLOOR) {
            platformGeo.move(0, tpf, 0);
            downward = false;
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
}
