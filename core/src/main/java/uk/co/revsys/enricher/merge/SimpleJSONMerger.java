package uk.co.revsys.enricher.merge;

import org.json.JSONObject;

public class SimpleJSONMerger implements JSONMerger{

    private String insertionPoint;

    public SimpleJSONMerger(String insertionPoint) {
        this.insertionPoint = insertionPoint;
    }
    
    @Override
    public JSONObject merge(JSONObject target, JSONObject merge) throws JSONMergeException{
        JSONObject enrichments = target.optJSONObject("enrichments");
        if(enrichments == null){
            enrichments = new JSONObject();
            target.put("enrichments", enrichments);
        }
        enrichments.put(insertionPoint, merge);
        return target;
    }

}
