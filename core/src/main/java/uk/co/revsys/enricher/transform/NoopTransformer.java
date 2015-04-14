package uk.co.revsys.enricher.transform;

import org.json.JSONObject;

public class NoopTransformer implements Transformer{

    @Override
    public JSONObject transform(JSONObject source) throws TransformerException {
        return source;
    }

}
