/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.collision.Collidable;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuli
 */
public class FPSInputAppState extends AbstractAppState implements AnalogListener, ActionListener {
    
    private final String CROSSHAIR_PATH = "Interface/Images/crosshair-sm.png";
    private final String SPARK_MODEL_PATH = "Effects/Spark.j3o";

    private InputManager inputManager;
    private FPSCharacterControl character;
    private SimpleApplication app;
    private List<Collidable> targets = new ArrayList<Collidable>();
    private AbstractBulletManager bManager;
    
    private Picture crossHair = new Picture("CrossHair");

    private void addInputMappings() {
        inputManager.addMapping(InputMapping.RotateLeft.name(), new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(InputMapping.RotateRight.name(), new MouseAxisTrigger(MouseInput.AXIS_X, false), new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(InputMapping.LookUp.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, false), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(InputMapping.LookDown.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, true), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(InputMapping.Jump.name(), new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(InputMapping.Duck.name(), new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputManager.addMapping(InputMapping.StrafeLeft.name(), new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(InputMapping.StrafeRight.name(), new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(InputMapping.MoveForward.name(), new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(InputMapping.MoveBackward.name(), new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(InputMapping.Fire.name(), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        for (InputMapping i : InputMapping.values()) {
            inputManager.addListener(this, i.name());
        }
    }
    
    private AbstractBulletManager produceDefaultBulletManager() {
        BulletManager manager = new BulletManager(app.getCamera());
        manager.setTargets(targets);
        return manager;
    }
    
    private AbstractBulletManager produceRayCastingBulletManager() {
        RayCastingBulletManager manager = new RayCastingBulletManager(app.getCamera());
        manager.setTargets(targets);
        return manager;
    }
    
    private AbstractBulletManager producePhysicsBulletManager() {
        PhyBulletManager manager = new PhyBulletManager(app.getCamera(), app.getStateManager().getState(BulletAppState.class).getPhysicsSpace());
        return manager;
    }
    
    protected AbstractBulletManager createBulletManager() {
        AbstractBulletManager manager = producePhysicsBulletManager();//producePhysicsBulletManager();//produceRayCastingBulletManager(); //produceDefaultBulletManager();
        manager.setHitSpark(app.getAssetManager().loadModel(SPARK_MODEL_PATH));
        Node bulletNode = new Node("Bullet Node");
        app.getRootNode().attachChild(bulletNode);
        bulletNode.addControl(manager);
        return manager;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication)app;
        this.inputManager = app.getInputManager();
        inputManager.setCursorVisible(false);
        addInputMappings();
        bManager = createBulletManager();
        Node rootNode = this.app.getRootNode();
         /* A colored lit cube. Needs light source! */ 
        Box boxMesh = new Box(0.1f,0.1f, 0.1f); 
        Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
        Material boxMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
        boxMat.setBoolean("UseMaterialColors", true); 
        boxMat.setColor("Ambient", ColorRGBA.Green); 
        boxMat.setColor("Diffuse", ColorRGBA.Green); 
        boxGeo.setMaterial(boxMat); 
        boxGeo.setLocalTranslation(1, 1, 1);
        rootNode.attachChild(boxGeo);
        bManager.setHitMark(boxGeo);
        
        
        crossHair.setImage(app.getAssetManager(), CROSSHAIR_PATH, true);
        crossHair.setWidth(30);
        crossHair.setHeight(30);
        int screenWidth = this.app.getContext().getSettings().getWidth();
        int screenHeight = this.app.getContext().getSettings().getHeight();
        crossHair.move(screenWidth/2.0f - 15, screenHeight/2.0f - 15 , 0);

        this.app.getGuiNode().attachChild(crossHair);
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void cleanup() {
        super.cleanup();
        for (InputMapping i : InputMapping.values()) {
            if (inputManager.hasMapping(i.name())) {
                inputManager.deleteMapping(i.name());
            }
        }
        inputManager.removeListener(this);
    }

    public void setCharacter(FPSCharacterControl character) {
        this.character = character;
    }
    
    public void fire() {

//        Ray r = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
//        CollisionResults collRes = new CollisionResults();
//        for (Collidable g: targets) {
//            g.collideWith(r, collRes);
//        }
//        if (collRes.size()>0) {
//            System.out.println("hit "+collRes.getClosestCollision().getGeometry().getName());
//        }

        bManager.fire();
        character.onFire();
    }

    public void onAnalog(String name, float value, float tpf) {
        if (character != null) {
            character.onAnalog(name, value, tpf);
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (character == null) {
            return;
        }
        if (name.equals(InputMapping.Fire.name())) {
            if (isPressed && character.getCooldown()<=0f) {
                fire();
            }
        } else {
            character.onAction(name, isPressed, tpf);
        }
    }
    
    public List<Collidable> getTargets() {
        return targets;
    }
}
