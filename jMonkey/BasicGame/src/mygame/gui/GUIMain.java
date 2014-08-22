package mygame.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.List;

/**
 * test
 * @author normenhansen
 */
public class GUIMain extends SimpleApplication {
    
    private float distance = 0;
    private BitmapText distanceText;
    
    private final String ICON_MARIO_DEFULT = "Interface/Images/Mario-icon.png";
    private final String ICON_MARIO_EYE_HALF = "Interface/Images/Mario-icon-eye-half.png";
    private final String ICON_MARIO_CLOSE = "Interface/Images/Mario-icon-eye-close.png";
    
    private Picture icon = new Picture("Mario");

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My test application");
        settings.setSettingsDialogImage(null);
        GUIMain app = new GUIMain();
        app.setSettings(settings);
        app.start();
        
    }
    
    private void initGUI() {
        setDisplayFps(false);
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/cn.fnt"); //assetManager.loadFont("Interface/Fonts/Default.fnt");
        distanceText = new BitmapText(guiFont);
        distanceText.setSize(guiFont.getCharSet().getRenderedSize());
        distanceText.move(settings.getWidth()/2, settings.getHeight()/2, 0);
        guiNode.attachChild(distanceText);
        
        icon.setImage(assetManager, ICON_MARIO_DEFULT, true);
        icon.setWidth(128);
        icon.setHeight(128);
        icon.move(settings.getWidth()/2 -128, settings.getHeight()/2-128, 0);//distanceText.getLocalTranslation().subtract(Vector3f.UNIT_X.mult(128)));
        guiNode.attachChild(icon);
    }
    
    private Spatial labelSpatial(Spatial s) {
        Node labeledNode = new Node();
        labeledNode.attachChild(s);
        BitmapText label = new BitmapText(guiFont);
        label.setColor(ColorRGBA.White);
        label.setSize(0.5f);
        label.setText(s.getName());
        label.setLocalTranslation(Vector3f.UNIT_Y.mult(2).add(s.getLocalTranslation()));
        labeledNode.attachChild(label);
        label.addControl(new BillboardControl());
        return labeledNode;
    }
    
    private Spatial labelSpatialIn2D(Spatial s) {
        BitmapText label = new BitmapText(guiFont);
        label.setColor(ColorRGBA.White);
        label.setSize(guiFont.getCharSet().getRenderedSize()*0.5f);
        label.setText(s.getName());
        guiNode.attachChild(label);
        s.addControl(new ObjectLabelControl(guiNode, cam, label));
        return s;
    }

    @Override
    public void simpleInitApp() {
        initGUI();
        
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        
        Box b2 = new Box(1, 1, 1);
        Geometry geom2 = new Geometry("Box2", b2);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Yellow);
        geom2.setMaterial(mat2);
        geom2.setLocalTranslation(2, 3, 4);
//        float r = FastMath.DEG_TO_RAD * 45f;
//        geom2.rotate(r, 0, 0);
        Quaternion roll045 = new Quaternion().fromAngleAxis(45f * FastMath.DEG_TO_RAD, Vector3f.UNIT_X);
        geom2.rotate(roll045);
        
        
        rootNode.attachChild(this.labelSpatialIn2D(geom));
        rootNode.attachChild(this.labelSpatialIn2D(geom2));
        
        flyCam.setMoveSpeed(50);
    }

    @Override
    public void simpleUpdate(float tpf) {
        distance = cam.getLocation().distance(Vector3f.ZERO);
        StringBuilder sb = new StringBuilder("你好Distance:看不见这些字（因为字体里面没有）");
        sb.append(distance);
        distanceText.setText(sb.toString());
        
        if (distance<10 && distance > 5) {
            icon.setImage(assetManager, ICON_MARIO_EYE_HALF, true);
        } else if (distance <=5) {
            icon.setImage(assetManager, ICON_MARIO_CLOSE, true);
        } else {
            icon.setImage(assetManager, ICON_MARIO_DEFULT, true);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
