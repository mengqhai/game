/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.physics;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;

/**
 *
 * @author qinghai
 */
public class PhysicsTownMain extends SimpleApplication implements ActionListener {

    private Node sceneNode;
    private BulletAppState bulletAppState;
    private RigidBodyControl scenePhy;
    private Node playerNode;
    private BetterCharacterControl playerControl;
    private CameraNode camNode;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 1);
    private boolean rotateLeft = false, rotateRight = false, forward = false, back = false;
    private float speed = 8;
    Material brickMat, stoneMat, woodMat;
    private Node crateNode;
    RigidBodyControl cratePhy;
    private final float CRATE_LIFTING_TOP = 5;
    private final String MAPPING_LIFT = "LIFT";
    private final KeyTrigger E_TRIGGER = new KeyTrigger(KeyInput.KEY_E);
    private final MouseButtonTrigger RMB_TRIGGER = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    private AnalogListener pickListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            Vector3f playerLoc = playerNode.getWorldTranslation();
            float playerY = playerLoc.getY();
            Vector3f location = cam.getLocation().add(cam.getDirection().mult(10f));//cratePhy.getPhysicsLocation();
            location.setY(playerY + CRATE_LIFTING_TOP);
            cratePhy.setPhysicsLocation(location);
            cratePhy.setPhysicsRotation(cam.getRotation());
            cratePhy.setLinearVelocity(Vector3f.ZERO);
            cratePhy.setAngularVelocity(Vector3f.ZERO);
            
            
            // lift the crate with a force
//            if (cratePhy.getPhysicsLocation().getY() < CRATE_LIFTING_TOP) {
//                cratePhy.applyCentralForce(Vector3f.UNIT_Y.mult(90));
//            }
        }
    };

    private Node loadTown() {
        assetManager.registerLocator("town.zip", ZipLocator.class);
        return (Node) assetManager.loadModel("main.scene");
    }

    @Override
    public void simpleInitApp() {
        sceneNode = (Node) loadTown();
        sceneNode.setLocalTranslation(0, 0, 0);
        //sceneNode.scale(1.5f);
        rootNode.attachChild(sceneNode);

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
        initCam();
        initInput();

        dropCrate();
    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        scenePhy = new RigidBodyControl(0f);
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
        Vector3f size = new Vector3f(1, 1, 1);
        Box crateMesh = new Box(size.x, size.y, size.z);
        BoxCollisionShape cShap = new BoxCollisionShape(size);
        Geometry crateGeom = new Geometry("Crate", crateMesh);
        crateGeom.move(1, 5, 1);
        crateGeom.setMaterial(woodMat);
        cratePhy = new RigidBodyControl(cShap, 5);
        crateGeom.addControl(cratePhy);

        crateNode = new Node();
        crateNode.attachChild(crateGeom);
        rootNode.attachChild(crateNode);
        bulletAppState.getPhysicsSpace().add(cratePhy);
    }

    private void initPlayer() {
        playerNode = new Node("the player");
        playerNode.setLocalTranslation(new Vector3f(0, 5, 0));
        rootNode.attachChild(playerNode);

        playerControl = new BetterCharacterControl(1.5f, 4, 40f);
        playerControl.setJumpForce(new Vector3f(0, 300, 0));
        playerControl.setGravity(new Vector3f(0, -10, 0));
        playerNode.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    private void initCam() {
        camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0, 4, -6);
        Quaternion quat = new Quaternion();
        quat.lookAt(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
        camNode.setLocalRotation(quat);
        playerNode.attachChild(camNode);
        camNode.setEnabled(true);
        flyCam.setEnabled(false);
    }

    private void initInput() {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addMapping(MAPPING_LIFT, E_TRIGGER, RMB_TRIGGER);

        inputManager.addListener(this, "Left", "Right");
        inputManager.addListener(this, "Forward", "Back", "Jump");
        inputManager.addListener(pickListener, MAPPING_LIFT);
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector3f modelForwardDir = playerNode.getWorldRotation().mult(Vector3f.UNIT_Z);
        walkDirection.set(0, 0, 0);
        if (forward) {
            walkDirection.addLocal(modelForwardDir.mult(speed));
        } else if (back) {
            walkDirection.addLocal(modelForwardDir.mult(speed).negate());
        }

        playerControl.setWalkDirection(walkDirection);
        if (rotateLeft) {
            Quaternion rotateL = new Quaternion().fromAngleAxis(FastMath.PI * tpf, Vector3f.UNIT_Y);
            // multiplies this quaternion by a parameter vector. The
            // result is stored in the supplied vector
            rotateL.multLocal(viewDirection);
        } else if (rotateRight) {
            Quaternion rotateR = new Quaternion().fromAngleAxis(-FastMath.PI * tpf, Vector3f.UNIT_Y);
            rotateR.multLocal(viewDirection);
        }
        playerControl.setViewDirection(viewDirection);
    }

    public static void main(String[] args) {
        PhysicsTownMain app = new PhysicsTownMain();
        app.start();
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Left")) {
            rotateLeft = isPressed;
        } else if (name.equals("Right")) {
            rotateRight = isPressed;
        } else if (name.equals("Forward")) {
            forward = isPressed;
        } else if (name.equals("Back")) {
            back = isPressed;
        } else if (name.equals("Jump")) {
            playerControl.jump();
        }
    }
}
