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

/**
 * Manage the configuration of overall application
 */
public class ConfigFile {

    /**
     * The private instance that save all the information
     */
    private static ConfigFile configFile = null;

    /**
     * A list of filenames coresponding to different levels
     */
    private List<String> levelFileNames = new ArrayList<>();

    /**
     * The number of lives that hero will have
     */
    private double heroLives = 0;

    /**
     * Constructor to create ConfigFile object
     * @param config private instance that contains configuration information of whole game
     */
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

    /**
     * Get the ConfigFile object instance
     * @param config filename of overall application
     * @return the private ConfigFile instance
     */
    public static ConfigFile getConfigFile(String config) {
        if (configFile == null) {
            configFile = new ConfigFile(config);
        }
        return configFile;
    }

    /**
     * Get a list of level configuration filenames
     * @return list of level configuration filenames
     */
    public List<String> getLevelFileNames() {
        return levelFileNames;
    }

    /**
     * Get the hero lives which is set from configuration file
     * @return hero lives
     */
    public double getHeroLives() {
        return heroLives;
    }
}
