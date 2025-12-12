package silly.chemthunder.redemption.block.entity.render;

import com.nitron.nitrogen.render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.block.FinalAceBlock;
import silly.chemthunder.redemption.block.entity.FinalAceBlockEntity;

public class FinalAceBlockEntityRenderer implements BlockEntityRenderer<FinalAceBlockEntity> {
    public static final Identifier TEXTURE = Redemption.id("textures/render/storm_eye_tile_2.png");
    @Override
    public void render(FinalAceBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(-entity.getPos().getX(), -entity.getPos().getY(), -entity.getPos().getZ());
        RenderUtils.renderTexturedCube(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE, true)),
                entity.getPos(),
                FinalAceBlock.radiusTicks,
                Vec2f.ZERO,
                7
        );

        MinecraftClient.getInstance().chunkCullingEnabled = false;
        matrices.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(FinalAceBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean isInRenderDistance(FinalAceBlockEntity blockEntity, Vec3d pos) {
        return true;
    }
}
