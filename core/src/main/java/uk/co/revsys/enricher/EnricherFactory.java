package uk.co.revsys.enricher;

public interface EnricherFactory {

    public Enricher getEnricher(String id);
    
}
