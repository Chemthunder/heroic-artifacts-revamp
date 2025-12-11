package silly.chemthunder.redemption.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import silly.chemthunder.redemption.index.RedemptionBlockEntities;

public class FinalAceBlockEntity extends BlockEntity {
    public FinalAceBlockEntity(BlockPos pos, BlockState state) {
        super(RedemptionBlockEntities.FINAL_ACE, pos, state);
    }


}
