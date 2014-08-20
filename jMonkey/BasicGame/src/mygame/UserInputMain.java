/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author qinghai
 */
public class UserInputMain extends SimpleApplication {

    private Geometry geom;
    private ActionListener colorListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                geom.getMaterial().setColor("Color", ColorRGBA.randomColor());
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            geom.rotate(0, value * 10f, 0);
        }
    };

    @Override
    public void simpleInitApp() {
        inputManager.addMapping(Utils.MAPPING_COLOR, Utils.TRIGGER_COLOR);
        inputManager.addMapping(Utils.MAPPING_ROTATE, Utils.TRIGGER_ROTATE);
        inputManager.addListener(colorListener, Utils.MAPPING_COLOR);
        inputManager.addListener(analogListener, Utils.MAPPING_ROTATE);
        geom = Utils.createBoxGeom1(assetManager, "Box1", Vector3f.ZERO, ColorRGBA.Blue);
        rootNode.attachChild(geom);
    }

    public static void main(String[] args) {
        UserInputMain app = new UserInputMain();
        app.start();
    }
}
