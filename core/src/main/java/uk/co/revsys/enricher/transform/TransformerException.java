package uk.co.revsys.enricher.transform;

import uk.co.revsys.enricher.EnrichmentException;

public class TransformerException extends EnrichmentException{

    public TransformerException(String message) {
        super(message);
    }

    public TransformerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformerException(Throwable cause) {
        super(cause);
    }

}
