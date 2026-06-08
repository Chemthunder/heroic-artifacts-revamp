package net.the_hero_robot.redemption.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;

/**
 * @author AcoYT
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isInvisible()Z"
            )
    )
    private boolean redemption$cancelRenderHitboxes(Entity instance, Operation<Boolean> original) {
        EnshroudedComponent component = EnshroudedComponent.KEY.getNullable(instance);
        if (component != null) {
            return original.call(instance) || component.isShrouded();
        }

        return original.call(instance);
    }
}
