import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Austin Zielinski
 * LifeView is the view for the LifeCounter program. It shows all of the UI
 * elements to the user and tells the model what the user inputs
 */
public class LifeView implements ModelListener {
    //iterator is the amount that the health increases/decreases by when there are no given health value
    private static final int ITERATOR = 1;

    //frame is the frame for showing the UI
    private JFrame frame;
    //addButton calls the increment function when pressed
    private JButton addButton;
    //subButton calls the decrement function when pressed
    private JButton subButton;
    //resetButton calls the reset function when pressed
    private JButton resetButton;
    //newMaxButton calls the setMaxHealth function when pressed
    private JButton newMaxButton;
    //numText gets input for modifying health values
    private JTextField numText;
    //healthLabel displays health values
    private JLabel healthLabel;
    // space for players to add new players into the game
    private JTextField playerText;
    // lists the players within the game
    private JComboBox playerBox;
    // adds players into the game
    private JButton addPlayerButton;
    // removes players from the game
    private JButton removePlayerButton;
    //font represents the font everything uses
    private Font font;

    //viewListener is the pointer to the model
    private ViewListener viewListener;

    //constants for displaying objects in the frame
    private static final String N = SpringLayout.NORTH;
    private static final String S = SpringLayout.SOUTH;
    private static final String E = SpringLayout.EAST;
    private static final String W = SpringLayout.WEST;
    private static final String HC = SpringLayout.HORIZONTAL_CENTER;
    private static final String VC = SpringLayout.VERTICAL_CENTER;
    private static final Spring GAP =
            Spring.constant (10, 10, Integer.MAX_VALUE);

