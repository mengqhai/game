/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import mygame.towerdefence.data.CreepData;
import mygame.towerdefence.data.DataService;
import mygame.towerdefence.data.PlayerData;

/**
 *
 * @author liuli
 */
public class CreepControl extends AbstractControl {
    
    private Node creepNode;
    
    public CreepControl(Node creepNode) {
        this.creepNode = creepNode;
    }
    
    private boolean isStromed() {
        return spatial.getLocalTranslation().z <= 0;
    }
    
    private boolean isAlive() {
        CreepData cData = spatial.getUserData(CreepData.KEY);
        return cData.getHealth() > 0;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        PlayerData playerData = DataService.INSTANCE.getData("Player");
        if (isStromed()) {
            // I've stormed the player base
            playerData.healthDecrease(1);
            creepNode.detachChild(spatial);
            System.out.println(spatial.getName()+" have stormed the base.");
        }
        
        if (isAlive()) {
            // Alive creep moves to the base
            Vector3f endPoint = new Vector3f(0, spatial.getLocalTranslation().y, 0);
            Vector3f dir = endPoint.subtract(spatial.getLocalTranslation());
            dir = dir.divide(dir.length()); //make the dir a unit vector
            spatial.lookAt(endPoint, Vector3f.UNIT_Y);
            spatial.move(dir.mult(tpf));
        } else {
            // die
            CreepData cData = spatial.getUserData(CreepData.KEY);
            playerData.addBudget(cData.getBonus());
            creepNode.detachChild(spatial);
            System.out.println(spatial.getName()+" is dead.");
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
