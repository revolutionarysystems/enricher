package uk.co.revsys.enricher;

import uk.co.revsys.enricher.transform.NoopTransformer;
import uk.co.revsys.enricher.merge.SimpleJSONMerger;
import uk.co.revsys.enricher.data.DataProvider;
import uk.co.revsys.enricher.condition.AllowAllConditionEvaluator;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.enricher.data.DataProviderException;

public class EnricherImplTest {

    IMocksControl mocksControl = EasyMock.createControl();

    public EnricherImplTest() {
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
    public void testEnrich() throws Exception {
        JSONObject data = new JSONObject();
        data.put("houseNumber", "45");
        data.put("postalCode", "SO15 3RT");
        DataProvider mockDataProvider = mocksControl.createMock(DataProvider.class);
        Enricher enricher = new EnricherImpl(new AllowAllConditionEvaluator(), new NoopTransformer(), mockDataProvider, new NoopTransformer(), new SimpleJSONMerger("address"));
        JSONObject addressData = new JSONObject();
        addressData.put("addressLine1", "45 Test Street");
        addressData.put("city", "London");
        expect(mockDataProvider.getData(data)).andReturn(addressData);
        mocksControl.replay();
        JSONObject result = enricher.enrich(data);
        mocksControl.verify();
        assertEquals("45", result.getString("houseNumber"));
        assertEquals("SO15 3RT", result.getString("postalCode"));
        JSONObject enrichments = result.getJSONObject("enrichments");
        JSONObject address = enrichments.getJSONObject("address");
        assertEquals("45 Test Street", address.getString("addressLine1"));
        assertEquals("London", address.getString("city"));
    }

    @Test
    public void testEnrichFailure() throws Exception {
        JSONObject data = new JSONObject();
        data.put("houseNumber", "45");
        data.put("postalCode", "SO15 3RT");
        DataProvider mockDataProvider = mocksControl.createMock(DataProvider.class);
        EnricherImpl enricher = new EnricherImpl(new AllowAllConditionEvaluator(), new NoopTransformer(), mockDataProvider, new NoopTransformer(), new SimpleJSONMerger("address"));
        JSONObject addressData = new JSONObject();
        addressData.put("addressLine1", "45 Test Street");
        addressData.put("city", "London");
        expect(mockDataProvider.getData(data)).andThrow(new DataProviderException("Failure"));
        mocksControl.replay();
        try {
            JSONObject result = enricher.enrich(data);
            fail("Expected DataProviderException to be thrown");
        } catch (DataProviderException ex) {
            mocksControl.verify();
        }
        mocksControl.reset();
        enricher.canFail(true);
        expect(mockDataProvider.getData(data)).andThrow(new DataProviderException("Failure"));
        mocksControl.replay();
        JSONObject result = enricher.enrich(data);
        mocksControl.verify();
        assertEquals("45", result.getString("houseNumber"));
        assertEquals("SO15 3RT", result.getString("postalCode"));
        assertNull(result.opt("enrichments"));
    }

}
