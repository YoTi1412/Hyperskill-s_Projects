package contacts;

import java.util.Optional;

public class Args {
    private final String[] args;

    public Args(String[] args) {
        this.args = args;
    }

    public Optional<String> get(String key) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(key)) {
                return Optional.of(args[i + 1]);
            }
        }
        return Optional.empty();
    }
}
