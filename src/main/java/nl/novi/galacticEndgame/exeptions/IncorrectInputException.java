package nl.novi.galacticEndgame.exeptions;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String message) {
        super(message);
    }
}