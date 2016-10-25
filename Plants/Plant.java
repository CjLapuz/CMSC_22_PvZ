package cmsc_22_midterms.plant;

/**
 * @author Jeremy
 */
import cmsc_22_midterms.Character;
/**
 * Class to generalize all types of plants.
 */
public abstract class Plant extends Character {
    private int sunlightCost;
    public Plant(int hp, int damage, int sunlightCost) {
        super(hp, damage);
        this.sunlightCost = sunlightCost;
    }
    
}
    
