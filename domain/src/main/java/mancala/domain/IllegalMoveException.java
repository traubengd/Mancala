package mancala.domain;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String message) {
        super("That is an illegal move: " + message);
    }
}