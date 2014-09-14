/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuli
 */
public class FPSInputAppState extends AbstractAppState implements AnalogListener, ActionListener {

    private InputManager inputManager;
    private FPSCharacterControl character;
    private Application app;
    private List<Geometry> targets = new ArrayList<Geometry>();

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

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
        this.inputManager = app.getInputManager();
        inputManager.setCursorVisible(false);
        addInputMappings();
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
        Ray r = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
        CollisionResults collRes = new CollisionResults();
        for (Geometry g: targets) {
            g.collideWith(r, collRes);
        }
        if (collRes.size()>0) {
            System.out.println("hit "+collRes.getClosestCollision().getContactPoint());
        }
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
    
    public List<Geometry> getTargets() {
        return targets;
    }
}
