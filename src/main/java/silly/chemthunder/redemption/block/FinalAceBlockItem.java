package silly.chemthunder.redemption.block;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import silly.chemthunder.redemption.index.RedemptionBlocks;

public class FinalAceBlockItem extends BlockItem implements ColorableItem {
    public FinalAceBlockItem(Settings settings) {
        super(RedemptionBlocks.FINAL_ACE, settings);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFFd70048;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF8e1a41;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF01c0810;
    }
}
