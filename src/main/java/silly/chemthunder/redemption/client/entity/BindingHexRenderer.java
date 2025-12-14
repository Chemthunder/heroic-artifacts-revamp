package silly.chemthunder.redemption.client.entity;

import com.nitron.nitrogen.render.RenderUtils;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.block.FinalAceBlock;
import silly.chemthunder.redemption.entity.BindingHexEntity;

public class BindingHexRenderer extends MobEntityRenderer<BindingHexEntity, BindingHexEntityModel<BindingHexEntity>> {
    public BindingHexRenderer(EntityRendererFactory.Context context) {
     //   super(context, new BindingHexEntityModel<>(context.getPart(BindingHexEntityModel.MOTH_MODEL)), 0.5f);
        super(context, new BindingHexEntityModel<>(context.getPart(BindingHexEntityModel.MOTH_MODEL)), 0);
    }

    @Override
    public Identifier getTexture(BindingHexEntity entity) {
        return Redemption.id("textures/entity/binding_hex.png");
    }
    public static final Identifier TEXTURE = Redemption.id("textures/render/storm_eye_tile_2.png");
    @Override
    public void render(BindingHexEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        matrixStack.translate(-livingEntity.getPos().getX(), -livingEntity.getPos().getY(), -livingEntity.getPos().getZ());


        RenderUtils.renderTexturedSphere(
                matrixStack,
                vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE, true)),
                livingEntity.getBlockPos(),
                FinalAceBlock.radiusTicks,
                42,
                0
        );

        matrixStack.pop();
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public boolean shouldRender(BindingHexEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    protected boolean isShaking(BindingHexEntity entity) {
        return true;
    }
}