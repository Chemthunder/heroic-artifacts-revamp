package silly.chemthunder.redemption;

import net.fabricmc.api.ClientModInitializer;
import silly.chemthunder.redemption.index.RedemptionParticles;

public class RedemptionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RedemptionParticles.clientIndex();
    }
}
