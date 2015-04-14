
package uk.co.revsys.enricher.merge;

import uk.co.revsys.enricher.merge.SimpleJSONMerger;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleJSONMergerTest {

    public SimpleJSONMergerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testMerge() throws Exception {
        JSONObject target = new JSONObject();
        target.put("key1", "value1");
        JSONObject merge = new JSONObject();
        merge.put("key2", "value2");
        SimpleJSONMerger merger = new SimpleJSONMerger("test");
        JSONObject result = merger.merge(target, merge);
        assertEquals("value1", result.getString("key1"));
        assertEquals("value2", result.getJSONObject("enrichments").getJSONObject("test").getString("key2"));
    }

}