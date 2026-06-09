package net.the_hero_robot.redemption.impl;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.the_hero_robot.redemption.impl.event.client.JudgementRiptideEvent;
import net.the_hero_robot.redemption.impl.event.client.SelfTransparencyEvent;
import net.the_hero_robot.redemption.impl.index.RedemptionEntities;
import net.the_hero_robot.redemption.impl.networking.RedemptionNetworking;
import net.the_hero_robot.redemption.impl.index.RedemptionParticles;
import net.the_hero_robot.redemption.impl.util.RedemptionKeyBindings;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class RedemptionClient implements ClientModInitializer {
    public void onInitializeClient() {
        /* Initialization */
        RedemptionParticles.clientInit();
        RedemptionEntities.clientInit();

        RedemptionKeyBindings.register();

        /* Networking */
        RedemptionNetworking.registerS2CPackets();

        /* Events */
        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());
        PlayerOpacityEvent.EVENT.register(new SelfTransparencyEvent());
    }
}
