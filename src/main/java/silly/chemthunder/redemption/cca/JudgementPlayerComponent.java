package silly.chemthunder.redemption.cca;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.index.RedemptionDamageSources;
import silly.chemthunder.redemption.index.RedemptionItems;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

import java.util.List;

public class JudgementPlayerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<JudgementPlayerComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("judgement"), JudgementPlayerComponent.class);
    private final PlayerEntity player;
    public boolean isJudgement = false;
    public int monologueTicks = 0;

    public JudgementPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.isJudgement = nbtCompound.getBoolean("isJudgement");
        this.monologueTicks = nbtCompound.getInt("monologueTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("isJudgement", isJudgement);
        nbtCompound.putInt("monologueTicks", monologueTicks);
    }

    @Override
    public void tick() {
        if (monologueTicks > 0) {
            monologueTicks--;
            if (player.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL, player.getX(), player.getY(), player.getZ(), 1, 0.3f, 0.6f, 0.3f, 0.03);
            }
            if (monologueTicks == 0) {
                sync();
                beginKillAnim(player, player.getWorld());
            }
        }
    }

    public void beginKillAnim(PlayerEntity player, World world) {
        if (world instanceof ServerWorld serverWorld) {
            player.dropStack(RedemptionItems.COURT_GLASS.getDefaultStack());
            player.setInvulnerable(false);
            player.damage(RedemptionDamageSources.descend(player), player.getMaxHealth() * 50);

            serverWorld.spawnParticles(ParticleTypes.SOUL, player.getX(), player.getY(), player.getZ(), 75, 0.3f, 0.6f, 0.3f, 0.5);
            serverWorld.spawnParticles(ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 75, 0.3f, 0.6f, 0.3f, 0.5);

            serverWorld.playSound(player.getX(), player.getY(), player.getZ(), RedemptionSoundEvents.JUDGE_DEATH, SoundCategory.MASTER, 1, 1, true);

            net.minecraft.util.math.Box box = new Box(player.getBlockPos()).expand(150, 200, 150);
            List<LivingEntity> entities = player.getWorld().getEntitiesByClass(
                    LivingEntity.class, box,
                    entity -> true
            );

            for (LivingEntity living : entities) {
                if (living instanceof ScreenShaker screenShaker) {
                    screenShaker.addScreenShake(10, 2);
                    JudgementFlashComponent flash = JudgementFlashComponent.KEY.get(living);

                    flash.flashTicks = 20;
                    flash.sync();
                }
            }


        }
    }
}
