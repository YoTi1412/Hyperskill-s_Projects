package server;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class InputArguments {

    @Parameter(names = {"-d"}, description = "Debugging mode flag", arity = 1)
    private boolean debug = false;

    public InputArguments(String[] args) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
    }

    public boolean getDebug() {
        return this.debug;
    }
}