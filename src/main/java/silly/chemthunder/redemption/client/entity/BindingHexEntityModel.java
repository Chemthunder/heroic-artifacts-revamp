package silly.chemthunder.redemption.client.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.entity.BindingHexEntity;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BindingHexEntityModel<T extends BindingHexEntity> extends SinglePartEntityModel<T> {
	private final ModelPart sigil;
	private final ModelPart sigil2;

	public BindingHexEntityModel(ModelPart root) {
		this.sigil = root.getChild("sigil");
		this.sigil2 = root.getChild("sigil2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sigil = modelPartData.addChild("sigil", ModelPartBuilder.create().uv(32, 16).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData sigil2 = modelPartData.addChild("sigil2", ModelPartBuilder.create().uv(32, 16).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        sigil.render(matrices, vertices, light, overlay);
        sigil2.render(matrices, vertices, light, overlay);
    }

    @Override
    public ModelPart getPart() {
        return sigil;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.idleAnimationState, BindingHexAnimations.idle, animationProgress);
    }

    public static final EntityModelLayer MOTH_MODEL = new EntityModelLayer(Redemption.id("binding_hex"), "main");
}