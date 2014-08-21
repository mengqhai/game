/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.input;

import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author qinghai
 */
public class SelectionListener implements ActionListener {
    
    private InputManager iManager;
    private Selection selection;
    private Camera cam;
    private Node rootNode;

    public SelectionListener(InputManager iManager, Selection selection, Camera cam, Node rootNode) {
        this.iManager = iManager;
        this.selection = selection;
        this.cam = cam;
        this.rootNode = rootNode;
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            Vector2f click2d = iManager.getCursorPosition();
            Vector3f click3f = cam.getWorldCoordinates(click2d, 0);
            Vector3f dir = cam.getWorldCoordinates(click2d, 1).subtract(click3f);
            CollisionResults results = new CollisionResults();
            Ray ray = new Ray(click3f, dir);
            rootNode.collideWith(ray, results);
            if (results.size() == 0) {
                return;
            }
            String selected = results.getClosestCollision().getGeometry().getName();
            selection.setSelectedName(selected);
            System.out.println("Selected:"+selected);
        }
    }
}
