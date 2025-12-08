package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;

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
        EnshroudedPlayerComponent shroud = EnshroudedPlayerComponent.KEY.get(user);

        double x = user.getX();
        double y = user.getY();
        double z = user.getZ();

        if (!shroud.isShrouded) {
            shroud.isShrouded = true;
            shroud.sync();
        } else {
            shroud.isShrouded = false;
            shroud.sync();
        }

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.TRIAL_OMEN, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
            serverWorld.spawnParticles(ParticleTypes.POOF, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
        }

        return super.use(world, user, hand);
    }
}
