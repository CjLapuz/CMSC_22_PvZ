package cmsc_22_midterms.zombie;

/**
 * @author Jeremy
 */
import cmsc_22_midterms.Character;
/**
 * Class to generalize all types of zombies.
 */
public abstract class Zombie extends Character {
    
    public Zombie(int hp, int damage) {
        super(hp, damage);
    }
}
