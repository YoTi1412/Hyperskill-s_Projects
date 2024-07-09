package server;

public class Constants {

    final static int POSITIVE = 1;

    final static int NEGATIVE = 0;

    final static int ERROR = -1;

    final static int ILLEGAL = -2;

    final static String SUCCESS_MESSAGE = "OK";

    final static String ERROR_MESSAGE = "ERROR";

    final static String RESPONSE = "response";

    final static String RESPONSE_VALUE = "value";

    final static String RESPONSE_REASON = "reason";

    final static String RESPONSE_REASON_NO_KEY = "No such key";

    final static String RESPONSE_REASON_ILLEGAL = "Invalid arguments";

    final static String RESPONSE_DATABASE_ERROR = "503 - something went wrong on server side";

    final static String ADDRESS = "127.0.0.1";

    final static int PORT = 22222;

    final static int BACKLOG = 50;

    final static String PATH_TO_DATA = ".src/server/data/db.json";

    final static int POOL_SIZE = 4;
}