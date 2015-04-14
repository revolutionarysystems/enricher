package uk.co.revsys.enricher.condition;

import org.json.JSONObject;

public class AllowAllConditionEvaluator implements ConditionEvaluator{

    @Override
    public boolean evaluate(JSONObject message) throws ConditionEvaluationException {
        return true;
    }

}
