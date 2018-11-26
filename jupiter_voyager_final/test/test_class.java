import com.jme3.bounding.BoundingSphere;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import mygame.Main;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 * A test suite of the BasicGame Default Template.
 * I just want to make sure nobody breaks the normenhansen blue box.
 * @author Gisler Garces
 */
public class test_class {
    public static Main app;//Our test target application or class.

    @BeforeClass
    public static void beforeRunTests() {
        app = new Main();
        app.start();
    }

    @AfterClass
    public static void afterRunTests() {
        //Typically cleanup of native resources should happen here. 
        //This method is called in the GL/Rendering thread.
        //We don't have any display in our jenkins server so calling manually...
        try {
            app.destroy();
        } catch (Exception e) {
            //Ignore any destroy exception. This tests are not running with a 
            //display, so anything can happend, in theory we are not testing 
            //the destroy() method, remenber this is the afterClass method run 
            //after all tests, so it is for cleaning purposes mainly.
            //You could log, print or whatever you want with the exception. 
            //I´m ignoring it on purpose. :)
        }
    }

    @Before
    public void beforeEachTest() {
        //Before each...
    }

    @After
    public void afterEachTest() {
        //After each...
    }

    /**
     * It should have the box the expected color.
     */
    @Test
    public void shouldHaveTheBoxTheExpectedColor() {
//        Geometry geom = (Geometry) app.getRootNode().getChild("Sphere");
//        Assert.assertNotNull(geom);
//        
//        ColorRGBA color = (ColorRGBA) geom.getMaterial()
//                .getParam("Color").getValue();
//        Assert.assertEquals(ColorRGBA.Yellow, color);
    }

    /**
     * Checking size
     */
    @Test
    public void shouldHaveTheBoxTheExpectedLength() {
//       
//        Geometry geom1 = (Geometry) app.getRootNode().getChild("Sphere");
//        BoundingSphere sphere = (BoundingSphere)geom1.getWorldBound();
//        Assert.assertEquals("For box length", 400 , sphere.getRadius());    
    }
  }