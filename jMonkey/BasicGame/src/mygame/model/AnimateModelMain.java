package mygame.model;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class AnimateModelMain extends SimpleApplication {

    private static final String MODEL_PATH_J3O = "Models/Jaime/Jaime.j3o";
    private static final String MODEL_PATH_J3O1 = "Textures/Jaime/JaimeGeom.mesh.j3o";
    
    private static final Trigger TRIGGER_SPACE = new KeyTrigger(KeyInput.KEY_SPACE);
    private static final String MAPPING_WALK = "walk";
    
    private JaimeControl jaime;
    
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            jaime.toggleWalk();
        }
    };
    
    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            jaime.move(0, 0, tpf);
        }
    };

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My model application");
        AnimateModelMain app = new AnimateModelMain();
        app.setSettings(settings);
        app.start();
    }
    
    private void regListeners() {
        inputManager.addMapping(MAPPING_WALK, TRIGGER_SPACE);
        inputManager.addListener(actionListener, MAPPING_WALK);
        inputManager.addListener(analogListener, MAPPING_WALK);
    }

    @Override
    public void simpleInitApp() {
        Spatial myModel = assetManager.loadModel(MODEL_PATH_J3O1);
        jaime = new JaimeControl();
        myModel.addControl(jaime);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        //myModel.setMaterial(mat);
        rootNode.attachChild(myModel);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(Vector3f.UNIT_XYZ.negate());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        
        regListeners();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
