package silly.chemthunder.redemption.impl.cca.entity;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.cca.entity.flash.JudgementFlashComponent;
import silly.chemthunder.redemption.impl.index.RedemptionItems;
import silly.chemthunder.redemption.impl.index.RedemptionSounds;
import silly.chemthunder.redemption.impl.index.data.RedemptionDamageTypes;

public class JudgementComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<JudgementComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("judgement"), JudgementComponent.class);
    private final PlayerEntity player;

    private boolean judgement = false;
    private int monologueTicks = 0;

    public JudgementComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.judgement = nbt.getBoolean("Judgement");
        this.monologueTicks = nbt.getInt("MonologueTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Judgement", this.judgement);
        nbt.putInt("MonologueTicks", this.monologueTicks);
    }

    public void tick() {
        if (this.monologueTicks > 0) {
            this.monologueTicks--;
            if (this.player.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.SCULK_SOUL,
                        this.player.getX(), this.player.getY(), this.player.getZ(),
                        1,
                        0.3F, 0.6F, 0.3F,
                        0.03F
                );
            }

            if (this.monologueTicks == 0) {
                this.sync();
                this.beginKillAnim(this.player, this.player.getWorld());
            }
        }
    }

    public void beginKillAnim(PlayerEntity player, World world) {
        if (world instanceof ServerWorld serverWorld) {
            player.dropStack(RedemptionItems.COURT_GLASS.getDefaultStack());
            player.setInvulnerable(false);
            player.damage(RedemptionDamageTypes.create(world, RedemptionDamageTypes.DESCEND), Float.MAX_VALUE);

            serverWorld.spawnParticles(ParticleTypes.SOUL, player.getX(), player.getY(), player.getZ(), 75, 0.3f, 0.6f, 0.3f, 0.5);
            serverWorld.spawnParticles(ParticleTypes.END_ROD, player.getX(), player.getY(), player.getZ(), 75, 0.3f, 0.6f, 0.3f, 0.5);

            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                if (serverPlayer instanceof ScreenShaker screenShaker) {
                    screenShaker.addScreenShake(10, 2);
                    JudgementFlashComponent.KEY.get(serverPlayer).setFlashTicks(20);
                }

                serverPlayer.playSoundToPlayer(RedemptionSounds.EVENT_JUDGE_DEATH, SoundCategory.PLAYERS, 1, 1);
                serverPlayer.playSoundToPlayer(RedemptionSounds.EVENT_PING, SoundCategory.PLAYERS, 1, 1);
                serverPlayer.playSoundToPlayer(RedemptionSounds.EVENT_SONAR_PING, SoundCategory.PLAYERS, 1, 1);
            }
        }
    }

    public boolean isJudgement() {
        return this.judgement;
    }

    public void setJudgement(boolean judgement) {
        this.judgement = judgement;
        this.sync();
    }

    public int getMonologueTicks() {
        return this.monologueTicks;
    }

    public void setMonologueTicks(int monologueTicks) {
        this.monologueTicks = monologueTicks;
        this.sync();
    }
}
