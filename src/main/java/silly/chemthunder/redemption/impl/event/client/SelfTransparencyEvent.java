package silly.chemthunder.redemption.impl.event.client;

import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import silly.chemthunder.redemption.impl.cca.entity.EnshroudedComponent;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class SelfTransparencyEvent implements PlayerOpacityEvent {
    public Optional<Double> getOpacity(PlayerEntity player) {
        if (EnshroudedComponent.KEY.get(player).isShrouded()) {
            return player == MinecraftClient.getInstance().player ? Optional.of(0.4) : Optional.of(0.0);
        }

        return Optional.empty();
    }
}
