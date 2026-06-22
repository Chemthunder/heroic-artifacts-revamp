package net.the_hero_robot.redemption.impl.cca;

import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.cca.entity.flash.FlashComponent;
import net.the_hero_robot.redemption.impl.cca.entity.flash.JudgementFlashComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class RedemptionComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(FlashComponent.KEY, FlashComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(JudgementFlashComponent.KEY, JudgementFlashComponent::new, RespawnCopyStrategy.NEVER_COPY);

        registry.registerForPlayers(EnshroudedComponent.KEY, EnshroudedComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(JudgementComponent.KEY, JudgementComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
