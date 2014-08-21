/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 *
 * @author liuli
 */
public class PlayerData implements Savable{
    
    public static final String KEY = "PlayerData";
    
    private int level, score, health = 100, budget = 100;
    private boolean lastGameWon;
    
    PlayerData() {
        
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public boolean isLastGameWon() {
        return lastGameWon;
    }

    public void setLastGameWon(boolean lastGameWon) {
        this.lastGameWon = lastGameWon;
    }
    
    public synchronized void healthDecrease(int num) {
        this.health = this.health - num;
    }
    
    public synchronized void addBudget(int bonus) {
        this.budget += bonus;
    }
    
    public synchronized void descreaseBudget(int price) {
        this.budget -= price;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
