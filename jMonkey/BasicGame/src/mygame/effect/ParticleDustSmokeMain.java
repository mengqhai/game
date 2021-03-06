/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.effect;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
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
public class ParticleDustSmokeMain extends SimpleApplication {

    private float angle;
    private ParticleEmitter dustEmitter;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My particle dust application");
        settings.setSettingsDialogImage(null);
        ParticleDustSmokeMain app = new ParticleDustSmokeMain();
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
        dustEmitter = new ParticleEmitter("Spark emitter", Type.Triangle, 60);
        rootNode.attachChild(dustEmitter);
        
        Material dustMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        dustMat.setTexture("Texture", assetManager.loadTexture("Effects/debris.png"));
        dustEmitter.setMaterial(dustMat);
        dustEmitter.setImagesX(2);
        dustEmitter.setImagesY(2);
        dustEmitter.setSelectRandomImage(true);
        dustEmitter.setRandomAngle(true);
        dustEmitter.getParticleInfluencer().setVelocityVariation(1f);
        
        
    }
    
    @Override
    public void simpleInitApp() {
        initLight();
        initEffect();
    }

    @Override
    public void simpleUpdate(float tpf) {
        angle += tpf;
        angle %= FastMath.TWO_PI;
        float x = FastMath.cos(angle)*2;
        float y = FastMath.sin(angle)*2;
        dustEmitter.setLocalTranslation(x, 0, y);
    }
    
    
}
