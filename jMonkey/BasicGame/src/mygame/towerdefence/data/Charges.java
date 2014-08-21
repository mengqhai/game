/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

/**
 *
 * @author liuli
 */
public class Charges {
    
    private int damage; // damage value
    private int bullets; // remaining bullets

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
}
