/**
 * @author Austin Zielinski
 * ViewListener is the interface for the model to interact with the view
 */
public interface ViewListener {

    /**
     * adds a new player to the list of players
     * @param name (String) - the name of the player to add
     */
    public void addPlayer(String name);

    /**
     * Removes a player from the list of players
     * @param name (String) - the name of the player to remove
     */
    public void removePlayer(String name);

    /**
     * Change the player to focus on within the list of players
     * @param name (String) - the player to focus on
     */
    public void changePlayer(String name);

    /**
     * decrement decreases the user's health by an amount given in the program
     * @param health (int) - how much to decrease the user's health
     */
    public void decrement(String player, int health);
    /**
     * increment increases the user's health by an amount given in the program
     * @param health (int) - how much to increase the user's health
     */
    public void increment(String player, int health);
    /**
     * reset sets the user's health back to their base health
     */
    public void reset();
    /**
     * setMaxHealth sets the user's health to a new amount
     * @param health (int) - the new base health the user sets
     */
    public void setNewBase(int health);
}
