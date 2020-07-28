package game;

import game.GameObjects.Vehicle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VehicleController implements KeyListener {

    private Vehicle vehicle;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;

    public VehicleController(Vehicle vehicle, int up, int down, int right, int left, int shoot) {
        this.vehicle = vehicle;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent key) {
        int keyPressed = key.getKeyCode();
        if (keyPressed == up) {
            this.vehicle.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.vehicle.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.vehicle.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.vehicle.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.vehicle.toggleShootPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        int keyReleased = key.getKeyCode();
        if (keyReleased == up) {
            this.vehicle.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.vehicle.unToggleDownPressed();
        }
        if (keyReleased == left) {
            this.vehicle.unToggleLeftPressed();
        }
        if (keyReleased == right) {
            this.vehicle.unToggleRightPressed();
        }
        if (keyReleased == shoot) {
            this.vehicle.unToggleShootPressed();
        }
    }
}
