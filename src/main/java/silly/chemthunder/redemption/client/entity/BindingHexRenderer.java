package silly.chemthunder.redemption.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.Redemption;
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
}