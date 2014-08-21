/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.data;

/**
 *
 * @author liuli
 */
public class Charge {
    
    private int damage; // damage value
    private int bullets; // remaining bullets

    public Charge(int damage, int bullets) {
        this.damage = damage;
        this.bullets = bullets;
    }

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
    
    public void descreaseBullets(int num) {
        this.bullets -= num;
    }
}
