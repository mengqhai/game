/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.controls;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.List;
import mygame.towerdefence.data.Charge;
import mygame.towerdefence.data.CreepData;
import mygame.towerdefence.data.TowerData;

/**
 *
 * @author qinghai
 */
public class TowerControl extends AbstractControl {

    private Node beamNode;
    private Node creepNode;
    private AssetManager assetManager;
    private long lastShootTime;

    public TowerControl(Node beamNode, Node creepNode, AssetManager assetManager) {
        this.beamNode = beamNode;
        this.creepNode = creepNode;
        this.assetManager = assetManager;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f towerLoc = spatial.getLocalTranslation();
        TowerData tData = spatial.getUserData(TowerData.KEY);
        if (tData.getCharges().isEmpty()) {
            return;
        }
        
        if (System.currentTimeMillis() - lastShootTime < tData.getShootInterval()) {
            // just shot, too fast, try next loop
            return;
        }
        
        List<Spatial> creeps = creepNode.getChildren();
        List<Spatial> reachable = new ArrayList<Spatial>();
        Spatial closestCreep = null;
        float closestDist = Float.MAX_VALUE;
        for (Spatial creep : creeps) {
            Vector3f creepLoc = creep.getLocalTranslation();
            float dist = towerLoc.distance(creepLoc);
            if (dist < tData.getRange()) {
                reachable.add(creep);
                if (dist < closestDist) {
                    closestCreep = creep;
                    closestDist = dist;
                }
            };
        }

        if (!reachable.isEmpty() && closestCreep != null) {
            // shoot at the closest creep
            shootAtCreep(closestCreep, tData.getCharges());
        }
    }

    private void shootAtCreep(Spatial creep, List<Charge> charges) {
        CreepData cData = creep.getUserData(CreepData.KEY);
        Charge charge = charges.get(0);
        charge.descreaseBullets(1);
        if (charge.getBullets() <= 0) {
            charges.remove(0);
            System.out.println("Charge empty and removed");
        }
        cData.descreaseHealth(charge.getDamage());
        new BeamControl(beamNode, assetManager, spatial.getLocalTranslation(), creep.getLocalTranslation());
        lastShootTime = System.currentTimeMillis();
        System.out.println("Shot "+creep.getName()+" at "+lastShootTime);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
