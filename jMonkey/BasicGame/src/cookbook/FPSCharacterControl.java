/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cookbook;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;

/**
 * A character control usually saw in FPS games.
 * 
 * 
 * @author liuli
 */
public class FPSCharacterControl extends BetterCharacterControl implements AnalogListener, ActionListener {

    private boolean forward, backward, leftRotate, rightRotate, leftStrafe, rightStrafe;
    private float moveSpeed = 8.0f;
    private float sensitivity = 100.0f;
    private Node head;
    private float yaw;

    public FPSCharacterControl(float radius, float height, float mass) {
        super(radius, height, mass);
        head = new Node("gameCharacterControl_head");
        head.setLocalTranslation(0, 1.8f, 0);
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial instanceof Node) {
            ((Node) spatial).attachChild(head);
        }
    }

    public void setCamera(Camera cam) {
        CameraNode camNode = new CameraNode("GameCharacterControl_CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        head.attachChild(camNode);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        float rotateValue = tpf * value * sensitivity;
        if (name.equals(InputMapping.RotateLeft.name())) {
            rotateY(rotateValue);
        } else if (name.equals(InputMapping.RotateRight.name())) {
            rotateY(-rotateValue);
        } else if (name.equals(InputMapping.LookUp.name())) {
            lookUpDown(-rotateValue*2);
        } else if (name.equals(InputMapping.LookDown.name())) {
            lookUpDown(rotateValue*2);
        }
    }

    private void rotateY(float value) {
        Quaternion rotate = new Quaternion().fromAngleAxis(FastMath.PI * value, Vector3f.UNIT_Y);
        rotate.multLocal(viewDirection);
        setViewDirection(viewDirection);
    }

    /**
     * In order to handle looking up and down, you have to do some more work.
     * The idea is to let the spatial object handle this. For this, you need to
     * step back to the top of the class and add two more fields: a Node field
     * called head and a float field called yaw. The yaw field will be the value
     * with which you will control the rotation of the head up and down.
     *
     * @param value
     */
    private void lookUpDown(float value) {
        yaw += value;
        yaw = FastMath.clamp(yaw, -FastMath.HALF_PI, FastMath.HALF_PI);
        //  clamp it so that it can't be rotated more than 90 degrees up or down. Not doing this might lead to weird results.
        head.setLocalRotation(new Quaternion().fromAngles(yaw, 0, 0));
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(InputMapping.StrafeLeft.name())) {
            leftStrafe = isPressed;
        } else if (name.equals(InputMapping.StrafeRight.name())) {
            rightStrafe = isPressed;
        } else if (name.equals(InputMapping.MoveForward.name())) {
            forward = isPressed;
        } else if (name.equals(InputMapping.MoveBackward.name())) {
            backward = isPressed;
        } else if (name.equals(InputMapping.Jump.name())) {
            jump();
        } else if (name.equals(InputMapping.Duck.name())) {
            setDucked(isPressed);
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);

        if (forward) {
            walkDirection.addLocal(modelForwardDir.mult(moveSpeed));
        } else if (backward) {
            walkDirection.addLocal(modelForwardDir.negate().mult(moveSpeed));
        }
        if (leftStrafe) {
            walkDirection.addLocal(modelLeftDir.mult(moveSpeed));
        } else if (rightStrafe) {
            walkDirection.addLocal(modelLeftDir.negate().multLocal(moveSpeed));
        }

        this.setWalkDirection(walkDirection);
    }
}
