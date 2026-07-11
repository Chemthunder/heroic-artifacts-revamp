package net.the_hero_robot.redemption.impl.arcane_kings;

import net.the_hero_robot.redemption.impl.arcane_kings.evocation.EvocationSpell;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
// Used for registering everything related to arcane kings
public interface ArcaneKingsInit {
    List<EvocationSpell> EVOCATION_SPELLS = new ArrayList<>();


    static void init() {}
}
