package uk.co.revsys.enricher.merge;

import uk.co.revsys.enricher.EnrichmentException;

public class JSONMergeException extends EnrichmentException{

    public JSONMergeException(String message) {
        super(message);
    }

    public JSONMergeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONMergeException(Throwable cause) {
        super(cause);
    }

}
