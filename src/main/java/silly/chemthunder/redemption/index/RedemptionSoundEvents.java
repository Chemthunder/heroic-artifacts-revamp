package silly.chemthunder.redemption.index;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.Redemption;

public interface RedemptionSoundEvents {
    SoundEvent HUNTER_BLACKOUT = create("item.hunter.blackout");
    SoundEvent UNSHEATHE = create("item.unsheathe");
    SoundEvent BECOME_JUDGE = create("event.become_judge");
    SoundEvent JUDGE_DEATH = create("event.judge_death");

    private static SoundEvent create(String name) {
        Identifier id = Redemption.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    static void index() {
        // Sound Events are Registered Statically
    }
}
