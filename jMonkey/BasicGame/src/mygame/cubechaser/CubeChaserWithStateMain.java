package mygame.cubechaser;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import java.util.List;
import mygame.Utils;
import mygame.control.CubeChaserControl;
import mygame.state.CubeChaserState;

/**
 * test
 *
 * @author normenhansen
 */
public class CubeChaserWithStateMain extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Cube chaser with State");
        CubeChaserWithStateMain app = new CubeChaserWithStateMain();
        app.setSettings(settings);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        CubeChaserState state = new CubeChaserState();
        stateManager.attach(state);
        
        flyCam.setMoveSpeed(100);
        attachCenterMark();
    }

    private void attachCenterMark() {
        Geometry c = Utils.createBoxGeom1(assetManager, "Center Mark", Vector3f.ZERO, ColorRGBA.White);
        c.scale(4);
        c.setLocalTranslation(settings.getWidth() / 2, settings.getHeight() / 2, 0);
        guiNode.attachChild(c);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
