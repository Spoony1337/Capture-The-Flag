package play.mickedplay.gameapi.game;

public interface GameStage {

    int getDuration();

    void onStart();

    void onRun();

    void onEnd();
}