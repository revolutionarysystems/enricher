package uk.co.revsys.enricher.merge;

import org.json.JSONObject;

public interface JSONMerger {

    public JSONObject merge(JSONObject target, JSONObject merge) throws JSONMergeException;
    
}
