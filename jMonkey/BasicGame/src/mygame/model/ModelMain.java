package mygame.model;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 * test
 * @author normenhansen
 */
public class ModelMain extends SimpleApplication {
    
    private static final String MODEL_PATH_J3O = "Textures/test/mymodel.mesh.j3o";
    private static final String MODEL_PATH_ORGE = "Textures/mymodel/mymodel.mesh.xml";
    private static final String MODEL_PATH_WF = "Textures/mymodel/mymodel.obj";
    

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My model application");
        ModelMain app = new ModelMain();
        app.setSettings(settings);
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        Spatial myModel = assetManager.loadModel(MODEL_PATH_J3O);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/ShowNormals.j3md");
        //myModel.setMaterial(mat);
        rootNode.attachChild(myModel);
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
