package net.the_hero_robot.arcane_kings.impl.index;

import net.the_hero_robot.arcane_kings.impl.evocation.EvocationSpell;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AcoYT
 */
public interface AKSpells {
    List<EvocationSpell> EVOCATION_SPELLS = new ArrayList<>();

    static void init() {}
}
