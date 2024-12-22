package com.game.event_handler;

import java.awt.*;

public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone;

    public EventRect() {}

    public EventRect(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
