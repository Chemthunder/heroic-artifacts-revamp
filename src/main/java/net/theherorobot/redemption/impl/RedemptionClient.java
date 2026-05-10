package net.theherorobot.redemption.impl;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.theherorobot.redemption.impl.event.client.JudgementRiptideEvent;
import net.theherorobot.redemption.impl.event.client.SelfTransparencyEvent;
import net.theherorobot.redemption.impl.index.RedemptionEntities;
import net.theherorobot.redemption.impl.networking.RedemptionNetworking;
import net.theherorobot.redemption.impl.index.RedemptionParticles;
import net.theherorobot.redemption.impl.util.RedemptionKeybindings;

@Environment(EnvType.CLIENT)
public class RedemptionClient implements ClientModInitializer {
    public void onInitializeClient() {
        /* Initialization */
        RedemptionParticles.clientInit();
        RedemptionEntities.clientInit();

        RedemptionKeybindings.register();

        /* Networking */
        RedemptionNetworking.registerS2CPackets();

        /* Events */
        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());
        PlayerOpacityEvent.EVENT.register(new SelfTransparencyEvent());
    }
}
