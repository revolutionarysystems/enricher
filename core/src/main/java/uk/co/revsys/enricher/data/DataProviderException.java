package uk.co.revsys.enricher.data;

import uk.co.revsys.enricher.EnrichmentException;

public class DataProviderException extends EnrichmentException{

    public DataProviderException(String message) {
        super(message);
    }

    public DataProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProviderException(Throwable cause) {
        super(cause);
    }

}
