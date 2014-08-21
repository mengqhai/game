/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence;

import com.jme3.app.SimpleApplication;

/**
 *
 * @author liuli
 */
public class TowerDefenceMain extends SimpleApplication{
    
    public static void main(String[] args) {
        TowerDefenceMain app = new TowerDefenceMain();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        this.settings.setTitle("Tower defence game");
        GamePlayAppState playState = new GamePlayAppState();
        stateManager.attach(playState);
    }
    
}
