package uk.co.revsys.enricher;

import org.json.JSONObject;

public interface Enricher {

    public JSONObject enrich(JSONObject data) throws EnrichmentException;
    
}
