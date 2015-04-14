package uk.co.revsys.enricher;

import java.util.Collections;
import uk.co.revsys.enricher.condition.ConditionEvaluator;
import uk.co.revsys.enricher.condition.ConditionEvaluationException;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegatingEnricher implements Enricher {

    private static Logger LOG = LoggerFactory.getLogger(DelegatingEnricher.class);
    
    private ConditionEvaluator conditionEvaluator;
    private List<Enricher> enrichers;

    public DelegatingEnricher(ConditionEvaluator conditionEvaluator, List<Enricher> enrichers) {
        this.conditionEvaluator = conditionEvaluator;
        this.enrichers = enrichers;
    }

    @Override
    public JSONObject enrich(JSONObject message) throws EnrichmentException {
        try {
            if (conditionEvaluator.evaluate(message)) {
                for (Enricher enricher : enrichers) {
                    message = enricher.enrich(message);
                }
                return message;
            } else {
                return message;
            }
        } catch (ConditionEvaluationException ex) {
            LOG.error("Failed to evaluate conditions", ex);
            return message;
        }
    }

    public List<Enricher> getEnrichers() {
        return Collections.unmodifiableList(enrichers);
    }

}
