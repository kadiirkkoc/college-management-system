package system.colluagemanagement.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ManagementSystemServerException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 45487L;

    private HttpStatus errorCode;

    public ManagementSystemServerException(){
        super("Unexpected Exception Encountered");
    }

    public ManagementSystemServerException(String message, HttpStatus errorCode){
        super(message);
        this.errorCode=errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }
}
