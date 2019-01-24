package Nature.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ReadPorperties {

    public static String getParams(String key) {
        InputStream in = ReadPorperties.class.getClassLoader().getResourceAsStream("Nature/Config.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Properties props = new Properties();

        try {
            props.load(br);
            return props.get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
