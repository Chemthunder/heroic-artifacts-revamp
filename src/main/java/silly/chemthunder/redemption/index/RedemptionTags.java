package silly.chemthunder.redemption.index;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import silly.chemthunder.redemption.Redemption;

public interface RedemptionTags {
    TagKey<Item> KATANAS = itemOf("katanas");

    static TagKey<Item> itemOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Redemption.id(id));
    }
}
