package silly.chemthunder.redemption.impl.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import silly.chemthunder.redemption.impl.cca.entity.EnshroudedComponent;
import silly.chemthunder.redemption.impl.cca.entity.ImmolationComponent;
import silly.chemthunder.redemption.impl.cca.entity.ImmolationHolderComponent;
import silly.chemthunder.redemption.impl.cca.entity.JudgementComponent;
import silly.chemthunder.redemption.impl.cca.entity.flash.FlashComponent;
import silly.chemthunder.redemption.impl.cca.entity.flash.JudgementFlashComponent;

public class RedemptionComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, EnshroudedComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(EnshroudedComponent::new);
        registry.beginRegistration(PlayerEntity.class, JudgementComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(JudgementComponent::new);
        registry.beginRegistration(LivingEntity.class, JudgementFlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(JudgementFlashComponent::new);
        registry.beginRegistration(LivingEntity.class, FlashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(FlashComponent::new);

        // Immolation
        registry.beginRegistration(PlayerEntity.class, ImmolationHolderComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(ImmolationHolderComponent::new);
        registry.beginRegistration(PlayerEntity.class, ImmolationComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ImmolationComponent::new);
    }
}
