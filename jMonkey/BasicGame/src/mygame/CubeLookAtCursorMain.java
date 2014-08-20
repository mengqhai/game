/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author qinghai
 */
public class CubeLookAtCursorMain extends SimpleApplication {

    private Geometry selected;
    private ActionListener colorListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (selected != null && !isPressed) {
                selected.getMaterial().setColor("Color", ColorRGBA.randomColor());
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (selected != null) {
                selected.rotate(0, value * 10f, 0);
            }
        }
    };
    private ActionListener selectListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (!isPressed) {
                CollisionResults results = new CollisionResults();

                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(click2d, 0);
                Vector3f dir = cam.getWorldCoordinates(click2d, 1).subtract(click3d);

                Ray ray = new Ray(click3d, dir);
                rootNode.collideWith(ray, results);
                for (CollisionResult r : results) {
                    float dist = r.getDistance();
                    Vector3f pt = r.getContactPoint();
                    String target = r.getGeometry().getName();
                    System.out.println("Collision: " + target + " at " + pt + ", " + dist + " WU away.");
                }
                CollisionResult closest = results.getClosestCollision();
                if (closest != null) {
                    selected = closest.getGeometry();
                } else {
                    System.out.println("Selected nothing!");
                }
            }
        }
    };

    @Override
    public void simpleInitApp() {
        inputManager.addMapping(Utils.MAPPING_COLOR, Utils.TRIGGER_COLOR);
        inputManager.addMapping(Utils.MAPPING_ROTATE, Utils.TRIGGER_ROTATE);
        inputManager.addMapping(Utils.MAPPING_SELECT, Utils.TRIGGER_SELECT);
        inputManager.addListener(colorListener, Utils.MAPPING_COLOR);
        inputManager.addListener(analogListener, Utils.MAPPING_ROTATE);
        inputManager.addListener(selectListener, Utils.MAPPING_SELECT);
        rootNode.attachChild(Utils.createBoxGeom1(assetManager, "Red Cube", new Vector3f(0, 1.5f, 0), ColorRGBA.Red));
        rootNode.attachChild(Utils.createBoxGeom1(assetManager, "Blue Cube", new Vector3f(0, -1.5f, 0), ColorRGBA.Blue));

        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector2f cursor2d = inputManager.getCursorPosition();
        Vector3f cursor3d = cam.getWorldCoordinates(cursor2d, 0);
        
        if (selected != null) {
            selected.lookAt(cursor3d, Vector3f.UNIT_Y);
        }
    }

    public static void main(String[] args) {
        CubeLookAtCursorMain app = new CubeLookAtCursorMain();
        app.start();
    }
}
