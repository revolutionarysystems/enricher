package uk.co.revsys.enricher;

import uk.co.revsys.enricher.transform.Transformer;
import uk.co.revsys.enricher.merge.JSONMerger;
import uk.co.revsys.enricher.data.DataProvider;
import uk.co.revsys.enricher.condition.ConditionEvaluator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnricherImpl implements Enricher {

    private static Logger LOG = LoggerFactory.getLogger(EnricherImpl.class);
    
    private ConditionEvaluator conditionEvaluator;
    private Transformer inTransformer;
    private DataProvider dataProvider;
    private Transformer outTransformer;
    private JSONMerger jsonMerger;

    private boolean canFail = false;

    public EnricherImpl(ConditionEvaluator conditionEvaluator, Transformer inTransformer, DataProvider dataProvider, Transformer outTransformer, JSONMerger jsonMerger) {
        this.conditionEvaluator = conditionEvaluator;
        this.inTransformer = inTransformer;
        this.dataProvider = dataProvider;
        this.outTransformer = outTransformer;
        this.jsonMerger = jsonMerger;
    }

    @Override
    public JSONObject enrich(JSONObject message) throws EnrichmentException {
        try {
            if (conditionEvaluator.evaluate(message)) {
                JSONObject transformedMessage = inTransformer.transform(message);
                JSONObject newData = dataProvider.getData(transformedMessage);
                JSONObject transformedData = outTransformer.transform(newData);
                JSONObject result = jsonMerger.merge(message, transformedData);
                return result;
            } else {
                return message;
            }
        } catch (EnrichmentException ex) {
            if(canFail()){
                LOG.error("Enrichment failed and ignored", ex);
                return message;
            }else{
                throw ex;
            }
        }
    }

    public boolean canFail() {
        return canFail;
    }

    public void canFail(boolean canFail) {
        this.canFail = canFail;
    }

}
