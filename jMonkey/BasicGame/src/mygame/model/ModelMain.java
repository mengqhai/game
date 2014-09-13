package mygame.model;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 * test
 *
 * @author normenhansen
 */
public class ModelMain extends SimpleApplication {

    private static final String MODEL_PATH_J3O = "Textures/test/mymodel.mesh.j3o";
    private static final String MODEL_PATH_ORGE = "Textures/mymodel/mymodel.mesh.xml";
    private static final String MODEL_PATH_WF = "Textures/mymodel/mymodel.obj";
    private static final String MODEL_MY_ROBOT = "Textures/myrobot/robot.mesh.j3o";

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My model application");
        ModelMain app = new ModelMain();
        app.setSettings(settings);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        Node myModel = (Node)assetManager.loadModel(MODEL_MY_ROBOT);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        //myModel.setMaterial(mat);
        rootNode.attachChild(myModel);
        
        AudioNode audio = (AudioNode)myModel.getChild("AudioNode");
        if (audio!=null) {
            audio.play();
        }
        
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(Vector3f.UNIT_XYZ.negate());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

//        DirectionalLight sun2 = new DirectionalLight();
//        sun2.setDirection(Vector3f.UNIT_XYZ);
//        sun2.setColor(ColorRGBA.White);
//        rootNode.addLight(sun2);

        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

    }

    @Override
    public void simpleUpdate(float tpf) {
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
