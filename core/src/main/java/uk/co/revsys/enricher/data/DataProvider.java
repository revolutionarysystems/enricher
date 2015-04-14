package uk.co.revsys.enricher.data;

import org.json.JSONObject;

public interface DataProvider {

    public JSONObject getData(JSONObject input) throws DataProviderException;
    
}
