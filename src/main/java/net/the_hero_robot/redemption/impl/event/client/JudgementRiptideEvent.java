package net.the_hero_robot.redemption.impl.event.client;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.tag.RedemptionItemTags;

import java.util.Optional;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class JudgementRiptideEvent implements CustomRiptideEvent {
    public static final Identifier TEXTURE = Redemption.id("textures/entity/rush_of_souls.png");
    public Optional<Identifier> getRiptideTexture(PlayerEntity player, ItemStack stack) {
        return stack.isIn(RedemptionItemTags.KATANAS) ? Optional.of(TEXTURE) : Optional.empty();
    }
}
