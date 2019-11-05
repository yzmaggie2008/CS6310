package com.a710.cs6310.model.form;

public class State {
    private String GameState;
    private String Body;
    private String message;

    public String getGameState() {
        return GameState;
    }

    public void setGameState(String gameState) {
        GameState = gameState;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public State() {
        super();
    }

    public State(String gameState, String body, String message) {
        super();
        GameState = gameState;
        Body = body;
        this.message = message;
    }
}