    private LifeView(){
        //initialize the frame
        frame = new JFrame("Life Counter");
        // this minimum size allows users to see all the information on the screen without needing
        // to resize the window
        frame.setMinimumSize(new Dimension(400, 520));
        Container pane = frame.getContentPane();
        JPanel p1 = new JPanel();
        pane.add(p1);
        SpringLayout layout = new SpringLayout();
        p1.setLayout(layout);

        //initialize the font the objects use
        this.font = new Font("Default", Font.PLAIN, 30);

        //initialize the objects to go into the frame
        // healthLabel tells users how much health a player has
        // we only have one because we want the code to be scalable for a large number of players
        healthLabel = new JLabel("0");
        healthLabel.setFont(font);
        p1.add(healthLabel);
        // addButton and subButton increases and decreases player's health, respectively
        addButton = new JButton("Up");
        addButton.setFont(font);
        p1.add(addButton);
        subButton = new JButton("Down");
        subButton.setFont(font);
        p1.add(subButton);
        // reset resets all player's health totals to the same base amount
        // this is useful for starting new games
        resetButton = new JButton("Reset");
        resetButton.setFont(font);
        p1.add(resetButton);
        // set a new base allows you to set a new base amount for players to use
        // this is very useful when you switch games or when you add more players
        // and the game changes health values based on the number of players
        newMaxButton = new JButton("Set new base");
        newMaxButton.setFont(font);
        p1.add(newMaxButton);
        // numText lets you change the amount of points a player's health gets increased
        // or decreased by (the default is 1), or lets you set a new base amount for the player's health
        numText = new JTextField(10);
        numText.setFont(font);
        p1.add(numText);
        // playerText allows you to put in information in for which players you want to add into the game
        playerText = new JTextField(10);
        playerText.setFont(font);
        p1.add(playerText);
        // playerBox holds a list of players currently in the game
        playerBox = new JComboBox();
        playerBox.setFont(font);
        p1.add(playerBox);
        // addPlayerButton and removePlayerButton allows you to add and remove players, respectively
        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setFont(font);
        p1.add(addPlayerButton);
        removePlayerButton = new JButton("Remove Player");
        removePlayerButton.setFont(font);
        p1.add(removePlayerButton);

        //organize the objects to go into the frame
        layout.putConstraint(HC, healthLabel, 0, HC, p1);
        layout.putConstraint(N, healthLabel, GAP, N, p1);
        layout.putConstraint(HC, playerBox, 0, HC, p1);
        layout.putConstraint(N, playerBox, GAP, S, healthLabel);
        layout.putConstraint(N, playerText, GAP, S, playerBox);
        layout.putConstraint(HC, playerText, 0, HC, p1);
        layout.putConstraint(N, addPlayerButton, GAP, S, playerText);
        layout.putConstraint(HC, addPlayerButton, 0, HC, p1);
        layout.putConstraint(N, removePlayerButton, GAP, S, addPlayerButton);
        layout.putConstraint(HC, removePlayerButton, 0, HC, p1);
        layout.putConstraint(N, subButton, GAP, S, removePlayerButton);
        layout.putConstraint(HC, subButton, 0, HC, p1);
        layout.putConstraint(E, addButton, -10, W, subButton);
        layout.putConstraint(N, addButton, GAP, S, removePlayerButton);
        layout.putConstraint(N, resetButton, GAP, S, removePlayerButton);
        layout.putConstraint(W, resetButton, GAP, E, subButton);
        layout.putConstraint(HC, numText, 0, HC, p1);
        layout.putConstraint(N, numText, GAP, S, newMaxButton);
        layout.putConstraint(HC, newMaxButton, 0, HC, p1);
        layout.putConstraint(N, newMaxButton, GAP, S, resetButton);

        //add actions for the buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { increment(); }
        });

        subButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ decrement(); }
        });

        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ reset(); }
        });

        newMaxButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ setNewBase(); }
        });

        addPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addPlayer(); }
        });

        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { removePlayer(); }
        });

        // when we change which player we're looking at with the playerBox, we want which values
        // we're looking at to change too
        playerBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                changePlayer();
            }
        });

        // when we close the window, close the program
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){ System.exit(0); }
        });

        // pack the frame, then make it visible
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * the reference to the UI elements with the LifeView ui parameter
     */
    private static class LifeViewRef{
        public LifeView ui;
    }

    /**
     * create a new LifeView object
     * we only want one on screen at a time per instance of the program
     * as a result, we have a private constructor called by this create method
     * @return (LifeView) - the view object
     */
    public static LifeView create(){
        // create the reference to the UI
        final LifeViewRef ref = new LifeViewRef();
        Runnable task = new Runnable() {
            public void run(){ ref.ui = new LifeView(); }
        };
        try{
            SwingUtilities.invokeAndWait(task);
        }
        catch(Throwable exc){
            exc.printStackTrace(System.err);
            System.exit(1);
        }
        return ref.ui;
    }

    /**
     * sets the model for the view to look at
     * @param viewListener (ViewListener) - the model to look at
     */
    public void setViewListener(final ViewListener viewListener){
        this.viewListener = viewListener;
        reset();
    }

    /**
     * addPlayer adds a player into the game by calling the equivalent function in the model
     */
    public void addPlayer(){
        viewListener.addPlayer(playerText.getText());
        // we don't want players to add multiple players with the same name,
        // so clearing it for the next player makes it easier to add multiple players
        // it also makes it much harder to accidentally remove a player after adding them
        playerText.selectAll();
        playerText.cut();
    }

    /**
     * removePlayer removes a player from the game by calling the equivalent function in the model
     */
    public void removePlayer(){
        viewListener.removePlayer(playerText.getText());
        // we don't want players to remove the same player multiple times,
        // so clearing it makes it harder to try clearing the same player multiple times
        playerText.selectAll();
        playerText.cut();
    }

    /**
     * changePlayer changes the player that's being focused on within the window
     */
    public void changePlayer(){
        viewListener.changePlayer(playerBox.getSelectedItem().toString());
    }

    /**
     * increment calls the model's increment function to increase health (default 1)
     */
    private void increment() {
        try{
            String playerName = playerBox.getSelectedItem().toString();
            try {
                // increment the current player's health by the value in numText
                int health = Integer.parseInt(numText.getText());
                viewListener.increment(playerName, health);
            } catch (NumberFormatException exc) {
                // if there are no or illegal values within numText,
                // just increase it by the value of ITERATOR (which by default is 1)
                viewListener.increment(playerName, ITERATOR);
            }
        }
        catch (NullPointerException e){
            // if there are no players, do nothing
        }
    }

    /**
     * decrement calls the model's decrement function to decrease health (default 1)
     * This number can be changed by putting a number into numText
     */
    private void decrement() {
        try {
            String playerName = playerBox.getSelectedItem().toString();
            try {
                // decrement the health of the player by the current value within numText
                int health = Integer.parseInt(numText.getText());
                viewListener.decrement(playerName, health);
            } catch (NumberFormatException exc) {
                // if there are no or illegal values within numText,
                // just decrease it by the value of ITERATOR (which by default is 1)
                viewListener.decrement(playerName, ITERATOR);
            }
        }
        catch (NullPointerException e){
            // if there are no players, do nothing
        }
    }

    /**
     * reset calls the reset function in the model to change the user's health to the base health
     */
    private void reset(){ viewListener.reset(); }

    /**
     * setNewMax calls the setNewMax function in the model to change the user's base health
     */
    private void setNewBase(){
        try {
            int health = Integer.parseInt(numText.getText());
            viewListener.setNewBase(health);
            numText.selectAll();
            numText.cut();
        }
        catch(IllegalArgumentException exc){
            // if there are no or illegal values within numText,
            // don't change the base since there's nothing to change to
        }
    }

    /**
     * implementation of the setHealth function within the ModelListener interface
     * @param health (int) - the health of the player
     */
    public void setHealth(int health, int baseHealth) {
        try {
            // set the healthLabel to display the current player's current health
            // because the playerBox automatically changes when you select a new player
            // and increment and decrement require that a player is already in the table,
            // we can use the playerBox to get the current player instead of pulling it
            // from the table
            String player = playerBox.getSelectedItem().toString();
            healthLabel.setText(player + "'s Health: " + Integer.toString(health));
        }
        catch (NullPointerException e){
            // if we don't have a player in the playerBox (which happens by default),
            // just list the base health
            healthLabel.setText("Starting health: " + Integer.toString(baseHealth));
        }
    }

    /**
     * implementation of the modifyPlayerList function within the ModelListener interface
     * @param name - the name of the player to add/remove
     * @param toAdd - whether the component should be added or removed
     */
    public void modifyPlayerList(String name, boolean toAdd){
        if(toAdd){
            playerBox.addItem(name);
        }
        else{
            playerBox.removeItem(name);
        }
    }
}
