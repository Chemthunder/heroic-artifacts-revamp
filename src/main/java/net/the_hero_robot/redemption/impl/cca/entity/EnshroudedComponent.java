package net.the_hero_robot.redemption.impl.cca.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class EnshroudedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<EnshroudedComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("shrouded"), EnshroudedComponent.class);
    private final PlayerEntity player;

    private boolean shrouded = false;
    private int cooldown = 0;

    public EnshroudedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void tick() {
        if (this.cooldown > 0) {
            this.cooldown--;
            if (this.cooldown == 0) {
                sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.shrouded = nbt.getBoolean("Shrouded");
        this.cooldown = nbt.getInt("Cooldown");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Shrouded", this.shrouded);
        nbt.putInt("Cooldown", this.cooldown);
    }

    public boolean isShrouded() {
        return this.shrouded;
    }

    public void setShrouded(boolean shrouded) {
        this.shrouded = shrouded;
        sync();
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        sync();
    }

    public void set(boolean shrouded, int cooldown) {
        this.shrouded = shrouded;
        this.cooldown = cooldown;
        sync();
    }

    public static boolean isShrouded(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return false;
        return KEY.get(player).isShrouded();
    }
}
