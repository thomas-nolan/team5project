package io.github.team10.escapefromuni;

public abstract class Event {
    public final EventType type;
    protected boolean eventFinished;
    protected Player player;
    protected EscapeGame game;

    public Event(EventType type, Player player, EscapeGame game)
    {
        this.type = type;
        this.player = player;
        this.game = game;
    }

    public abstract void startEvent();

    public abstract void endEvent();

    public abstract void update(float delta);

    public abstract void draw();

    public abstract void drawUI();
}
