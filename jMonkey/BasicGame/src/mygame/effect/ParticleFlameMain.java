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
public class ParticleFlameMain extends SimpleApplication {

    private float angle;
    private ParticleEmitter flameEmitter;

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("My particle dust application");
        settings.setSettingsDialogImage(null);
        ParticleFlameMain app = new ParticleFlameMain();
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
        flameEmitter = new ParticleEmitter("Spark emitter", Type.Triangle, 30);
        rootNode.attachChild(flameEmitter);
        
        Material dustMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        dustMat.setTexture("Texture", assetManager.loadTexture("Effects/flame.png"));
        flameEmitter.setMaterial(dustMat);
        flameEmitter.setImagesX(2);
        flameEmitter.setImagesY(2);
        flameEmitter.setSelectRandomImage(true);
        flameEmitter.setRandomAngle(true);
        flameEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
        flameEmitter.getParticleInfluencer().setVelocityVariation(0.2f);
        flameEmitter.setStartColor(new ColorRGBA(1,1,.5f,1f));
        flameEmitter.setEndColor(new ColorRGBA(1,0,0,0));
        flameEmitter.setGravity(0, 0, 0);
        flameEmitter.setStartSize(1.5f);
        flameEmitter.setEndSize(0.05f);
        flameEmitter.setLowLife(.5f);
        flameEmitter.setHighLife(2f);
        
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
        flameEmitter.setLocalTranslation(x, 0, y);
    }
    
    
}
