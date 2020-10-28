import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * @author Austin Zielinski
 * LifeModel is the model for the LifeCounter program. It does all of the under-the-hood calculations
 * to ensure that the correct health amount is stored and displayed to the user
 */
public class LifeModel implements ViewListener {
    //modelListener is the view object the model interacts with
    private ModelListener modelListener;

    // this is the list of players, each with their own name and life total
    private HashMap<String, Integer> playerList;
    // baseHealth is the starting value for each player's health
    private int baseHealth;

    /**
     * initialize the model with the given health values for currHealth and baseHealth
     * @param health (int) - the health to set to
     */
    public LifeModel(int health){
        this.baseHealth = health;
        playerList = new HashMap<>();
    }

    /**
     * set the view for the model to look at
     * @param modelListener (ModelListener) - the view to look at
     */
    public void setModelListener(ModelListener modelListener){ this.modelListener = modelListener; }

    /**
     * implementation of the addPlayer function within the ViewListener interface
     * @param name (String) - the name of the player to add
     * @return - true if a player was added, false otherwise
     */
    public void addPlayer(String name){
        if(!playerList.containsKey(name)){
            playerList.put(name, baseHealth);
            modelListener.modifyPlayerList(name, true);
        }
    }

    /**
     * implementation of the removePlayer function within the ViewListener interface
     * @param name (String) - the name of the player to remove
     * @return - true if a player was removed, false otherwise
     */
    public void removePlayer(String name){
        if(playerList.containsKey(name)){
            playerList.remove(name);
            modelListener.modifyPlayerList(name, false);
        }
    }

    /**
     * implementation of the changePlayer function within the ViewListener interface
     * @param name - the player to change to
     */
    public void changePlayer(String name) {
        modelListener.setHealth(playerList.get(name), baseHealth);
    }

    /**
     * implementation of the decrement function within the ViewListener interface
     * @param player (String) - the player whose health to decrement
     * @param health (int) - how much to decrease the user's health
     */
    public void decrement(String player, int health){
        int currHealth = playerList.get(player);
        currHealth -= health;
        playerList.put(player, currHealth);
        modelListener.setHealth(currHealth, baseHealth);
    }

    /**
     * implementation of the increment function within the ViewListener interface
     * @param player (String) - the player whose health to increment
     * @param health (int) - how much to increase the user's health
     */
    public void increment(String player, int health){
        int currHealth = playerList.get(player);
        currHealth += health;
        playerList.put(player, currHealth);
        modelListener.setHealth(currHealth, baseHealth);
    }

    /**
     * implementation of the reset function within the ViewListener interface
    */
    public void reset(){
        playerList.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String s, Integer integer) {
                playerList.put(s, baseHealth);
            }
        });
        modelListener.setHealth(baseHealth, baseHealth);
    }

    //implementation of the setNewBase function
    public void setNewBase(int health){
        this.baseHealth = health;
        reset();
    }
}
