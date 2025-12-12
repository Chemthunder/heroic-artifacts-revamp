package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import silly.chemthunder.redemption.entity.BindingHexEntity;
import silly.chemthunder.redemption.index.RedemptionEntities;

public class RoyalFlushItem extends Item implements ColorableItem {
    public RoyalFlushItem(Settings settings) {
        super(settings);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFFd70048;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF8e1a41;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF01c0810;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();
        BindingHexEntity hex = new BindingHexEntity(RedemptionEntities.BINDING_HEX, user.getWorld());
        hex.updatePosition(entity.getX(), entity.getY(), entity.getZ());
        entity.startRiding(hex);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnEntity(hex);
        }

        return super.useOnEntity(stack, user, entity, hand);
    }
}
