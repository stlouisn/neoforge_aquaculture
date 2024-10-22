package dev.aquaculture.misc;

import com.electronwill.nightconfig.core.file.FileConfig;
import java.io.File;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AquaConfig {

  public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
  public static final BasicOptions BASIC_OPTIONS = new BasicOptions(BUILDER);

  public static class BasicOptions {

    static final String BASIC_OPTIONS = "basic options";
    public ModConfigSpec.BooleanValue randomWeight;
    public ModConfigSpec.IntValue fishSpawnLevelModifier;

    BasicOptions(ModConfigSpec.Builder builder) {
      builder.push(BASIC_OPTIONS);
      randomWeight = builder.define("Enable weight for fish? Useful for fishing competitions", true);
      fishSpawnLevelModifier = builder.defineInRange("How many blocks below sea level Aquaculture fish can spawn", -13, -63, 0);
      builder.pop();
    }
  }

  public static ModConfigSpec spec = BUILDER.build();

  @SuppressWarnings("unused")
  public static class Helper {

    private static final FileConfig CONFIG_FILE = FileConfig.of(new File(FMLPaths.CONFIGDIR.get().toFile(), "aquaculture-common.toml"));

    public static <T> T get(String category, String subCategory, String value) {
      return get(category + "." + subCategory, value);
    }

    public static <T> T get(String category, String value) {
      T configEntry = CONFIG_FILE.get(category + "." + value);
      if (configEntry == null) {
        CONFIG_FILE.load();
        return CONFIG_FILE.get(category + "." + value);
      }
      else {
        return configEntry;
      }
    }

    public static String getSubConfig(String category, String subCategory) {
      return category + "." + subCategory;
    }
  }
}