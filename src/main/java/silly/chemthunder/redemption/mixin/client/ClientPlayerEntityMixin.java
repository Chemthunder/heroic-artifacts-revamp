package silly.chemthunder.redemption.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import silly.chemthunder.redemption.impl.index.RedemptionItems;

/**
 * @author AcoYT
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @WrapOperation(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
            )
    )
    private boolean redemption$noIceCreamSlowdown(ClientPlayerEntity instance, Operation<Boolean> original) {
        return original.call(instance) && !instance.getActiveItem().isOf(RedemptionItems.ICE_CREAM);
    }

    @WrapOperation(
            method = "canStartSprinting",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
            )
    )
    private boolean redemption$canSprintIfIceCream(ClientPlayerEntity instance, Operation<Boolean> original) {
        return original.call(instance) && !instance.getActiveItem().isOf(RedemptionItems.ICE_CREAM);
    }
}
