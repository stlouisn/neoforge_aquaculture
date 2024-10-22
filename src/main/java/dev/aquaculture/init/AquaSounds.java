package dev.aquaculture.init;

import dev.aquaculture.Aquaculture;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class AquaSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(Registries.SOUND_EVENT, Aquaculture.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> TACKLE_BOX_OPEN = registerSound("tackle_box_open");
    public static final DeferredHolder<SoundEvent, SoundEvent> TACKLE_BOX_CLOSE = registerSound("tackle_box_close");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOBBER_LAND_IN_LAVA = registerSound("bobber_land_lava");
    public static final DeferredHolder<SoundEvent, SoundEvent> JELLYFISH_FLOP = registerSound("jellyfish_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> FISH_FLOP = registerSound("fish_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> FISH_AMBIENT = registerSound("fish_ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> FISH_DEATH = registerSound("fish_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> FISH_HURT = registerSound("fish_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> JELLYFISH_COLLIDE = registerSound("jellyfish_collide");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Aquaculture.MOD_ID, name);
        return SOUND_EVENT_DEFERRED.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}