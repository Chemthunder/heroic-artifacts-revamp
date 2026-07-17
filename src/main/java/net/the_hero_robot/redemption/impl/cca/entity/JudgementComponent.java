package net.the_hero_robot.redemption.impl.cca.entity;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.cca.entity.flash.JudgementFlashComponent;
import net.the_hero_robot.redemption.impl.index.RedemptionItems;
import net.the_hero_robot.redemption.impl.index.RedemptionSounds;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 * @author Chemthunder
 */
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
                Vec3d pos = this.player.getPos();

                serverWorld.spawnParticles(
                        ParticleTypes.SCULK_SOUL,
                        pos.x, pos.y, pos.z,
                        1,
                        0.3F, 0.6F, 0.3F,
                        0.03F
                );
            }

            if (this.monologueTicks == 0) {
                sync();
                beginKillAnim(this.player.getWorld());
            }
        }
    }

    public void beginKillAnim(World world) {
        if (world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.player.getPos();

            this.player.dropStack(RedemptionItems.COURT_GLASS.getDefaultStack());
            this.player.setInvulnerable(false);
            this.player.damage(RedemptionDamageTypes.create(world, RedemptionDamageTypes.DESCEND), Float.MAX_VALUE);

            serverWorld.spawnParticles(
                    ParticleTypes.SOUL,
                    pos.x, pos.y, pos.z,
                    75,
                    0.3F, 0.6F, 0.3F,
                    0.5F
            );
            serverWorld.spawnParticles(
                    ParticleTypes.END_ROD,
                    pos.x, pos.y, pos.z,
                    75,
                    0.3F, 0.6F, 0.3F,
                    0.5F
            );

            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                if (serverPlayer instanceof ScreenShaker screenShaker) {
                    screenShaker.addScreenShake(10, 2);
                    JudgementFlashComponent.KEY.get(serverPlayer).setFlashTicks(20);
                }

                serverPlayer.playSoundToPlayer(
                        RedemptionSounds.EVENT_JUDGE_DEATH, SoundCategory.PLAYERS,
                        1.0F, 1.0F
                );

                serverPlayer.playSoundToPlayer(
                        RedemptionSounds.EVENT_PING, SoundCategory.PLAYERS,
                        1.0F, 1.0F
                );

                serverPlayer.playSoundToPlayer(
                        RedemptionSounds.EVENT_SONAR_PING, SoundCategory.PLAYERS,
                        1.0F, 1.0F
                );
            }
        }
    }

    public boolean isJudgement() {
        return this.judgement;
    }

    public void setJudgement(boolean judgement) {
        this.judgement = judgement;
        sync();
    }

    public int getMonologueTicks() {
        return this.monologueTicks;
    }

    public void setMonologueTicks(int monologueTicks) {
        this.monologueTicks = monologueTicks;
        sync();
    }

    public static boolean isJudgement(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return false;
        return KEY.get(player).isJudgement();
    }
}
