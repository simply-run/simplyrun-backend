package com.simpllyrun.srcservice.global.error;

public class SrcException extends RuntimeException{

        private ErrorCode errorCode;

        public SrcException(String message, ErrorCode errorCode) {
            super(message);
            this.errorCode = errorCode;
        }

        public SrcException(ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }
}
