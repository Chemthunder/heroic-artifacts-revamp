package silly.chemthunder.redemption;

import com.nitron.nitrogen.render.RenderUtils;
import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import silly.chemthunder.redemption.block.entity.render.FinalAceBlockEntityRenderer;
import silly.chemthunder.redemption.event.JudgementRiptideEvent;
import silly.chemthunder.redemption.index.RedemptionBlockEntities;
import silly.chemthunder.redemption.index.RedemptionBlocks;
import silly.chemthunder.redemption.index.RedemptionParticles;

public class RedemptionClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
        RedemptionParticles.clientIndex();
        RedemptionBlocks.clientIndex();

        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());

        BlockEntityRendererFactories.register(RedemptionBlockEntities.FINAL_ACE, context -> new FinalAceBlockEntityRenderer());
    }
}
