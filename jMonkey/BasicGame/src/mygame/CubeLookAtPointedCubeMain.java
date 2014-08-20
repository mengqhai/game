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
import java.util.List;

/**
 *
 * @author qinghai
 */
public class CubeLookAtPointedCubeMain extends SimpleApplication {

    private Geometry selected;
    private Geometry target;
    private ActionListener colorListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if (selected != null && !isPressed) {
                selected.getMaterial().setColor("Color", ColorRGBA.randomColor());
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
                    if (name.equals(Utils.MAPPING_SELECT)) {
                        selected = closest.getGeometry();
                    } else {
                        target = closest.getGeometry();
                    }
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
        inputManager.addListener(selectListener, Utils.MAPPING_SELECT);
        inputManager.addListener(selectListener, Utils.MAPPING_ROTATE);
        rootNode.attachChild(Utils.createBoxGeom1(assetManager, "Red Cube", new Vector3f(0, 1.5f, 0), ColorRGBA.Red));
        rootNode.attachChild(Utils.createBoxGeom1(assetManager, "Blue Cube", new Vector3f(0, -1.5f, 0), ColorRGBA.Blue));

        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);


        List<Geometry> boxes = Utils.createBoxGeom1s(assetManager, 40);
        for (Geometry box : boxes) {
            rootNode.attachChild(box);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector2f cursor2d = inputManager.getCursorPosition();
        Vector3f cursor3d = cam.getWorldCoordinates(cursor2d, 0);

        if (selected != null && target != null) {
           selected.lookAt(target.getLocalTranslation(), Vector3f.UNIT_Y);
        } else if (selected !=null) {
             selected.lookAt(cursor3d, Vector3f.UNIT_Y);
        }
    }

    public static void main(String[] args) {
        CubeLookAtPointedCubeMain app = new CubeLookAtPointedCubeMain();
        app.start();
    }
}
