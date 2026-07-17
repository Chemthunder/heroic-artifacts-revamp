package net.the_hero_robot.redemption.impl.arcane_kings.evocation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public class EvocationSpell {
    public static final Codec<EvocationSpell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("color", 0).forGetter(EvocationSpell::getColor),
            Codec.INT.optionalFieldOf("cooldown", 0).forGetter(EvocationSpell::getCooldown),
            Codec.STRING.optionalFieldOf("name", "").forGetter(EvocationSpell::getName)
    ).apply(instance, EvocationSpell::new));

    public static final PacketCodec<ByteBuf, EvocationSpell> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, EvocationSpell::getColor,
            PacketCodecs.VAR_INT, EvocationSpell::getCooldown,
            PacketCodecs.STRING, EvocationSpell::getName,
            EvocationSpell::new
    );

    private final int color;
    private final int cooldown;
    private final String name;

    public EvocationSpell(int color, int cooldown, String name) {
        this.color = color;
        this.cooldown = cooldown;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public void use(PlayerEntity player, ItemStack stack, World world) {}
}
