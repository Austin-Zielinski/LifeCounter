/**
 * @author Austin Zielinski
 * ModelListener is the interface for the view to interact with the model.
 */
public interface ModelListener {
    /**
     * setHealth modifies the health the view displays depending on what the model shows
     * @param health (int) - the health of the player
     * @param baseHealth (int) - the base health of players
     */
    public void setHealth(int health, int baseHealth);

    /**
     * modifyPlayerList changes the comboBox within the View, either adding the player or
     * removing the player
     * @param name - the name of the player to add/remove
     * @param toAdd - whether the component should be added or removed
     */
    public void modifyPlayerList(String name, boolean toAdd);
}
