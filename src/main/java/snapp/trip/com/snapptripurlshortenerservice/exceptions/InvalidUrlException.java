package snapp.trip.com.snapptripurlshortenerservice.exceptions;

public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String message) {
        super(message);
    }
}
