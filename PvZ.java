package cmsc_22_midterms;

/**
 *
 * @author Jeremy
 */

import cmsc_22_midterms.plant.Plant;
import cmsc_22_midterms.zombie.Zombie;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class PvZ {

    private Character[][] lawn;
    private int sunlight;
    private boolean isAlive;

    // NO NEED TO MODIFY THIS!
    public PvZ() {
        // 5 rows, 10 columns 2d array, everything is null by default
        this.lawn = new Character[][]{
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
        };
        // player starts with 500 sunlight to set up his/her lawn
        this.sunlight = 500;
        this.isAlive = true;
    }

    // NO NEED TO MODIFY THIS!
    public PvZ(Character[][] lawn) {
        this.lawn = lawn;
        this.sunlight = 500;
        this.isAlive = true;
    }

    // This method simulates one turn of our game.
    // MODIFY ONLY PARTS WITH TODO
    public void simulateGame() {
        // Collect sunlight from SunProducer plants give off sunlight.
        // TODO start
        // call on getProducerPlants() to get a list of producers
        List<Producer> producers = getProducerPlants();
        for (Producer sunFlower: producers){
            sunlight += sunFlower.produce();
        }
        // for each of the producers, retrieve the amount of sunlight they produce by calling on produce()
        // TODO end

        // Plants deal damage to first zombie on the same row
        List<Plant> plants = null;
        for (int i = 0; i < lawn.length; i++) {
            plants = getPlantsOnRow(i);
            // each plant on row will attack first zombie on the same row..
            Zombie firstZombieOnRow = getFirstZombie(i);
            for (Plant plant : plants) {
                if (firstZombieOnRow != null) {
                    plant.attack(firstZombieOnRow);
                }
            }
        }

        // SplashAttacker Plants deal damage to surrounding zombies
        for (int i = 0; i < lawn.length; i++) {
            for (int j = 0; j < lawn[i].length; j++) {
                // if splash attacker is found!
                if (lawn[i][j] instanceof SplashAttacker) {
                    SplashAttacker atkr = (SplashAttacker) lawn[i][j];
                    // get zombies on a 3x3 radius
                    List<Zombie> zombies = getZombies3by3(i, j);
                    // ATTACK!!
                    atkr.splashAttack(zombies);
                }
            }
        }

        // Remove any dead monsters from our Lawn.
        removeDead();
        // Zombies move, or attack if plants are blocking the way.
        for (int i = 0; i < lawn.length; i++) {
            for (int j = 0; j < lawn[i].length; j++) {
                if (lawn[i][j] instanceof Zombie) {
                    Zombie zombie = (Zombie) lawn[i][j];
                    // nothing is blocking the zombie's way, you're dead!
                    if (j == 0) {
                        // end game. bye brains!
                        isAlive = false;

                    } else {
                        // if plants are blocking the way, attack
                        if (lawn[i][j - 1] instanceof Plant) {
                            Plant plant = (Plant) lawn[i][j-1];
                            zombie.attack(plant);
                        } else if(lawn[i][j - 1] instanceof Zombie) {
                            // stay.
                        } else {
                            // move zombie.
                            lawn[i][j] = null;
                            lawn[i][j - 1] = zombie;
                        }
                    }
                }
            }
        }
        // Remove any dead plants from our Lawn.
        removeDead();

        // display
        displayLawn();

    }

    // loops through lawn and removes the dead
    private void removeDead() {
        for (int i = 0; i < lawn.length; i++) {
            for (int j = 0; j < lawn[i].length; j++) {
                if (lawn[i][j] != null) {
                    Character chr = lawn[i][j];
                    // if dead, set square to null
                    if (!chr.isAlive()) {
                        lawn[i][j] = null;
                    }
                }
            }
        }
    }


    // gets the first zombie in a row or null if no zombie found
    private Zombie getFirstZombie(int row) {
        for (Character chr : lawn[row]) {
            if (chr instanceof Zombie) {
                // TODO return an instance of Zombie.
                // hint: cast chr then return.
                return (Zombie)chr;
            }
        }
        return null;
    }

    // retrieves zombie on given point, null if empty or plant
    private Zombie getZombie(int row, int col) {
        // check bounds..
        if (row < 0 || row >= lawn.length) {
            return null;
        }

        if (col < 0 || col >= lawn[row].length) {
            return null;
        }

        if (lawn[row][col] instanceof Zombie) {
            // TODO return an instance of Zombie.
            // hint: cast lawn[row][col] then return.
            return (Zombie)lawn[row][col];
        }
        return null;
    }

    // retrieves a list of producer plants
    public List<Producer> getProducerPlants() {
        List<Producer> producers = new ArrayList<Producer>();
        // loop through rows
        for (Character[] col : lawn) {
            // loop through cols
            for (Character chr : col) {
                // TODO get all Producer plants on the lawn...
                if (chr instanceof Producer){
                    producers.add((Producer) chr);
                }
            }
        }
        return producers;
    }

    // gets all the plants on a specific row
    public List<Plant> getPlantsOnRow(int row) {
        List<Plant> attackers = new ArrayList<Plant>();
        // loop through cols of the given row
        for (Character chr : lawn[row]) {
            // TODO get all plants on given row, NOTE must NOT be Splash attacker
            if (!(chr instanceof SplashAttacker) && chr instanceof Plant){
                attackers.add((Plant) chr);
            }
        }
        return attackers;
    }

    // retrieve all zombies to attack in a 3x3 radius and store them in a List
    // parameters represent index of center
    // NO NEED TO MODIFY THIS METHOD!!!
    public List<Zombie> getZombies3by3(int row, int col) {
        List<Zombie> zombies = new ArrayList<Zombie>();

        // get top zombies on the blast radius,
        if (getZombie(row - 1, col - 1) != null)
            zombies.add(getZombie(row - 1, col - 1));
        if (getZombie(row - 1, col) != null)
            zombies.add(getZombie(row - 1, col));
        if (getZombie(row - 1, col + 1) != null)
            zombies.add(getZombie(row - 1, col + 1));
        if (getZombie(row, col - 1) != null)
            zombies.add(getZombie(row, col - 1));
        if (getZombie(row, col + 1) != null)
            zombies.add(getZombie(row, col + 1));
        if (getZombie(row + 1, col - 1) != null)
            zombies.add(getZombie(row + 1, col - 1));
        if (getZombie(row + 1, col) != null)
            zombies.add(getZombie(row + 1, col));
        if (getZombie(row + 1, col + 1) != null)
            zombies.add(getZombie(row + 1, col + 1));

        return zombies;
    }

    // Displays current state of board.
    public void displayLawn() {
        try {
        File file = new File("C:\\Users\\Jeremy\\Desktop\\Test_1.txt");
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        for (int i = 0; i < lawn.length; i++) {
            for (int j = 0; j < lawn[i].length; j++) {
                if (lawn[i][j] == null) {
                    bw.write("_ ");
                } else if (lawn[i][j] instanceof Zombie) {
                    // gets first character of class name and prints it out...
                   bw.write(lawn[i][j].getClass().getSimpleName().toLowerCase().charAt(0) + " ");
                } else if (lawn[i][j] instanceof Plant) {
                    // gets first character of class name and prints it out...
                   bw.write(lawn[i][j].getClass().getSimpleName().charAt(0) + " ");
                }
            }
            bw.newLine();
        }
        String str = ("sunlight: " + sunlight);
        bw.write(str);
        bw.newLine();
        bw.newLine();
        
        bw.close();
        } catch (IOException e) {
        e.printStackTrace();
    }
    }

    // NO NEED TO MODIFY THIS!
    public boolean isAlive() {
        return isAlive;
    }

    // checks whether or not there are still zombies to kill.
    // NO NEED TO MODIFY THIS METHOD!!!
    public boolean hasZombies() {
        for (int i = 0; i < lawn.length; i++) {
            for (int j = 0; j < lawn[i].length; j++) {
                if (lawn[i][j] instanceof Zombie) {
                    return true;
                }
            }
        }
        return false;
    }
}

