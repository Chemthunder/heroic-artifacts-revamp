package silly.chemthunder.redemption.impl;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import silly.chemthunder.redemption.impl.event.client.JudgementRiptideEvent;
import silly.chemthunder.redemption.impl.event.client.SelfTransparencyEvent;
import silly.chemthunder.redemption.impl.index.RedemptionEntities;
import silly.chemthunder.redemption.impl.index.RedemptionNetworking;
import silly.chemthunder.redemption.impl.index.RedemptionParticles;
import silly.chemthunder.redemption.impl.util.RedemptionKeybindings;

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
