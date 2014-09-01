/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.effect;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class ParticleFlashMain extends SimpleApplication {

    private float angle;
    private ParticleEmitter flashEmitter;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My particle dust application");
        settings.setSettingsDialogImage(null);
        ParticleFlashMain app = new ParticleFlashMain();
        app.setSettings(settings);
        app.start();
    }

    private void initLight() {
        /**
         * A white, directional light source
         */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(0, -1f, -5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }

    
    private void initEffect() {
        flashEmitter = new ParticleEmitter("Spark emitter", Type.Triangle, 60);
        rootNode.attachChild(flashEmitter);
        
        Material dustMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        dustMat.setTexture("Texture", assetManager.loadTexture("Effects/flash.png"));
        flashEmitter.setMaterial(dustMat);
        flashEmitter.setImagesX(2);
        flashEmitter.setImagesY(2);
        flashEmitter.setSelectRandomImage(true);
        flashEmitter.setStartColor(new ColorRGBA(1, 0.8f, 0.36f, 1));
        flashEmitter.setEndColor(new ColorRGBA(1, 0.8f, 0.36f, 0));
        flashEmitter.setStartSize(0.1f);
        flashEmitter.setEndSize(5f);
        flashEmitter.setGravity(0, 0 , 0);
        
        flashEmitter.setLowLife(.2f);
        flashEmitter.setHighLife(.2f);
        flashEmitter.getParticleInfluencer()
                .setInitialVelocity(new Vector3f(0, 5f, 0));
        flashEmitter.getParticleInfluencer()
                .setVelocityVariation(1);
        flashEmitter.setShape(new EmitterSphereShape(Vector3f.ZERO, .5f));
    }
    
    @Override
    public void simpleInitApp() {
        initLight();
        initEffect();
    }

    @Override
    public void simpleUpdate(float tpf) {
//        angle += tpf;
//        angle %= FastMath.TWO_PI;
//        float x = FastMath.cos(angle)*2;
//        float y = FastMath.sin(angle)*2;
//        flashEmitter.setLocalTranslation(x, 0, y);
    }
    
    
}
