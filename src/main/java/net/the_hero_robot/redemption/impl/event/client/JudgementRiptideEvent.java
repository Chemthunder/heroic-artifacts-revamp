package net.the_hero_robot.redemption.impl.event.client;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.tag.RedemptionItemTags;

import java.util.Optional;

public class JudgementRiptideEvent implements CustomRiptideEvent {
    public Optional<Identifier> getRiptideTexture(PlayerEntity player, ItemStack stack) {
        return stack.isIn(RedemptionItemTags.KATANAS) ? Optional.of(Redemption.id("textures/entity/rush_of_souls.png")) : Optional.empty();
    }
}
