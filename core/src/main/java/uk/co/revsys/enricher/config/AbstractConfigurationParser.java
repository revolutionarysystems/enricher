package uk.co.revsys.enricher.config;

import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import uk.co.revsys.enricher.Enricher;

public abstract class AbstractConfigurationParser implements ConfigurationParser{

    @Override
    public List<Enricher> parse(JSONArray config) throws ConfigurationParseException {
        List<Enricher> enrichers = new LinkedList<Enricher>();
        for(int i=0; i<config.length(); i++){
            enrichers.add(parse(config.getJSONObject(i)));
        }
        return enrichers;
    }

}
