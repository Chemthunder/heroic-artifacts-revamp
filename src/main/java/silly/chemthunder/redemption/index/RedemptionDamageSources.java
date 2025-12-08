package silly.chemthunder.redemption.index;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import silly.chemthunder.redemption.Redemption;

public interface RedemptionDamageSources {
    RegistryKey<DamageType> KATANA = of("katana");

    static DamageSource katana(LivingEntity entity) {
        return entity.getDamageSources().create(KATANA); }

    private static RegistryKey<DamageType> of(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Redemption.id(name));
    }
}
