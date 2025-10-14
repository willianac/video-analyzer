package com.willianac.video_analyzer.exceptions;

public class SummaryException extends RuntimeException {

    private final SummaryErrorsEnum errorCode;

    public SummaryException(SummaryErrorsEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SummaryException(SummaryErrorsEnum errorCode, String details) {
        super(details != null ? details : errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SummaryErrorsEnum getErrorCode() {
        return errorCode;
    }
    
}
