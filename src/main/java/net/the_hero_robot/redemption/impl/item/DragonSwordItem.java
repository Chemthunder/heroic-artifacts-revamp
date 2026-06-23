package net.the_hero_robot.redemption.impl.item;

import net.acoyt.acornlib.api.item.AdvBurningItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.entity.DragonSwordFireballEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author AcoYT
 */
public class DragonSwordItem extends SwordItem implements ModelVaryingItem, AdvBurningItem {
    public DragonSwordItem(Settings settings) {
        super(ToolMaterials.NETHERITE, settings);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return MiscUtils.isGui(renderMode) ? Redemption.id("dragon_sword") : Redemption.id("dragon_sword_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Redemption.id("dragon_sword"),
                Redemption.id("dragon_sword_in_hand")
        );
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            DragonSwordFireballEntity entity = new DragonSwordFireballEntity(world, user, user.getRotationVector().multiply(1.2F), 2);
            entity.setPosition(user.getEyePos());
            world.spawnEntity(entity);
            world.playSoundFromEntity(null, entity, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);

            user.getItemCooldownManager().set(stack.getItem(), user.isInCreativeMode() ? 20 : 140);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public int getBurnTime(ItemStack stack, LivingEntity attacker, LivingEntity victim) {
        return 5;
    }
}
