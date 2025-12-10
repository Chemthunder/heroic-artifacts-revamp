package silly.chemthunder.redemption.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin <T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @ModifyReturnValue(method = "getShadowRadius(Lnet/minecraft/entity/LivingEntity;)F", at = @At("RETURN"))
    private float modifyShadow(float original) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {

            if (EnshroudedPlayerComponent.KEY.get(player).isShrouded) {
                    // changed to be in PlayerEntityMixin, tick() and doing player.setInvisible(true)
                // changed it to cancel rendering due to potential memory leak bugs or lag, since it sets the player to invisible every single server tick >:p
//                return 0f;
            }
        }
        return original;
    }
}
