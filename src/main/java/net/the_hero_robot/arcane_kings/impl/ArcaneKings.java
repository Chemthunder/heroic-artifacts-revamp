package net.the_hero_robot.arcane_kings.impl;

import net.fabricmc.api.ModInitializer;
import net.the_hero_robot.arcane_kings.impl.index.AKItemGroups;
import net.the_hero_robot.arcane_kings.impl.index.AKItems;
import net.the_hero_robot.arcane_kings.impl.index.AKSpells;
import net.the_hero_robot.arcane_kings.impl.networking.AKNetworking;

/**
 * @author AcoYT
 */
public class ArcaneKings implements ModInitializer {
    public void onInitialize() {
        /* Initialization */
        AKItemGroups.init();
        AKItems.init();
        AKSpells.init();

        /* Networking */
        AKNetworking.registerCommon();
    }
}
