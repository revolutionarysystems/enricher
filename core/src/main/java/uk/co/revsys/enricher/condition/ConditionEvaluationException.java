package uk.co.revsys.enricher.condition;

import uk.co.revsys.enricher.EnrichmentException;

public class ConditionEvaluationException extends EnrichmentException{

    public ConditionEvaluationException(String message) {
        super(message);
    }

    public ConditionEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConditionEvaluationException(Throwable cause) {
        super(cause);
    }

}
