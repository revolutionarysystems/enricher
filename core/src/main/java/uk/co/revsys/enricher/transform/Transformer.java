package uk.co.revsys.enricher.transform;

import org.json.JSONObject;

public interface Transformer {

    public JSONObject transform(JSONObject source) throws TransformerException;
    
}
