
/**
 * @author Austin Zielinski
 *
 * Main is the main class for the LifeCounter program. The program asks the user for a number of
 * players and their names, and creates a life counter for each of them. The life counter allows
 * the user to keep track of health totals by setting a base amount of health and allowing the
 * user to increase or decrease it either by 1 or an amount that the user specifies
 */
public class Main {
    //set a default health if the player sets no base
    public static final int BASE_HEALTH = 20;
    public static void main(String[] args) {

        // set the default health to the base health (default: 20)
        int health = BASE_HEALTH;

        // starts up the model and the view
        LifeModel model = new LifeModel(health);
        LifeView view = LifeView.create();
        model.setModelListener(view);
        view.setViewListener(model);
    }
}
