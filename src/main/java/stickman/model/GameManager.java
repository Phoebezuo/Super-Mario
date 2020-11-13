package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.level.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of GameEngine. Manages the running of the game.
 */
public class GameManager implements GameEngine {

    /**
     * The current level
     */
    private Level level;

    /**
     * List of all level files
     */
    private List<String> levelFileNames;

    private int currentLevel = 0;
    private double heroLives;
    private int tick = 0;
    private int currentScore = 0;
    private int prevScore = 0;

    /**
     * Creates a GameManager object.
     * @param levels The config file containing the names of all the levels
     */
    public GameManager(String levels) {
        this.levelFileNames = this.readConfigFile(levels);
        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(currentLevel), this);
    }

    public int getTick() {
        return tick;
    }

    public void updateTick() {
        tick++;
    }

    @Override
    public Level getCurrentLevel() {
        return this.level;
    }

    @Override
    public boolean jump() {
        return this.level.jump();
    }

    @Override
    public boolean moveLeft() {
        return this.level.moveLeft();
    }

    @Override
    public boolean moveRight() {
        return this.level.moveRight();
    }

    @Override
    public boolean stopMoving() {
        return this.level.stopMoving();
    }

    @Override
    public void tick() {
        this.level.tick();
    }

    @Override
    public void shoot() {
        this.level.shoot();
    }

    public int getLevel() {
        return currentLevel + 1;
    }

    public void nextLevel() {
        this.currentLevel++;
        if (currentLevel >= levelFileNames.size())  {
            ((LevelManager) level).setWon(true);
            return;
        }
        prevScore += currentScore;
        currentScore = 0;
        tick = 0;
        this.level = LevelBuilderImpl.generateFromFile(levelFileNames.get(currentLevel), this);
    }

    public void decreaseLives() {
        heroLives--;
    }

    public double getLives() {
        return heroLives;
    }

    public void addCurrentScore(int value) {
        currentScore += value;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getPrevScore() {
        return prevScore;
    }


    /**
     * Retrieves the list of level filenames from a config file
     * @param config The config file
     * @return The list of level names
     */
    @SuppressWarnings("unchecked")
    private List<String> readConfigFile(String config) {

        List<String> res = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader(config);
            JSONObject object = (JSONObject) parser.parse(reader);

            JSONArray levelFiles = (JSONArray) object.get("levelFiles");
            Iterator<String> iterator = (Iterator<String>) levelFiles.iterator();
            // Get level file names
            while (iterator.hasNext()) {
                String file = iterator.next();
                res.add("levels/" + file);
            }

            // TODO
            heroLives = (double) object.get("stickmanLives");
            System.out.printf("lives: %f\n", heroLives);

        } catch (IOException e) {
            System.exit(10);
            return null;
        } catch (ParseException e) {
            return null;
        }

        return res;
    }

}