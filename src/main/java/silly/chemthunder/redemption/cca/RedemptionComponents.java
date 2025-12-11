package silly.chemthunder.redemption.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class RedemptionComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, EnshroudedPlayerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(EnshroudedPlayerComponent::new);
        registry.beginRegistration(PlayerEntity.class, JudgementPlayerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(JudgementPlayerComponent::new);
        registry.beginRegistration(LivingEntity.class, JudgementFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(JudgementFlashComponent::new);
        registry.beginRegistration(LivingEntity.class, FlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(FlashComponent::new);
    }
}
