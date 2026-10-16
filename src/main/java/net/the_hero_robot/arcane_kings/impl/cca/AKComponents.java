package net.the_hero_robot.arcane_kings.impl.cca;

import net.the_hero_robot.arcane_kings.impl.cca.entity.PetrificationComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

/**
 * @author AcoYT
 */
public class AKComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PetrificationComponent.KEY, PetrificationComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
