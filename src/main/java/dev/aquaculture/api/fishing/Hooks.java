package dev.aquaculture.api.fishing;

import dev.aquaculture.init.AquaSounds;
import net.minecraft.ChatFormatting;

public class Hooks {

    public static final Hook EMPTY = new Hook.HookBuilder().build();

    public static final Hook IRON = new Hook.HookBuilder("iron").setDurabilityChance(0.20).setColor(ChatFormatting.GRAY).build();
    public static final Hook GOLD = new Hook.HookBuilder("gold").setColor(ChatFormatting.GOLD).setLuckModifier(1).build();
    public static final Hook DIAMOND = new Hook.HookBuilder("diamond").setColor(ChatFormatting.AQUA).setDurabilityChance(0.50).build();
    public static final Hook DOUBLE = new Hook.HookBuilder("double").setColor(ChatFormatting.DARK_GRAY).setDoubleCatchChance(0.10).build();
    public static final Hook REDSTONE = new Hook.HookBuilder("redstone").setColor(ChatFormatting.RED).setCatchableWindow(35, 70).build();

    public static void load() {}
}