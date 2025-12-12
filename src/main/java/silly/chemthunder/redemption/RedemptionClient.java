package silly.chemthunder.redemption;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import silly.chemthunder.redemption.block.entity.render.FinalAceBlockEntityRenderer;
import silly.chemthunder.redemption.event.JudgementRiptideEvent;
import silly.chemthunder.redemption.index.RedemptionBlockEntities;
import silly.chemthunder.redemption.index.RedemptionBlocks;
import silly.chemthunder.redemption.index.RedemptionEntities;
import silly.chemthunder.redemption.index.RedemptionParticles;

public class RedemptionClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        RedemptionParticles.clientIndex();
        RedemptionBlocks.clientIndex();
        RedemptionEntities.clientIndex();

        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());

        BlockEntityRendererFactories.register(RedemptionBlockEntities.FINAL_ACE, context -> new FinalAceBlockEntityRenderer());
    }
}
