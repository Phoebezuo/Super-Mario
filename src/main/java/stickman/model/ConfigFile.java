package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigFile {

    private static ConfigFile configFile = null;

    private List<String> levelFileNames = new ArrayList<>();
    private double heroLives = 0;

    @SuppressWarnings("unchecked")
    private ConfigFile(String config) {
        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader(config);
            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray levelFiles = (JSONArray) object.get("levelFiles");
            Iterator<String> iterator = (Iterator<String>) levelFiles.iterator();

            // Get level file names
            while (iterator.hasNext()) {
                String file = iterator.next();
                levelFileNames.add("levels/" + file);
            }

            heroLives = (double) object.get("stickmanLives");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static ConfigFile getConfigFile(String config) {
        if (configFile == null) {
            configFile = new ConfigFile(config);
        }
        return configFile;
    }

    public List<String> getLevelFileNames() {
        return levelFileNames;
    }

    public double getHeroLives() {
        return heroLives;
    }
}
