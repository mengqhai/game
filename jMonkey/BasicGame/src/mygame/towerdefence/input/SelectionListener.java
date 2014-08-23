/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.input;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;

/**
 *
 * @author qinghai
 */
public class SelectionListener implements ActionListener {

    private InputManager iManager;
    private Selection selection;
    private Camera cam;
    private Node rootNode;
    private Geometry selectionBox;
    private AssetManager assetManager;

    public SelectionListener(InputManager iManager, AssetManager assetManager, Selection selection, Camera cam, Node rootNode) {
        this.iManager = iManager;
        this.selection = selection;
        this.cam = cam;
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }

    private Geometry createSelectionBox() {
        WireBox wbx = new WireBox();
        Geometry bx = new Geometry("Bound for Selection", wbx);
        Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_box.setColor("Color", ColorRGBA.Blue);
        bx.setMaterial(mat_box);
        bx.updateModelBound();
        bx.setLocalScale(1.1f);
        return bx;
    }

    private void updateSelectionBox(String selected) {
        Spatial selectedObj = rootNode.getChild(selected);
        if (selectionBox == null) {
            selectionBox = createSelectionBox();
            rootNode.attachChild(selectionBox);
        }

        WireBox wbx = (WireBox) selectionBox.getMesh();
        BoundingBox bb= (BoundingBox) selectedObj.getWorldBound();
        wbx.fromBoundingBox(bb);
        selectionBox.updateModelBound();
        selectionBox.setLocalScale(1f);
        //selectionBox.setLocalTranslation(selectedObj.getLocalTranslation());
        selectionBox.setLocalTranslation(selectedObj.getWorldTranslation());

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
            Geometry selectedGeom = results.getClosestCollision().getGeometry();
            String selected = null;
            
            if (selectedGeom.getParent() == rootNode || selectedGeom.getParent().getName() == null) {
                return;
            }
            
            if ("TowerNode".equals(selectedGeom.getParent().getName())) {
                selected=selectedGeom.getName();
            } else if (selectedGeom.getParent().getName().startsWith("Tower ")) {
                // a single tower's Node
                selected = selectedGeom.getParent().getName();
            }
            if (selected == null) {
                return;
            }
            selection.setSelectedName(selected);
            System.out.println("Selected:" + selected);
           if (selected.startsWith("Tower")) {
                updateSelectionBox(selected);
           }
        }
    }
}
