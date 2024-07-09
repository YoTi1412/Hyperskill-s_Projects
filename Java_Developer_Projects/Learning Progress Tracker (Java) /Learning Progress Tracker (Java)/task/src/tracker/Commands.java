package tracker;

public enum Commands {
    EXIT("exit", new Exit()),
    ADDSTUDENT("add students", new AddStudent()),
    LIST("list", new ListC()),
    ADDPOINTS("add points", new AddPoints()),
    FIND("find", new Find()),
    STATISTICS("statistics", new Statistics()),
    NOTIFY("notify", new Notify())
    ;

    String strCommand;
    Command command;
    Commands(String strCommand, Command classCommand){
        this.strCommand =strCommand;
        this.command = classCommand;

    }


}