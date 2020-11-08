package stickman.level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.entity.Entity;
import stickman.entity.Interactable;
import stickman.entity.moving.MovingEntity;
import stickman.entity.moving.enemy.DumbStrategy;
import stickman.entity.moving.enemy.EnemyStrategy;
import stickman.entity.moving.enemy.FollowStrategy;
import stickman.entity.moving.enemy.Slime;
import stickman.entity.still.Flag;
import stickman.entity.still.Mushroom;
import stickman.entity.still.Platform;
import stickman.model.GameEngine;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Concrete implementation of the LevelBuilder interface.
 */
public class LevelBuilderImpl implements LevelBuilder {

    /**
     * The default floor height.
     */
    private static final double DEFAULT_FLOOR_HEIGHT = 300;

    /**
     * The default level width.
     */
    private static final double DEFAULT_LEVEL_WIDTH = 1000;

    /**
     * The default level height.
     */
    private static final double DEFAULT_LEVEL_HEIGHT = 500;

    /**
     * The list of moving entities.
     */
    private List<MovingEntity> movingEntities;

    /**
     * The list of static entities.
     */
    private List<Entity> staticEntities;

    /**
     * The list of interactable entities.
     */
    private List<Interactable> interactables;

    /**
     * The source file for the level.
     */
    private String file;

    /**
     * The starting x-coordinate for the hero.
     */
    private double heroX;

    /**
     * The size of the hero.
     */
    private String heroSize;

    /**
     * The width of the level.
     */
    private double width;

    /**
     * The height of the level.
     */
    private double height;

    /**
     * The height of the floor in the level.
     */
    private double floorHeight;

    /**
     * The GameEngine the level exists within.
     */
    private GameEngine model;

    /**
     * Creates a new LevelBuilderImpl object.
     * @param file The source file for the level
     * @param model The GameEngine the level is part of.
     */
    public LevelBuilderImpl(String file, GameEngine model) {
        this.file = file;
        this.model = model;

        this.movingEntities = new ArrayList<>();
        this.staticEntities = new ArrayList<>();
        this.interactables = new ArrayList<>();

        this.floorHeight = DEFAULT_FLOOR_HEIGHT;
        this.width = DEFAULT_LEVEL_WIDTH;
        this.height = DEFAULT_LEVEL_HEIGHT;
    }

    @Override
    public LevelBuilder addEnemy(MovingEntity enemy) {
        this.movingEntities.add(enemy);
        return this;
    }

    @Override
    public LevelBuilder addStaticEntity(Entity staticEntity) {
        this.staticEntities.add(staticEntity);
        return this;
    }

    @Override
    public LevelBuilder addInteractable(Interactable collectable) {
        this.interactables.add(collectable);
        return this;
    }

    @Override
    public LevelBuilder setHero(double x, String size) {
        this.heroX = x;
        this.heroSize = size;
        return this;
    }

    @Override
    public LevelBuilder setFloorHeight(double height) {
        this.floorHeight = height;
        return this;
    }

    @Override
    public LevelBuilder setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public Level build() {
        return new LevelManager(model, file, height, width, floorHeight, heroX, heroSize, staticEntities, movingEntities, interactables);
    }

    /**
     * Reads a json file and generates a Level object from it.
     * @param file The JSON file to read
     * @return The corresponding Level object
     */
    @SuppressWarnings("unchecked")
    public static Level generateFromFile(String file, GameEngine model) {

        LevelBuilder levelBuilder = new LevelBuilderImpl(file, model);

        JSONParser parser = new JSONParser();

        try {

            Reader reader = new FileReader(file);

            JSONObject object = (JSONObject) parser.parse(reader);

            String size = (String) object.get("stickmanSize");
            JSONObject pos = (JSONObject) object.get("stickmanPos");
            double heroX = (double) pos.get("x");

            levelBuilder.setHero(heroX, size);

            JSONObject levelDimensions = (JSONObject) object.get("levelDimensions");

            double width = (double) levelDimensions.get("width");
            double height = (double) levelDimensions.get("height");
            double floorHeight = (double) levelDimensions.get("floorHeight");

            levelBuilder.setDimensions(width, height);
            levelBuilder.setFloorHeight(floorHeight);

            JSONArray platforms = (JSONArray) object.get("platforms");

            Iterator<JSONObject> iterator = (Iterator<JSONObject>) platforms.iterator();

            // Get platforms
            while (iterator.hasNext()) {
                JSONObject plat = (JSONObject) iterator.next();

                double x = (double) plat.get("x");
                double y = (double) plat.get("y");

                levelBuilder.addStaticEntity(new Platform(x, y));
            }

            JSONArray mushrooms = (JSONArray) object.get("mushrooms");

            iterator = (Iterator<JSONObject>) mushrooms.iterator();

            // Get mushrooms
            while (iterator.hasNext()) {
                JSONObject mush = (JSONObject) iterator.next();

                double x = (double) mush.get("x");
                double y = (double) mush.get("y");

                Mushroom shroom = new Mushroom(x, y);

                levelBuilder.addInteractable(shroom);
                levelBuilder.addStaticEntity(shroom);
            }

            // Get enemies
            JSONArray enemies = (JSONArray) object.get("enemies");

            iterator = (Iterator<JSONObject>) enemies.iterator();

            // Enemy Strategies
            EnemyStrategy dumb = new DumbStrategy();
            EnemyStrategy follow = new FollowStrategy();

            // Get mushrooms
            while (iterator.hasNext()) {
                JSONObject enemyJSON = (JSONObject) iterator.next();

                double x = (double) enemyJSON.get("x");
                double y = (double) enemyJSON.get("y");
                String image = (String) enemyJSON.get("path");
                boolean startLeft = (boolean) enemyJSON.get("startLeft");

                String strategy = (String) enemyJSON.get("strategy");

                EnemyStrategy strat = null;

                switch (strategy) {
                    case "dumb":
                        strat = dumb;
                        break;
                    case "follow":
                        strat = follow;
                        break;
                }

                Slime enemy = new Slime(image, x, y, startLeft, strat);

                levelBuilder.addInteractable(enemy);
                levelBuilder.addEnemy(enemy);
            }

            JSONObject flagJSON = (JSONObject) object.get("flag");

            double flagX = (double) flagJSON.get("x");
            double flagY = (double) flagJSON.get("y");

            Flag flag = new Flag(flagX, flagY);

            levelBuilder.addInteractable(flag);
            levelBuilder.addStaticEntity(flag);

            return levelBuilder.build();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }
}
