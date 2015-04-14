package uk.co.revsys.enricher.transform;

import java.util.HashMap;
import org.json.JSONObject;
import uk.co.revsys.jsont.JSONPathEvaluator;
import uk.co.revsys.jsont.JSONTransformer;

public class JSONTTransformer implements Transformer{

    private JSONTransformer transformer = new JSONTransformer(new JSONPathEvaluator());
    private String transform;

    public JSONTTransformer(JSONObject transform) {
        this.transform = transform.toString();
    }
    
    @Override
    public JSONObject transform(JSONObject source) throws TransformerException {
        String result = transformer.transform(source.toString(), transform, new HashMap());
        return new JSONObject(result);
    }

}
