/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.Utils;

/**
 *
 * @author liuli
 */
public class GamePlayAppState extends AbstractAppState{
    
    public static final float SCENE_WIDE =16.5f;
    
    private AssetManager assetManager;
    private Camera cam;
    private Node rootNode, playerNode, creepNode, towerNode, beamNode;

    public GamePlayAppState() {
    }
    
    private void initFloor() {
        Box floorMesh = new Box(SCENE_WIDE, 0, SCENE_WIDE);
        Geometry floor = Utils.createGeom(assetManager, "Floor", new Vector3f(0, 0, SCENE_WIDE-0.5f), ColorRGBA.Orange, floorMesh);
        rootNode.attachChild(floor);
    }
    
    private void initPlayer() {
        playerNode = new Node();
        Geometry player = Utils.createBoxGeomLong(assetManager, "Player", new Vector3f(0, 0.5f, 0), ColorRGBA.Yellow);
        playerNode.attachChild(player);
        rootNode.attachChild(playerNode);
    }
    
    private void initTower() {
        towerNode = new Node();;
        addTower(-5, 10, towerNode, 0);
        rootNode.attachChild(towerNode);
    }
    
    private void addTower(float x, float z, Node node, int index) {
        Geometry tower = Utils.createBoxGeomTall(assetManager, "Tower "+index, new Vector3f(x, 1, z), ColorRGBA.Green);
        node.attachChild(tower);
    }
    
    private void initCreep() {
        creepNode = new Node();
        addCreep(0, 16, creepNode, 0);
        rootNode.attachChild(creepNode);
    }
    
    private void addCreep(float x, float z, Node node, int index) {
        Geometry creep = Utils.createBoxGeom1(assetManager, "Creep "+index, new Vector3f(x, 0.5f, z), ColorRGBA.Black);
        node.attachChild(creep);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        SimpleApplication sApp = (SimpleApplication)app;
        rootNode = sApp.getRootNode();
        assetManager = sApp.getAssetManager();
        cam = sApp.getCamera();
        sApp.getFlyByCamera().setMoveSpeed(50);
        
        initFloor();
        initPlayer();
        initTower();
        initCreep();
        
        
        beamNode = new Node();
        super.initialize(stateManager, app); 
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    
    
}
