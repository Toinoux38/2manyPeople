package com.citybuilder.modelBis.purchasable;

import com.citybuilder.modelBis.events.GameEvent;

import java.util.concurrent.SubmissionPublisher;

public abstract class Purchasable extends SubmissionPublisher<GameEvent> {
    protected String name;
    protected int price;

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void destroy() {
        //this.submit();
    }
}
