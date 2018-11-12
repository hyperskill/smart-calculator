package repl;

/**
 * Created by Пользователь on 12.11.2018.
 */
public class Operand {
    private Character instance;
    private int priority;

    public Operand(char oper){
        this.instance = oper;
        switch (oper){
            case '+':
            case '-':
                this.priority = 1;
                break;
            case '*':
            case '/':
                this.priority = 2;
                break;
            case '^':
                this.priority = 3;
                break;
            default:
                this.priority  =0;
                break;
        }

    }

    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setInstance(char instance){
        this.instance = instance;
    }

    public Character getInstance(){
        return this.instance;
    }



}
