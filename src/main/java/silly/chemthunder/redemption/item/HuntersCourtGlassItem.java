package silly.chemthunder.redemption.item;

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
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;
import silly.chemthunder.redemption.index.RedemptionParticles;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

import java.util.List;

public class HuntersCourtGlassItem extends Item implements ColorableItem {
    public HuntersCourtGlassItem(Settings settings) {
        super(settings);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFFb629eb;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF460350;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF01b1121;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();
        // var declaration

        if (user.getOffHandStack().isOf(this)) {
            EnshroudedPlayerComponent shroud = EnshroudedPlayerComponent.KEY.get(user);


            if (!shroud.isShrouded) {
                shroud.isShrouded = true;
                user.setInvisible(true);
                shroud.sync();
            } else {
                shroud.isShrouded = false;
                shroud.sync();
            }

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RedemptionParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0.3f, 0.6f, 0.3f, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0.3f, 0.6f, 0.3f, 0.03f);
            }
        } else if (user.getMainHandStack().isOf(this)) {
            net.minecraft.util.math.Box box = new Box(user.getBlockPos()).expand(10, 10, 10);
            List<LivingEntity> entities = user.getWorld().getEntitiesByClass(
                    LivingEntity.class, box,
                    entity -> true
            );

            for (LivingEntity entity : entities) {
                if (!(entity == user)) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600));
                }
                entity.playSound(RedemptionSoundEvents.HUNTER_BLACKOUT);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int color = 0xb629eb;

        tooltip.add(Text.translatable("lore.hunters_glass_1").withColor(color));
        tooltip.add(Text.translatable("lore.hunters_glass_2").withColor(color));
        tooltip.add(Text.translatable("lore.hunters_glass_3").withColor(color));

        super.appendTooltip(stack, context, tooltip, type);
    }
}
