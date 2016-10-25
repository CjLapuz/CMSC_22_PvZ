package cmsc_22_midterms.plant;

import cmsc_22_midterms.SplashAttacker;
import cmsc_22_midterms.zombie.Zombie;
import java.util.List;

/**
 *
 * @author Jeremy
 */
public class CherryBomb extends Plant implements SplashAttacker{
    final static int HP = 10;
    final static int DAMAGE = 150;
    final static int SUN_COST = 150;
    private int counter = 2;
    public CherryBomb() {
        super(HP, DAMAGE, SUN_COST);
    }

    @Override
    public void splashAttack(List<Zombie> zombies) {
        if (counter == 0){
            for (Zombie zombie: zombies){
                zombie.takeDamage(DAMAGE);
                this.setHP(0);
            }
        }else {
            counter--;
        }
    }
    
    public void setCounter(int counter) {this.counter = counter;}
}
