package silly.chemthunder.redemption.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public record AshiroComponent(RegistryKey<World> dimension, Vec3d pos) {
    public static final Codec<AshiroComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            World.CODEC.fieldOf("dimension").forGetter(AshiroComponent::dimension),
            Vec3d.CODEC.fieldOf("pos").forGetter(AshiroComponent::pos)
    ).apply(instance, AshiroComponent::new));

    public static final PacketCodec<ByteBuf, AshiroComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
