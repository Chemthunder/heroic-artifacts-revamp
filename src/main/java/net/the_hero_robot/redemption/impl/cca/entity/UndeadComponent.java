package net.the_hero_robot.redemption.impl.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class UndeadComponent implements AutoSyncedComponent, CommonTickingComponent  {
    public static final ComponentKey<UndeadComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("vampire"), UndeadComponent.class);
    private final PlayerEntity player;

    private int undead = 0;
    // 0 is Non-undead
    // 4 is Rotting AKA Zombie
    // 3 is Vampire
    // 2 is Half-Grey
    // 1 is Grey

    public UndeadComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void setUndead(int undead) {
        this.undead = undead;
        this.sync();
    }
    @Override
    public void tick() {

    }



    public int returnUndead() {
        return this.undead;
    }

    public boolean isUndead(){
        if (undead != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isVampire(){
        if (undead == 3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRotting(){
        if (undead == 4) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isSunWeak(){
        if (undead == 3 || undead == 4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAnyGreyed(){
        if (undead == 1 || undead == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHalfGrey(){
        if (undead == 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        undead = nbtCompound.getInt("Undead");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Undead", this.undead);
    }
}
