package ra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception{
    private HttpStatus httpStatus;;
    public CustomException(String message,HttpStatus status) {
        super(message); // ke thu lop cha
        this.httpStatus = status;
    }

}
