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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author qinghai
 */
public class Utils {
    
    public final static Box BOX1 = new Box(1, 1, 1);
    
    public final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    
    public final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    
    public final static String MAPPING_COLOR = "Toggle Color";
    public final static String MAPPING_ROTATE = "Rotate";
    
    public static Geometry createBoxGeom1(AssetManager assetManager, String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry(name, BOX1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }
    
    
}
