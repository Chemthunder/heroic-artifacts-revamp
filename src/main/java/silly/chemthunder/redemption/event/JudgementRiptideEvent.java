package silly.chemthunder.redemption.event;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.index.RedemptionTags;

import java.util.Optional;

public class JudgementRiptideEvent implements CustomRiptideEvent {
    @Override
    public Optional<Identifier> getRiptideTexture(PlayerEntity player, ItemStack stack) {
        return stack.isIn(RedemptionTags.KATANAS) ? Optional.of(Redemption.id("textures/entity/rush_of_souls.png")) : Optional.empty();
    }
}
