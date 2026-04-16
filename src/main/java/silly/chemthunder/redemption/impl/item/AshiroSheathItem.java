package silly.chemthunder.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;

import java.util.Arrays;
import java.util.List;

public class AshiroSheathItem extends Item implements ColorableItem, ModelVaryingItem {
    public AshiroSheathItem(Settings settings) {
        super(settings);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return MiscUtils.isGui(renderMode) ? Redemption.id("ashiro_sheath") : Redemption.id("ashiro_sheath_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Redemption.id("ashiro_sheath"),
                Redemption.id("ashiro_sheath_in_hand")
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
