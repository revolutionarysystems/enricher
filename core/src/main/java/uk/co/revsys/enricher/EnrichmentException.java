package uk.co.revsys.enricher;

public class EnrichmentException extends Exception{

    public EnrichmentException(String message) {
        super(message);
    }

    public EnrichmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrichmentException(Throwable cause) {
        super(cause);
    }

}
