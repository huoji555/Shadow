package mediator;

public class ConcreateMeditor extends Mediator {

    ColleagueA colleagueA;
    ColleagueB colleagueB;


    public ColleagueA getColleagueA() { return colleagueA; }
    public void setColleagueA(ColleagueA colleagueA) { this.colleagueA = colleagueA; }

    public ColleagueB getColleagueB() { return colleagueB; }
    public void setColleagueB(ColleagueB colleagueB) { this.colleagueB = colleagueB; }


    //制定传输规则
    @Override
    public void contact(String msg, Colleague colleague) {
        if (colleague instanceof ColleagueA) {
            colleagueB.getMessage(msg);
        } else if (colleague instanceof ColleagueB) {
            colleagueA.getMessage(msg);
        }
    }
}
