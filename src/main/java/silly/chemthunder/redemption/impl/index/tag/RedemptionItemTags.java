package silly.chemthunder.redemption.impl.index.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import silly.chemthunder.redemption.impl.Redemption;

public interface RedemptionItemTags {
    TagKey<Item> KATANAS = of("katanas");

    static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Redemption.id(id));
    }
}
