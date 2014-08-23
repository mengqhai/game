/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Sphere;

public class DrawBorderMain extends SimpleApplication {

    public static void main(String[] args) {
        DrawBorderMain app = new DrawBorderMain();
        app.start();
    }
    Geometry geom;
    Geometry bx;
    WireBox wbx;

    @Override
    public void simpleInitApp() {
        Mesh sphr = new Sphere(10, 10, 1f);
        geom = new Geometry("Sphere", sphr);
        geom.scale(2, 1, 1);  //check if scale works with bx correctly

//geom.setModelBound(bb);
        wbx = new WireBox();
        wbx.fromBoundingBox((BoundingBox) geom.getWorldBound());

        Geometry bx = new Geometry("TheMesh", wbx);
        Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_box.setColor("Color", ColorRGBA.Blue);
        bx.setMaterial(mat_box);
        bx.updateModelBound();


        rootNode.attachChild(bx);


        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        geom.setMaterial(mat);
        rootNode.attachChild(geom);


        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.8f, -0.6f, -0.08f).normalizeLocal());
        dl.setColor(new ColorRGBA(1, 1, 1, 1));
        rootNode.addLight(dl);

        viewPort.setBackgroundColor(ColorRGBA.Gray);
        flyCam.setMoveSpeed(30);

    }

    @Override
    public void simpleUpdate(float tpf) {
//float angle =0;
        float angle = tpf * 2f;
//  angle %= FastMath.TWO_PI;


        geom.move(0, angle, 0);
        geom.rotate(0, angle, 0);
        System.out.println(geom.getLocalRotation().getY());

    }
}