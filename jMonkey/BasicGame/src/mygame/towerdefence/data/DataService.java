/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liuli
 */
public class DataService {
    
    public static final DataService INSTANCE = new DataService();
    
    private Map<String, Object> dataMap;

    private DataService() {
        dataMap = new HashMap<String, Object>();
    }
    
    public PlayerData createPlayerData(String name) {
        PlayerData data = new PlayerData();
        dataMap.put(name, data);
        return data;
    }
    
    public TowerData createTowerData(String name) {
        TowerData data = new TowerData();
        dataMap.put(name, data);
        return data;
    }
    
    public CreepData createCreepData(String name) {
        CreepData data = new CreepData();
        dataMap.put(name, data);
        return data;
    }
    
    public <T> T getData(String name) {
        return (T)dataMap.get(name);
    }
}
