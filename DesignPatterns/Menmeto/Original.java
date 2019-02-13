package Menmeto;

public class Original {

    private String state;

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Menmeto createMenmeto(){
        return new Menmeto(state);
    }

    public void setMenmeto(Menmeto menmeto) {
        state = menmeto.getState();
    }

    public void showState() {
        System.out.println(state);
    }

}
