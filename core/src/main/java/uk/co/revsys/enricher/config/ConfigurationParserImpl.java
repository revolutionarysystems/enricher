package uk.co.revsys.enricher.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.enricher.condition.AllowAllConditionEvaluator;
import uk.co.revsys.enricher.condition.ConditionEvaluator;
import uk.co.revsys.enricher.data.DataProvider;
import uk.co.revsys.enricher.DelegatingEnricher;
import uk.co.revsys.enricher.Enricher;
import uk.co.revsys.enricher.EnricherImpl;
import uk.co.revsys.enricher.transform.JSONTTransformer;
import uk.co.revsys.enricher.condition.JsonPathConditionEvaluator;
import uk.co.revsys.enricher.transform.NoopTransformer;
import uk.co.revsys.enricher.merge.SimpleJSONMerger;
import uk.co.revsys.enricher.transform.Transformer;

public class ConfigurationParserImpl extends AbstractConfigurationParser {

    private Map<String, DataProvider> dataProviders;

    public ConfigurationParserImpl(Map<String, DataProvider> dataProviders) {
        this.dataProviders = dataProviders;
    }

    @Override
    public Enricher parse(JSONObject config) throws ConfigurationParseException {
        ConditionEvaluator conditionEvaluator = parseConditions(config.getJSONArray("conditions"));
        List<Enricher> enrichers = parseEnrichments(config.getJSONArray("enrichments"));
        DelegatingEnricher delegatingEnricher = new DelegatingEnricher(conditionEvaluator, enrichers);
        return delegatingEnricher;
    }

    private ConditionEvaluator parseConditions(JSONArray config) {
        List<String> conditions = new LinkedList<String>();
        for (int i = 0; i < config.length(); i++) {
            String condition = config.getString(i);
            conditions.add(condition);
        }
        ConditionEvaluator conditionEvaluator = new JsonPathConditionEvaluator(conditions);
        return conditionEvaluator;
    }

    private List<Enricher> parseEnrichments(JSONArray config) {
        List<Enricher> enrichers = new LinkedList<Enricher>();
        for (int i = 0; i < config.length(); i++) {
            enrichers.add(parseEnrichment(config.getJSONObject(i)));
        }
        return enrichers;
    }

    private Enricher parseEnrichment(JSONObject config) {
        String dataProviderId = config.getString("dataProvider");
        Transformer inTransformer;
        if (config.has("inTransform")) {
            inTransformer = new JSONTTransformer(config.getJSONObject("inTransform"));
        } else {
            inTransformer = new NoopTransformer();
        }
        DataProvider dataProvider = dataProviders.get(dataProviderId);
        Transformer outTransformer;
        if (config.has("outTransform")) {
            outTransformer = new JSONTTransformer(config.getJSONObject("outTransform"));
        } else {
            outTransformer = new NoopTransformer();
        }
        EnricherImpl enricher = new EnricherImpl(new AllowAllConditionEvaluator(), inTransformer, dataProvider, outTransformer, new SimpleJSONMerger(dataProviderId));
        enricher.canFail(config.optBoolean("canFail", false));
        return enricher;
    }

}
