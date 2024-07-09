package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class InputArguments {

    @Parameter(names = {"-t"}, description = "Type of a request")
    private String type;

    @Parameter(names = {"-k"}, description = "Key to be accessed")
    private String key;

    @Parameter(names = {"-v"}, description = "Value to be set")
    private String value;

    @Parameter(names = {"-in"}, description = "Name of the request input file")
    private String fileName = null;

    public InputArguments(String[] args) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);
    }

    public String parseIntoJson() {
        if (!Objects.equals(fileName, null)) {
            try {
                return new String(Files.readAllBytes(Paths.get(Constants.PATH_TO_DATA + fileName)));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Map<String, String> map = new LinkedHashMap<>();
            Gson gson = new Gson();
            return switch (type) {
                case "set" -> {
                    map.put("type", type);
                    map.put("key", key);
                    map.put("value", value);
                    yield gson.toJson(map);
                }
                case "get", "delete" -> {
                    map.put("type", type);
                    map.put("key", key);
                    yield gson.toJson(map);
                }
                case "exit" -> {
                    map.put("type", type);
                    yield gson.toJson(map);
                }
                default -> null;
            };
        }
    }
}