
package uk.co.revsys.enricher.config;

import uk.co.revsys.enricher.data.DataProvider;
import uk.co.revsys.enricher.config.ConfigurationParserImpl;
import java.util.HashMap;
import java.util.Map;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.revsys.enricher.DelegatingEnricher;
import uk.co.revsys.enricher.EnricherImpl;
import static org.junit.Assert.*;

public class ConfigurationParserImplTest {

    public ConfigurationParserImplTest() {
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
    public void testParse() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        JSONObject config = new JSONObject();
        JSONArray conditions = new JSONArray();
        conditions.put("$.key1");
        config.put("conditions", conditions);
        JSONArray enrichments = new JSONArray();
        JSONObject enrichment1 = new JSONObject();
        enrichment1.put("dataProvider", "mock");
        enrichment1.put("canFail", true);
        enrichments.put(enrichment1);
        config.put("enrichments", enrichments);
        DataProvider mockDataProvider = mocksControl.createMock(DataProvider.class);
        Map<String, DataProvider> dataProviders = new HashMap<String, DataProvider>();
        dataProviders.put("mock", mockDataProvider);
        ConfigurationParserImpl parser = new ConfigurationParserImpl(dataProviders);
        DelegatingEnricher result = (DelegatingEnricher) parser.parse(config);
        assertEquals(1, result.getEnrichers().size());
        assertEquals(true, ((EnricherImpl)result.getEnrichers().get(0)).canFail());
    }

}