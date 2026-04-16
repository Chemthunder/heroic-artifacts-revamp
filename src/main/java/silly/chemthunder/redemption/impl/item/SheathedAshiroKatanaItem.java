package silly.chemthunder.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.index.RedemptionItems;
import silly.chemthunder.redemption.impl.index.RedemptionSoundEvents;

import java.util.Arrays;
import java.util.List;

public class SheathedAshiroKatanaItem extends Item implements ColorableItem, ModelVaryingItem {
    public SheathedAshiroKatanaItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack mainStack = new ItemStack(RedemptionItems.ASHIRO_KATANA);
        ItemStack offStack = new ItemStack(RedemptionItems.ASHIRO_SHEATH);

        user.setStackInHand(Hand.MAIN_HAND, mainStack);
        user.getInventory().insertStack(PlayerInventory.OFF_HAND_SLOT, offStack);
        stack.decrement(1);

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400));
        user.playSound(RedemptionSoundEvents.UNSHEATHE);

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return MiscUtils.isGui(renderMode) ? Redemption.id("ashiro_sheathed") : Redemption.id("ashiro_sheathed_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Redemption.id("ashiro_sheathed"),
                Redemption.id("ashiro_sheathed_in_hand")
        );
    }

    public int startColor(ItemStack itemStack) {
        return 0xFF6e5353;
    }

    public int endColor(ItemStack itemStack) {
        return 0xFF271e1e;
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xFF1d1212;
    }
}
