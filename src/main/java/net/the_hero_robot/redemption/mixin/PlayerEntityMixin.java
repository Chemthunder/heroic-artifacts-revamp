package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RedemptionDataComponents;
import net.the_hero_robot.redemption.impl.index.RedemptionParticles;
import net.the_hero_robot.redemption.impl.index.tag.RedemptionItemTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    @Shadow
    @Final
    private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "playStepSound", at = @At("RETURN"), cancellable = true)
    private void redemption$cancelStepSounds(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (EnshroudedComponent.KEY.get(this).isShrouded()) ci.cancel();
    }

    @Inject(method = "attack", at = @At("TAIL"))
    private void redemption$disableShroudUponAttack(Entity target, CallbackInfo ci) {
        redemption$disableCloak((PlayerEntity)(Object)this);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void redemption$disableShroudUponDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        redemption$disableCloak((PlayerEntity)(Object)this);
    }

    @Unique
    public void redemption$disableCloak(PlayerEntity player) {
        EnshroudedComponent component = EnshroudedComponent.KEY.get(player);
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        if (component.isShrouded()) {
            component.setShrouded(false);
            component.setCooldown(100);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RedemptionParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void redemption$playerTicker(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getStackInHand(player.getActiveHand()).isIn(RedemptionItemTags.KATANAS) && player.isUsingItem() && JudgementComponent.KEY.get(player).isJudgement()) {
            this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, true, player.getX(), player.getY(), player.getZ(), 0, 0, 0);
        }
    }

    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    private void redemption$disableKatanaSheath(LivingEntity attacker, CallbackInfo ci) {

        ItemStack blockingItem = this.getActiveItem();
        KatanaComponent component = blockingItem.get(RedemptionDataComponents.KATANA);
        Redemption.LOGGER.info(" " + (component != null));
        if (attacker.disablesShield() && component != null && component.bladeType() == KatanaComponent.BladeType.SHEATH) {
            this.getItemCooldownManager().set(blockingItem.getItem(), 100);

            this.clearActiveItem();
            this.getWorld().sendEntityStatus(this, EntityStatuses.BREAK_SHIELD);
        }
    }

    @ModifyVariable(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getVelocity()Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 0
            ),
            ordinal = 3
    )
    private boolean redemption$allowSweeping(boolean bl) {
        ItemStack itemStack = this.getMainHandStack();
        KatanaComponent component = itemStack.get(RedemptionDataComponents.KATANA);
        if (component != null && component.bladeType() == KatanaComponent.BladeType.KATANA) {
            return true;
        }

        return bl;
    }

    @WrapOperation(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;spawnSweepAttackParticles()V"
            )
    )
    private void redemption$noOriginalSweepParticle(PlayerEntity instance, Operation<Void> original) {
        if (instance.getMainHandStack().contains(RedemptionDataComponents.KATANA)) return;
        original.call(instance);
    }
}
