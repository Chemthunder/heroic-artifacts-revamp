package net.the_hero_robot.redemption.impl;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.index.*;
import net.the_hero_robot.redemption.impl.networking.RedemptionNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author All
 */
public class Redemption implements ModInitializer {
    public static final String MOD_ID = "redemption";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        /* AcornLib */
        ALib.registerModMenu(MOD_ID, 0xFFe95050);

        /* Initialization */
        RNItems.init();
        RNItemGroups.init();
        RNSounds.init();
        RNParticles.init();
        RNDataComponents.init();
        RNEntities.init();

        /* Networking */
        RedemptionNetworking.registerTypes();
        RedemptionNetworking.registerC2SPackets();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
