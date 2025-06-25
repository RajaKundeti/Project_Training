package tradeprocessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ConfigLoader {
    public static Set<String> loadValidExchanges(String filePath) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(filePath));
        String exchanges = props.getProperty("valid.exchanges", "");
        return new HashSet<>(Arrays.asList(exchanges.split(",")));
    }
}