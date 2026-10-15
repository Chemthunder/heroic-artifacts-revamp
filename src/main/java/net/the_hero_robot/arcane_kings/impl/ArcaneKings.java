package net.the_hero_robot.arcane_kings.impl;

import net.fabricmc.api.ModInitializer;
import net.the_hero_robot.arcane_kings.impl.index.AKItemGroups;
import net.the_hero_robot.arcane_kings.impl.index.AKItems;
import net.the_hero_robot.arcane_kings.impl.index.AKSpells;

/**
 * @author AcoYT
 */
public class ArcaneKings implements ModInitializer {
    public void onInitialize() {
        AKItemGroups.init();
        AKItems.init();
        AKSpells.init();
    }
}
