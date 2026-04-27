package silly.chemthunder.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import silly.chemthunder.redemption.impl.cca.entity.EnshroudedComponent;
import silly.chemthunder.redemption.impl.index.RedemptionParticles;
import silly.chemthunder.redemption.impl.index.RedemptionSounds;

import java.util.List;

public class HuntersCourtGlassItem extends Item implements ColorableItem {
    public HuntersCourtGlassItem(Settings settings) {
        super(settings);
    }

    public int startColor(ItemStack itemStack) {
        return 0xFFb629eb;
    }

    public int endColor(ItemStack itemStack) {
        return 0xFF460350;
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xF01b1121;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        EnshroudedComponent shroud = EnshroudedComponent.KEY.get(user);

        if (user.getOffHandStack().isOf(this)) {
            shroud.setShrouded(!shroud.isShrouded());

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RedemptionParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0.3f, 0.6f, 0.3f, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0.3f, 0.6f, 0.3f, 0.03f);
            }
        } else if (user.getMainHandStack().isOf(this)) {
            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, new Box(user.getBlockPos()).expand(10), living -> living != user)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600));

                entity.playSound(RedemptionSounds.HUNTERS_GLASS_BLACKOUT);
            }
        }

        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        for (int i = 0; i < 3; i++) {
            tooltip.add(Text.translatable("lore.hunters_glass." + i).withColor(0xFFb629eb));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
