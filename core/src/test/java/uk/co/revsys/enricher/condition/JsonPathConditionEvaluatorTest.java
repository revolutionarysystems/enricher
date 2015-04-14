
package uk.co.revsys.enricher.condition;

import uk.co.revsys.enricher.condition.JsonPathConditionEvaluator;
import static com.oracle.nio.BufferSecrets.instance;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JsonPathConditionEvaluatorTest {

    public JsonPathConditionEvaluatorTest() {
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
    public void testEvaluate() throws Exception {
        JSONObject message = new JSONObject();
        message.put("key1", "value1");
        message.put("n1", 2);
        message.put("n2", 3);
        List<String> conditions = new LinkedList<String>();
        JsonPathConditionEvaluator conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(true, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("$.key1");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(true, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("$.key2");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(false, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("eval(v1 == 'value1', $.key1)");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(true, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("eval(v1 == 'value2', $.key1)");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(false, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("eval(v1 + v2 == 5, $.n1, $.n2)");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(true, conditionEvaluator.evaluate(message));
        conditions = new LinkedList<String>();
        conditions.add("eval(v1 == 'value1', $.key1)");
        conditions.add("eval(v1 + v2 == 5, $.n1, $.n2)");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(true, conditionEvaluator.evaluate(message));
        conditions.add("eval(v1 == 'value1', $.key1)");
        conditions.add("eval(v1 + v2 == 6, $.n1, $.n2)");
        conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        assertEquals(false, conditionEvaluator.evaluate(message));
    }

}