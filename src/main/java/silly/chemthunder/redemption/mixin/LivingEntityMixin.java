package silly.chemthunder.redemption.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import silly.chemthunder.redemption.cca.FlashComponent;
import silly.chemthunder.redemption.cca.JudgementPlayerComponent;
import silly.chemthunder.redemption.entity.BindingHexEntity;
import silly.chemthunder.redemption.index.RedemptionDamageSources;
import silly.chemthunder.redemption.index.RedemptionEntities;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique @Final
    LivingEntity living = (LivingEntity) (Object) this;

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void deathEffect(DamageSource deathSource, CallbackInfoReturnable<Boolean> cir) {
        if (living instanceof PlayerEntity player) {
            JudgementPlayerComponent judge = JudgementPlayerComponent.KEY.get(player);
            if (judge.isJudgement) {
                if (!deathSource.isOf(RedemptionDamageSources.DESCEND)) {
                    player.setHealth(player.getMaxHealth());
                    player.setVelocity(0, 0.3, 0);
                    player.velocityModified = true;
                    player.setNoGravity(true);
                    player.setInvulnerable(true);
                    player.noClip = true;

                    judge.monologueTicks = 200;
                    judge.sync();
                    cir.setReturnValue(true);
                }
            }
        }

        if (deathSource.isOf(RedemptionDamageSources.ENKEY)) {
            if (!living.hasVehicle() && living.getVehicle() instanceof BindingHexEntity) {
                living.setHealth(living.getMaxHealth());
                BindingHexEntity hex = new BindingHexEntity(RedemptionEntities.BINDING_HEX, getWorld());

                hex.updatePosition(living.getX(), living.getY(), living.getZ());
                living.startRiding(hex);

                if (getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnEntity(hex);

                    for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                        FlashComponent flash = FlashComponent.KEY.get(serverPlayer);
                        flash.flashTicks = 20;
                        flash.sync();

                        if (serverPlayer instanceof ScreenShaker screenShaker) {
                            screenShaker.addScreenShake(2, 1);
                        }
                    }
                }

                cir.setReturnValue(true);
            } else {
                if (getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.END_ROD, living.getX(), living.getY(), living.getZ(), 50, 0, 0, 0, 0.3);
                    for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                        FlashComponent flash = FlashComponent.KEY.get(serverPlayer);
                        flash.flashTicks = 20;
                        flash.sync();

                        if (serverPlayer instanceof ScreenShaker screenShaker) {
                            screenShaker.addScreenShake(1, 5);
                        }

                        serverPlayer.sendMessage(Text.literal(living.getNameForScoreboard() + " left the game").formatted(Formatting.YELLOW));
                    }
                }
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyReturnValue(method = "getMaxHealth", at = @At("RETURN"))
    private float judge$maxHealth(float original) {
        if (living instanceof PlayerEntity player && JudgementPlayerComponent.KEY.get(player).isJudgement) {
            return 40.0f;
        }
        return original;
    }

    @WrapMethod(method = "heal")
    private void judge$boostedHeal(float amount, Operation<Void> original) {
        if (living instanceof PlayerEntity player && JudgementPlayerComponent.KEY.get(player).isJudgement) {
            original.call(amount * 2);
        } else {
            original.call(amount);
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void killOther(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity.hasVehicle() && livingEntity.getVehicle() instanceof BindingHexEntity hex) {
            hex.kill();
        }
    }
}
