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
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.Utils;
import mygame.towerdefence.controls.CreepControl;
import mygame.towerdefence.controls.CreepLabelControl;
import mygame.towerdefence.controls.TowerControl;
import mygame.towerdefence.controls.TowerLabelControl;
import mygame.towerdefence.data.Charge;
import mygame.towerdefence.data.CreepData;
import mygame.towerdefence.data.DataService;
import mygame.towerdefence.data.PlayerData;
import mygame.towerdefence.data.TowerData;
import mygame.towerdefence.input.ChargeListener;
import mygame.towerdefence.input.InputConstants;
import mygame.towerdefence.input.Selection;
import mygame.towerdefence.input.SelectionListener;

/**
 *
 * @author liuli
 */
public class GamePlayAppState extends AbstractAppState{
    
    public static final float SCENE_WIDE =16.5f;
    
    private AssetManager assetManager;
    private InputManager inputManager;
    private Camera cam;
    private Node rootNode, guiNode, playerNode, creepNode, towerNode, beamNode;
    private PlayerData playerData;
    private BitmapFont guiFont;
    
    private Selection selection = new Selection();

    public GamePlayAppState() {
    }
    
    private void initFloor() {
        Box floorMesh = new Box(SCENE_WIDE, 0, SCENE_WIDE);
        Geometry floor = Utils.createGeom(assetManager, "Floor", new Vector3f(0, 0, SCENE_WIDE-0.5f), ColorRGBA.Orange, floorMesh);
        rootNode.attachChild(floor);
    }
    
    private void initPlayer() {
        playerNode = new Node();
        String name = "Player";
        Geometry player = Utils.createBoxGeomLong(assetManager, name, new Vector3f(0, 0.5f, 0), ColorRGBA.Yellow);
        playerData = DataService.INSTANCE.createPlayerData(name);
        player.setUserData(PlayerData.KEY, playerData);
        playerNode.attachChild(player);
        rootNode.attachChild(playerNode);
    }
    
    private void initTower() {
        towerNode = new Node();
        int totalTowers = 6;
        for (int i=0;i<totalTowers;i++) {
            int x = FastMath.nextRandomInt(-5, 5);
            int z = FastMath.nextRandomInt(0, 16);
            addTower(x, z, towerNode, i);
        }
        rootNode.attachChild(towerNode);
    }
    
    private void addTower(float x, float z, Node node, int index) {
        String name = "Tower "+index;
        Geometry tower = Utils.createBoxGeomTall(assetManager, name, new Vector3f(x, 1, z), ColorRGBA.Green);
        TowerData tData = DataService.INSTANCE.createTowerData(name);
        tower.setUserData(TowerData.KEY, tData);
        for (int i=0;i<1000;i++) {
            // some test charges
            tData.getCharges().add(new Charge());
        }
        
        tower.addControl(new TowerControl(beamNode, creepNode, assetManager));
        tower.addControl(new TowerLabelControl(guiNode, cam, guiFont));
        node.attachChild(tower);
    }
    
    private void initCreep() {
        creepNode = new Node();
        int totalCreepCount = 10;
        for (int i=0;i<totalCreepCount;i++) {
            addCreep(FastMath.nextRandomFloat()*SCENE_WIDE*2 - SCENE_WIDE, SCENE_WIDE*2, creepNode, i);
        }
        rootNode.attachChild(creepNode);
    }
    
    private void addCreep(float x, float z, Node node, int index) {
        String name = "Creep "+index;
        Geometry creep = Utils.createBoxGeom1(assetManager, name, new Vector3f(x, 0.5f, z), ColorRGBA.Black);
        creep.setUserData(CreepData.KEY, DataService.INSTANCE.createCreepData(name));
        creep.addControl(new CreepControl(creepNode));
        creep.addControl(new CreepLabelControl(guiNode, cam, guiFont));
        node.attachChild(creep);
    }
    
    private void initBeam() {
        beamNode = new Node();
        rootNode.attachChild(beamNode);
    }
    
    private void initListeners() {
        SelectionListener sListener = new SelectionListener(inputManager, selection, cam, rootNode);
        ChargeListener cListener = new ChargeListener(selection);
        inputManager.addMapping(InputConstants.MAPPING_SELECT, InputConstants.TRIGGER_MOUSE_LEFT);
        inputManager.addListener(sListener, InputConstants.MAPPING_SELECT);
        inputManager.addMapping(InputConstants.MAPPING_CHARGE, InputConstants.TRIGGER_MOUSE_RIGHT);
        inputManager.addListener(cListener, InputConstants.MAPPING_CHARGE);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        SimpleApplication sApp = (SimpleApplication)app;
        rootNode = sApp.getRootNode();
        assetManager = sApp.getAssetManager();
        inputManager = sApp.getInputManager();
        cam = sApp.getCamera();
        cam.setLocation(new Vector3f(0, SCENE_WIDE*2, SCENE_WIDE*2));
        cam.lookAt(new Vector3f(0, 0, SCENE_WIDE), Vector3f.UNIT_Y);
        sApp.getFlyByCamera().setMoveSpeed(50);
        sApp.getFlyByCamera().setDragToRotate(true);
        inputManager.setCursorVisible(true);
        guiFont = assetManager.loadFont("Interface/Fonts/cn.fnt");
        guiNode = sApp.getGuiNode();

        initFloor();
        initBeam();
        initPlayer();
        initCreep();
        initTower();
        initListeners();
        
        
        
        super.initialize(stateManager, app); 
    }

    @Override
    public void update(float tpf) {
        if (playerData.getHealth()<=0) {
            System.out.println("Game over");
            playerData.setLastGameWon(false);
            // TODO detach the state
        }
    }
    
    
    
}
