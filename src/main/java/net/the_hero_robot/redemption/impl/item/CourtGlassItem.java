package net.the_hero_robot.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.cca.entity.flash.JudgementFlashComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RedemptionDataComponents;
import net.the_hero_robot.redemption.impl.index.RedemptionItems;
import net.the_hero_robot.redemption.impl.index.RedemptionSounds;
import net.the_hero_robot.redemption.impl.util.ModUtil;

import java.util.List;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class CourtGlassItem extends Item implements ColorableItem {
    public CourtGlassItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            if (hand == Hand.OFF_HAND && !JudgementComponent.isJudgement(user)) {
                becomeJudgement(user, world, user.getOffHandStack());

                user.getItemCooldownManager().set(stack.getItem(), 180);
                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (hand == Hand.MAIN_HAND && JudgementComponent.isJudgement(user)) {
                summonCourt(world, user);

                user.getItemCooldownManager().set(stack.getItem(), 320);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }

        return super.use(world, user, hand);
    }

    public void becomeJudgement(PlayerEntity player, World world, ItemStack offStack) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        JudgementComponent component = JudgementComponent.KEY.get(player);

        component.setJudgement(true);
        player.setHealth(player.getMaxHealth());

        for (PlayerEntity playerEntity : world.getPlayers()) {
            JudgementFlashComponent.KEY.get(playerEntity).setFlashTicks(20);

            // This plays a new sound at the position of every player, in both the ClientWorld and ServerWorld. Do you only want this to play once? - SkyNotTheLimit
            playerEntity.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
            playerEntity.playSound(SoundEvents.ENTITY_WARDEN_HEARTBEAT);
            playerEntity.playSound(SoundEvents.ENTITY_WARDEN_EMERGE);
            playerEntity.playSound(RedemptionSounds.EVENT_BECOME_JUDGE);
        }

        if (!player.isInCreativeMode()) offStack.decrement(1);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SOUL, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            serverWorld.spawnParticles(ParticleTypes.GLOW, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            if (player instanceof ScreenShaker screenShaker) {
                screenShaker.addScreenShake(5, 1);
            }

            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                // iterating through players twice, consider doing that only once - SkyNotTheLimit
                serverPlayer.sendMessage(Text.translatable(player.getNameForScoreboard()).append(Text.literal(" was crowned for Judgement")), false);
            }
        }
    }

    public void summonCourt(World world, PlayerEntity user) {
        for (int i = 0; i < 3; i++) {
            WitherSkeletonEntity glassCannon = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);

            ItemStack katana = RedemptionItems.SCULK_KATANA.getDefaultStack();
            ItemStack sheath = ModUtil.copy(katana,
                    RedemptionDataComponents.KATANA,
                    KatanaComponent.get(katana)
                            .withBladeType(KatanaComponent.BladeType.SHEATH)
            );

            glassCannon.equipStack(EquipmentSlot.MAINHAND, katana);
            glassCannon.equipStack(EquipmentSlot.OFFHAND, sheath);
            glassCannon.equipStack(EquipmentSlot.HEAD, Items.DIAMOND_HELMET.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.FEET, Items.DIAMOND_BOOTS.getDefaultStack());

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (glassCannon.getEquippedStack(slot).isEmpty()) continue;
                glassCannon.setEquipmentDropChance(slot, 0.0F);
            }

            glassCannon.updatePosition(user.getX(), user.getY(), user.getZ());
            glassCannon.setCustomNameVisible(true);
            glassCannon.setCustomName(Text.translatable("lore.courtling").formatted(Formatting.BLUE));
            glassCannon.setGlowing(true);
            glassCannon.getCommandTags().add("courtling");

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnEntity(glassCannon);
            }
        }
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        for (int i = 0; i < 3; i++) {
            tooltip.add(Text.translatable("tooltip.redemption.court_glass." + i).withColor(0xFF26bdbd));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public int startColor(ItemStack stack) {
        return 0xFF00fbff;
    }

    public int endColor(ItemStack stack) {
        return 0xFF153030;
    }

    public int backgroundColor(ItemStack stack) {
        return 0xF0040a0a;
    }
}
