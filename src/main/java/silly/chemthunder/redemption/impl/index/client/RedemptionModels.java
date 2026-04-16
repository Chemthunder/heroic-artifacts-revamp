package silly.chemthunder.redemption.impl.index.client;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import silly.chemthunder.redemption.impl.Redemption;

import java.util.Optional;

public interface RedemptionModels {
    Model KATANA_IN_HAND = create("katana_in_hand", TextureKey.LAYER0);
    Model SHEATH_IN_HAND = create("sheath_in_hand", TextureKey.LAYER0);
    Model SHEATHED_IN_HAND = create("sheathed_in_hand", TextureKey.LAYER0);
    Model LARGE_KATANA_IN_HAND = create("large_katana_in_hand", TextureKey.LAYER0);
    Model LARGE_SHEATH_IN_HAND = create("large_sheath_in_hand", TextureKey.LAYER0);

    static Model create(String parent,  TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Redemption.id("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}
