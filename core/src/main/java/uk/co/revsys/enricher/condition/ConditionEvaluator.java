package uk.co.revsys.enricher.condition;

import org.json.JSONObject;

public interface ConditionEvaluator {

    public boolean evaluate(JSONObject message) throws ConditionEvaluationException;
    
}
