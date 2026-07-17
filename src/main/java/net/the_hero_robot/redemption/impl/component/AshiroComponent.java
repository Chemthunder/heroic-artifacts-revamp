package net.the_hero_robot.redemption.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.index.RedemptionDataComponents;
import net.the_hero_robot.redemption.impl.util.ModUtil;

import java.util.List;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public record AshiroComponent(RegistryKey<World> world, Vec3d pos) {
    public static final AshiroComponent DEFAULT = new AshiroComponent(World.OVERWORLD, Vec3d.ZERO);

    public static final Codec<AshiroComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            World.CODEC.fieldOf("world").forGetter(AshiroComponent::world),
            Vec3d.CODEC.fieldOf("pos").forGetter(AshiroComponent::pos)
    ).apply(instance, AshiroComponent::new));

    public static final PacketCodec<PacketByteBuf, AshiroComponent> PACKET_CODEC = PacketCodec.tuple(
            RegistryKey.createPacketCodec(RegistryKeys.WORLD), AshiroComponent::world,
            ModUtil.VEC3D_PACKET_CODEC, AshiroComponent::pos,
            AshiroComponent::new
    );

    public static AshiroComponent get(ItemStack stack) {
        return stack.getOrDefault(RedemptionDataComponents.ASHIRO, DEFAULT);
    }

    public AshiroComponent withWorld(RegistryKey<World> world) {
        return new AshiroComponent(world, this.pos);
    }

    public AshiroComponent withPos(Vec3d pos) {
        return new AshiroComponent(this.world, pos);
    }
}
