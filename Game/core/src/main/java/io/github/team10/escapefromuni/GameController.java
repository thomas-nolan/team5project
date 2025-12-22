package io.github.team10.escapefromuni;

public class GameController {
    private final EscapeGame game;
    private final UIController uiController;

    private Player player;
    private PlayerController playerController;
    private final ScoreManager scoreManager;
    private final Timer timer;

    private final EventSystem eventSystem;
    private RoomFlowManager roomFlow;
    private DoorController doorController;
    private final AchievementManager achievementManager;

    public GameController(EscapeGame game, UIController uiController){

        this.game = game;
        this.uiController = uiController;
        this.timer = new Timer();
        this.scoreManager = new ScoreManager();
        this.player = new Player(3f, 1f, 1f, game);
        this.achievementManager = new AchievementManager();
        this.playerController = new PlayerController(game, player);
        this.eventSystem = new EventSystem();
        this.roomFlow = new RoomFlowManager(game, this.uiController, playerController, null, eventSystem, scoreManager, timer, achievementManager);
        this.doorController = new DoorController(game, playerController, roomFlow);
        this.roomFlow.setDoorController(doorController);

        roomFlow.initialiseMap();
    }

    public void update(float delta){
        roomFlow.update(delta); // processes player input
        doorController.update(); // checks if the player has touched a door and needs to change room
        eventSystem.update(delta); // checks if the event has been activated
        timer.update(delta); // updates the timer
    }

    public void drawWorld() {
        roomFlow.drawCurrentRoom(); // draws the current room
        doorController.draw(); // draws the doors
        playerController.drawPlayer(); // draws the player
        eventSystem.drawWorld(); // draws the event
    }

    public void drawUI(EscapeGame game){
        eventSystem.drawUI(); // renders the ui
        doorController.drawUI();
        game.font.draw(this.game.batch, "Time: " + timer.getTimeLeftSeconds() + "s", 75f, 1000f); // draws remaining time
    }
    public void resetGame(){
        // Dispose old resources
        roomFlow.dispose();
        doorController.dispose();
        player.dispose();

        // Reset timer and score
        timer.reset();
        scoreManager.reset();

        // Recreate the player
        Player newPlayer = new Player(3f, 1f, 1f, game);
        this.player = newPlayer;
        this.playerController = new PlayerController(game, newPlayer);

        // Recreate door controller (important!)
        this.doorController = new DoorController(game, playerController, null);

        // Recreate room flow manager and connect it with the new doorController
        this.roomFlow = new RoomFlowManager(game, uiController, playerController, doorController, eventSystem, scoreManager, timer, achievementManager);
        roomFlow.setDoorController(doorController);
        doorController.setRoomFlowManager(roomFlow);

        // Initialise rooms and events
        roomFlow.initialiseMap();
}

    public void dispose() {
        roomFlow.dispose();
        doorController.dispose();
        playerController.dispose();
    }

    public Timer getTimer() {
        return timer;
    }

    public ScoreManager getScoreManager(){
        return scoreManager;
    }

    public EventSystem getEventSystem(){
        return eventSystem;
    }

    public Player getPlayer(){
        return player;
    }
    public AchievementManager getAchievementManager(){
        return achievementManager;
    }

}
