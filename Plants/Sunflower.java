package cmsc_22_midterms.plant;

import cmsc_22_midterms.Producer;

/**
 *
 * @author Jeremy
 */
public class Sunflower extends Plant implements Producer{
    final static int HP = 30;
    final static int DAMAGE = 0;
    final static int SUN_COST = 50;
    final static int PRODUCE = 100;
    public Sunflower() {
        super(HP, DAMAGE, SUN_COST);
    }

    @Override
    public int produce() {
        return PRODUCE;
    }
}
