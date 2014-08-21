/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.controls;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;

/**
 *
 * @author qinghai
 */
public class BeamControl extends AbstractControl{
    
    private static final int BEAM_LIFE_TIME = 200;
    private int remainingLifeTime = BEAM_LIFE_TIME;
    
    private Node beamNode;

    public BeamControl(Node beamNode, AssetManager assetManager, Vector3f start, Vector3f end) {
        this.beamNode = beamNode;
        createBeam(assetManager, start, end);
    }
    
    private void createBeam(AssetManager assetManager, Vector3f start, Vector3f end) {
        if (spatial!=null) {
            throw new IllegalStateException("The beam controller has already been attached.");
        }
        // draw a line
        Line mesh = new Line(start, end);
        Geometry line = new Geometry("Beam "+beamNode.getChildren().size(), mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        line.setMaterial(mat);
        line.addControl(this);
        beamNode.attachChild(line);
    }

    @Override
    protected void controlUpdate(float tpf) {
        remainingLifeTime = remainingLifeTime - (int)(tpf*1000);
        if (remainingLifeTime<=0) {
            beamNode.detachChild(spatial);
            System.out.println(spatial.getName()+ " detached");
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
    
}
