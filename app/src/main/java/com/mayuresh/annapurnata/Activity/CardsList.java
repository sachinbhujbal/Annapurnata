package com.mayuresh.annapurnata.Activity;

public class CardsList {

    private final String id;
    private final int winAmount;
    // when user scratch a card then this value will be true so user won't scan same card again
    private boolean scratchStatus;

    public CardsList(String id, int winAmount, boolean scratchStatus) {
        this.id = id;
        this.winAmount = winAmount;
        this.scratchStatus = scratchStatus;
    }

    public String getId() {
        return id;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public boolean isScratchStatus() {
        return scratchStatus;
    }

    public void setScratchStatus(boolean cardScratched){
        this.scratchStatus=cardScratched;

    }
}
