package uk.co.revsys.enricher.config;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.revsys.enricher.Enricher;

public interface ConfigurationParser{

    public List<Enricher> parse(JSONArray config) throws ConfigurationParseException;
    
    public Enricher parse(JSONObject config) throws ConfigurationParseException;
    
}
