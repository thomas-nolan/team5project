package io.github.team10.escapefromuni;

/**
 * NEW FOR ASSESSMENT 2.
 * Acts as the central controller for the logic of the game
 * Responsible for coordinating core system like the player controller,
 * room flow, events, doors, scoring, timing and achievements.
 */
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

  /**
   * NEW FOR ASSESSMENT 2.
   * Creates a new GameController and initialises the core game systems.
   *
   * @param game the main LibGDX game instance
   * @param uiController the UI controller used to manage screens
   */
  public GameController(EscapeGame game, UIController uiController) {

    this.game = game;
    this.uiController = uiController;

    // All the core gameplay systems
    this.timer = new Timer();
    this.scoreManager = new ScoreManager();
    this.achievementManager = new AchievementManager();
    this.eventSystem = new EventSystem();

    // Sets up the player as well as its controller
    this.player = new Player(3f, 1f, 1f, game);
    this.playerController = new PlayerController(game, player);

    // Sets up the room and door manager
    this.roomFlow = new RoomFlowManager(game, this.uiController, playerController,
        null, eventSystem, scoreManager, timer, achievementManager);
    this.doorController = new DoorController(game, playerController, roomFlow, eventSystem);
    this.roomFlow.setDoorController(doorController);

    // Initialises the map and all the rooms
    roomFlow.initialiseMap();
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Updates the games logic every frame
   *
   * @param delta the time elapsed between frames
   */
  public void update(float delta) {
    roomFlow.update(delta); // NEW FOR ASSESSMENT 2 - processes player input
    // NEW FOR ASSESSMENT 2 - checks if the player has touched a door and needs to change room
    doorController.update();
    eventSystem.update(delta); // NEW FOR ASSESSMENT 2 - checks if the event has been activated
    timer.update(delta); // NEW FOR ASSESSMENT 2 - updates the timer
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Draws all the world UI for the game
   */
  public void drawWorld() {
    roomFlow.drawCurrentRoom(); // NEW FOR ASSESSMENT 2 - draws the current room
    doorController.draw(); // NEW FOR ASSESSMENT 2 - draws the doors
    playerController.drawPlayer(); // NEW FOR ASSESSMENT 2 - draws the player
    eventSystem.drawWorld(); // NEW FOR ASSESSMENT 2 - draws the event
  }

  /**
   * NEW FOR ASSESSMENT 2.
   * Draws all the UI for each frame
   *
   * @param game the main LibGDX game instance
   */
  public void drawUI(EscapeGame game) {
    eventSystem.drawUI(); // renders the ui
    doorController.drawUI();
    game.font.draw(this.game.batch, "Time: "
        + timer.getTimeLeftSeconds()
        + "s", 75f, 1000f); // draws remaining time

    game.font.draw(this.game.batch,
        "Negative events: " + eventSystem.getTriggered(EventType.NEGATIVE)
        +  "/" + eventSystem.getMax(EventType.NEGATIVE),
        50f, 950f);

    game.font.draw(this.game.batch,
        "Positive events: " + eventSystem.getTriggered(EventType.POSITIVE)
        +  "/" + eventSystem.getMax(EventType.POSITIVE),
        50f, 910f);

    game.font.draw(this.game.batch,
        "Hidden events: " + eventSystem.getTriggered(EventType.HIDDEN)
        +  "/" + eventSystem.getMax(EventType.HIDDEN),
        50f, 870f);
  }

  /**
   * EXTENDED FROM ASSESSMENT 1.
   * Resets the game to its initial state, allowing the player to replay it.
   */
  public void resetGame() {
    // Dispose old resources
    roomFlow.dispose();
    doorController.dispose();
    player.dispose();

    // Reset timer and score
    timer.reset();
    scoreManager.reset();

    eventSystem.reset();

    // Recreate the player
    Player newPlayer = new Player(3f, 1f, 1f, game);
    this.player = newPlayer;
    this.playerController = new PlayerController(game, newPlayer);

    // Recreate door controller (important!)
    this.doorController = new DoorController(game, playerController, null, eventSystem);

    // Recreate room flow manager and connect it with the new doorController
    this.roomFlow = new RoomFlowManager(game, uiController, playerController,
        doorController, eventSystem, scoreManager, timer, achievementManager);
    roomFlow.setDoorController(doorController);
    doorController.setRoomFlowManager(roomFlow);

    // Initialise rooms and events
    roomFlow.initialiseMap();
  }

  /**
   * Frees up the memory used by the systems.
   */
  public void dispose() {
    roomFlow.dispose();
    doorController.dispose();
    playerController.dispose();
  }

  /**
   * Returns the timer object.
   *
   * @return the {@link Timer} object
   */
  public Timer getTimer() {
    return timer;
  }

  public ScoreManager getScoreManager() {
    return scoreManager;
  }

  public EventSystem getEventSystem() {
    return eventSystem;
  }

  public Player getPlayer() {
    return player;
  }

  public AchievementManager getAchievementManager() {
    return achievementManager;
  }

}
