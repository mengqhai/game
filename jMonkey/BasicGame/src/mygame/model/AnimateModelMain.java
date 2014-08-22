package mygame.model;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * test
 * @author normenhansen
 */
public class AnimateModelMain extends SimpleApplication {
    
    private static final String MODEL_PATH_J3O = "Models/Jaime/Jaime.j3o";
     private static final String MODEL_PATH_J3O1 = "Textures/Jaime/JaimeGeom.mesh.j3o";
    
    private static final String ANI_IDLE = "Idle";
    private static final String ANI_WALK = "Walk";
    
    
    private Node player;
    
    private AnimControl control;
    private AnimChannel channel;
    

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My model application");
        AnimateModelMain app = new AnimateModelMain();
        app.setSettings(settings);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        Spatial myModel = assetManager.loadModel(MODEL_PATH_J3O1);
        player = (Node)myModel;
        control = player.getControl(AnimControl.class);
        for (String anim : control.getAnimationNames()) {
            System.out.println(anim);
        }
        channel = control.createChannel();
        channel.setAnim(ANI_IDLE);
        
        
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/ShowNormals.j3md");
        //myModel.setMaterial(mat);
        rootNode.attachChild(player);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(Vector3f.UNIT_XYZ.negate());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
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
