package silly.chemthunder.redemption.index;

import net.acoyt.acornlib.impl.item.TranslationBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.block.FinalAceBlock;

import java.util.function.Function;

public interface RedemptionBlocks {

    Block FINAL_ACE = createWithItem("final_ace", FinalAceBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
            .sounds(BlockSoundGroup.BONE)
            .dropsNothing()
    );

    static Block create(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, Redemption.id(name), block);
    }

    static Block createWithItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = create(name, factory, settings);
        RedemptionItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), new Item.Settings().maxCount(1));
        return block;
    }

    static void index() {
    }
}
