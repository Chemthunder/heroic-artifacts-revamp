package silly.chemthunder.redemption.index;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.block.entity.FinalAceBlockEntity;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public interface RedemptionBlockEntities {
    Map<BlockEntityType<?>, Identifier> BLOCK_ENTITIES = new LinkedHashMap<>();

    BlockEntityType<FinalAceBlockEntity> FINAL_ACE = create("final_ace", FabricBlockEntityTypeBuilder
            .create(FinalAceBlockEntity::new)
            .addBlocks(RedemptionBlocks.FINAL_ACE)
            .build());

    private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> blockEntity) {
        BLOCK_ENTITIES.put(blockEntity, Redemption.id(name));
        return blockEntity;
    }

    static void index() {
        BLOCK_ENTITIES.keySet().forEach(blockEntity -> {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCK_ENTITIES.get(blockEntity), blockEntity);
        });
    }
}
