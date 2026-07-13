package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getTarget", at = @At("RETURN"))
    public LivingEntity redemption$getTarget(LivingEntity target) {
        if (JudgementComponent.isJudgement(target) && this.getCommandTags().contains("courtling")) {
            return null;
        }

        return target;
    }
}
