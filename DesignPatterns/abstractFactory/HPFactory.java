package DesignPatterns.abstractFactory;

public class HPFactory implements PcFactory {
    @Override
    public Mouse createMouse() {
        return new HPMouse();
    }

    @Override
    public Keyboard createKeyboard() {
        return new HpKeyBoard();
    }
}
