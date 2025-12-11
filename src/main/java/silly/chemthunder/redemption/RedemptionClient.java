package silly.chemthunder.redemption;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.fabricmc.api.ClientModInitializer;
import silly.chemthunder.redemption.event.JudgementRiptideEvent;
import silly.chemthunder.redemption.index.RedemptionParticles;

public class RedemptionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RedemptionParticles.clientIndex();

        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());
    }
}
