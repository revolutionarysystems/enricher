package uk.co.revsys.enricher.condition;

import java.util.List;
import org.json.JSONObject;
import uk.co.revsys.jsont.JSONPathEvaluator;
import uk.co.revsys.jsont.jexl.JEXLJSONPathEvaluator;

public class JsonPathConditionEvaluator implements ConditionEvaluator {

    private JSONPathEvaluator jsonPathEvaluator = new JEXLJSONPathEvaluator();
    
    private List<String> conditions;

    public JsonPathConditionEvaluator(List<String> conditions) {
        this.conditions = conditions;
    }
    
    @Override
    public boolean evaluate(JSONObject message) throws ConditionEvaluationException {
        String json = message.toString();
        for(String condition: conditions){
            Object result = jsonPathEvaluator.evaluate(json, condition);
            if(result == null || result.equals(false)){
                return false;
            }
        }
        return true;
    }

}
