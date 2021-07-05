package net.launcher.components;

public class GameThread extends Thread {
    public GameThread(Runnable target) {
        super(target);
    }
}
