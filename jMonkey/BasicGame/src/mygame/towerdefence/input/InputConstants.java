/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.towerdefence.input;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author qinghai
 */
public class InputConstants {
    
    public static final String MAPPING_SELECT = "select";
    public static final String MAPPING_CHARGE = "charge";
    
    public static final Trigger TRIGGER_MOUSE_LEFT = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    public static final Trigger TRIGGER_MOUSE_RIGHT = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
    
    
}
