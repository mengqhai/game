/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qinghai
 */
public class Utils {
    
    public final static Box BOX1 = new Box(0.5f, 0.5f, 0.5f);
    public final static Box TALL_BOX = new Box(0.5f, 1, 0.5f);
    public final static Box LONG_BOX = new Box(1, 0.5f, 0.5f);
    
    public final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    
    public final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    public final static Trigger TRIGGER_SELECT =  new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    
    public final static String MAPPING_COLOR = "Toggle Color";
    public final static String MAPPING_ROTATE = "Rotate";
    public final static String MAPPING_SELECT = "Select";
    
    public static Geometry createGeom(AssetManager assetManager, String name, Vector3f loc, ColorRGBA color, Mesh mesh) {
        Geometry geom = new Geometry(name, mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }
    
    public static Geometry createBoxGeom1(AssetManager assetManager, String name, Vector3f loc, ColorRGBA color) {
        return createGeom(assetManager, name, loc, color, BOX1);
    }
    
    public static Geometry createBoxGeomTall(AssetManager assetManager, String name, Vector3f loc, ColorRGBA color) {
        return createGeom(assetManager, name, loc, color, TALL_BOX);
    }
    
    public static Geometry createBoxGeomLong(AssetManager assetManager, String name, Vector3f loc, ColorRGBA color) {
        return createGeom(assetManager, name, loc,color, LONG_BOX);
    }
    
    public static Geometry createBoxGeom1(AssetManager assetManager, String name) {
        Vector3f location = new Vector3f(FastMath.nextRandomInt(-20, 20), FastMath.nextRandomInt(-20, 20), FastMath.nextRandomInt(-20, 20));
        return createBoxGeom1(assetManager, name, location, ColorRGBA.randomColor());
    }
    
    public static List<Geometry> createBoxGeom1s(AssetManager assetManager, int count) {
        List<Geometry> result = new ArrayList<Geometry>(count);
        for (int i=0;i<count;i++) {
            result.add(createBoxGeom1(assetManager, "Box"+i));
        }
        return result;
    }
    
    
}
