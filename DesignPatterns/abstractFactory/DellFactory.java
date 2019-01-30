package DesignPatterns.abstractFactory;

public class DellFactory implements PcFactory{

    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new DellKeyBoard();
    }
}
