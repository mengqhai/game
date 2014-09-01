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
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;

/**
 *
 * @author qinghai
 */
public class ParticleDustSmokeMain extends SimpleApplication {


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
        ParticleEmitter sparksEmitter = new ParticleEmitter("Spark emitter", Type.Triangle, 60);
        rootNode.attachChild(sparksEmitter);
        
        Material sparkMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        sparkMat.setTexture("Texture", assetManager.loadTexture("Effects/debris.png"));
        sparksEmitter.setMaterial(sparkMat);
        sparksEmitter.setImagesX(1);
        sparksEmitter.setImagesY(1);
        
        
    }
    
    @Override
    public void simpleInitApp() {
        initLight();
        
    }
}
