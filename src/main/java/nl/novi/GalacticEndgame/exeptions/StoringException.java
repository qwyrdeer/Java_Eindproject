package nl.novi.GalacticEndgame.exeptions;

import java.io.IOException;

public class StoringException extends RuntimeException {
    public StoringException(String message, IOException e) {
        super(message);
    }
}
