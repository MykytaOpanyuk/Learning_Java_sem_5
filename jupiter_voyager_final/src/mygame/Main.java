package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Cylinder;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.scene.Spatial;
import com.jme3.util.SkyFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;

public class Main extends SimpleApplication {
    public Geometry voyager, jupiter, sun_spatial;
    public Vector3f voyager_start_pos      = new Vector3f(-900, -3, 1000);
    public Vector3f voyager_speed          = new Vector3f(40, 0, -40);
    public Vector3f jupiter_pos            = new Vector3f(0, 0, 0);
    public Vector3f jupiter_speed          = new Vector3f(40, 40, 40);
    public float    jupiter_mass           = 1000000;
    public Vector3f sun_pos                = new Vector3f(-8000, 0, 8000);
    
    public float time                      = 0;
    public Geometry temp_geom, point;  // used for trajectory
    public BitmapText speed_text;      // to show voyager Speed value
    public BitmapText info_text;       // ibfo about pause key
    public Vector3f tempVec                = new Vector3f(0,0,0);
    
    boolean paused = false;     // to switch between views
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    @Override
    public void simpleUpdate(float tpf) {        
        if (paused)
            return;
        
        tpf = tpf * 2;
        time = time + tpf;
        
        // build trajectory by copying "tempsphere"-point and using pos of voyager
        if (time > 0.5) {
            time = 0;
            
            temp_geom = point.clone();
            temp_geom.getMaterial().setColor("Color", ColorRGBA.Yellow);
            rootNode.attachChild(temp_geom);
            temp_geom.setLocalTranslation(voyager.getLocalTranslation());
        }
        
        // moving planet 
        jupiter_pos = (jupiter_pos.add(jupiter_speed.mult(tpf)).subtract(sun_pos).normalize().mult(jupiter_pos.subtract(sun_pos).length())).add(sun_pos);
        jupiter.setLocalTranslation(jupiter_pos);
        jupiter.rotate(0, 0, -0.004f);
        
        // moving voyager
        voyager.move(voyager_speed.x * tpf, voyager_speed.y * tpf, voyager_speed.z * tpf);
        tempVec = voyager.getWorldTranslation();
        
        // camera
        cam.setLocation(jupiter_pos.subtract(cam.getDirection().mult(10000))); // fixed look at planet
        tempVec = tempVec.subtract(jupiter_pos);

        voyager_speed.addLocal(tempVec.normalize().mult((-tpf * 6.67f * jupiter_mass)/
                (tempVec.length() * tempVec.length()))); // changing voyager speed 

        // gui update
        speed_text.setText("Speed relative to the sun : " + Float.toString(voyager_speed.length()));
    }
    
    @Override
    public void simpleInitApp() {
        Material material;
        
        initKeys();

        // adding text nodes
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        speed_text = new BitmapText(guiFont, false);
        speed_text.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        speed_text.setLocalTranslation(400, settings.getHeight() - 2 * speed_text.getLineHeight(), 180);
        guiNode.attachChild(speed_text);
        
        BitmapText hintText = new BitmapText(guiFont, false);
        hintText.setText("press\n"
                + "SPACE to pause");
        hintText.setSize(guiFont.getCharSet().getRenderedSize());
        hintText.setLocalTranslation(settings.getWidth() - 270, 110, 0);
        guiNode.attachChild(hintText);
 
        // add skyBox
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(), 
                "Textures/Space.dds", SkyFactory.EnvMapType.CubeMap));
        
        // jupiter
        Sphere jupiter_sphere = new Sphere(100, 100, 500);
        jupiter = new Geometry("Sphere", jupiter_sphere);
        material = new Material(assetManager, 
                "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", 
                assetManager.loadTexture("Textures/Jupiter.jpg"));
        material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        jupiter.setQueueBucket(RenderQueue.Bucket.Transparent);
        jupiter.setMaterial(material);
        jupiter.rotate(180, 0, 0);
        jupiter.move(jupiter_pos);
        jupiter.setShadowMode(RenderQueue.ShadowMode.CastAndReceive); 
        rootNode.attachChild(jupiter);
        
        // voyager
        Cylinder cyl = new Cylinder(10, 10, 5, 5, true);
        voyager = new Geometry("Cylinder", cyl);
        material  = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap",assetManager.loadTexture("Textures/metal.jpg"));
        voyager.setMaterial(material);
        voyager.move(voyager_start_pos);
        voyager.scale(10);
        rootNode.attachChild(voyager);
        
        // sphere sun
        Sphere sun_sphere = new Sphere(150, 150, 400);
        sun_spatial = new Geometry("Sphere", sun_sphere);
        sun_spatial.move(sun_pos);
        sun_spatial.setCullHint(Spatial.CullHint.Never);
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Yellow);
        sun_spatial.setMaterial(material);
        rootNode.attachChild(sun_spatial);
        cam.setFrustumFar(120000);
        
        //light sun
        PointLight sun = new PointLight();
        sun.setPosition(sun_pos);
        AmbientLight in_shadow = new AmbientLight();
        in_shadow.setColor(ColorRGBA.Black);
        sun.setColor(ColorRGBA.White.mult(1f));
        rootNode.addLight(sun);
        rootNode.addLight(in_shadow);
      
        // tempsphere
        point = new Geometry("Sphere", new Sphere(10, 10, 25));
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color",ColorRGBA.Yellow);
        point.setMaterial(material);
        point.setLocalTranslation(voyager.getLocalTranslation());      
    }
   
    private void initKeys() {
        inputManager.addMapping("Pause",    new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Pause");
    }
     
    private ActionListener actionListener = new ActionListener() {
      	@Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) 
                return;
            else if (name.equals("Pause"))
                paused = !paused;    
        } 
    };
    
}
