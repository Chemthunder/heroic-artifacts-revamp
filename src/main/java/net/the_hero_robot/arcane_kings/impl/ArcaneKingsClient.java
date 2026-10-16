package net.the_hero_robot.arcane_kings.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.the_hero_robot.arcane_kings.impl.client.PetrificationHandler;
import net.the_hero_robot.arcane_kings.impl.networking.AKNetworking;
import net.the_hero_robot.arcane_kings.impl.util.AKKeyBindings;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
public class ArcaneKingsClient implements ClientModInitializer {
    public void onInitializeClient() {
        /* Initialization */
        AKKeyBindings.register();

        /* Networking */
        AKNetworking.registerClient();

        /* Events */
        PetrificationHandler.init();
    }
}
