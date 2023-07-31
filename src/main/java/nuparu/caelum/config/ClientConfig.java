package nuparu.caelum.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.LongValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;


public class ClientConfig {
    public static LongValue lunarOrbitPeriod;
    public static LongValue earthOrbitPeriod;
    public static EnumValue<StarsType> starsType;
    public static EnumValue<LatitudeEffects> latitudeEffects;
    public static EnumValue<WorldHeightType> worldHeightType;
    public static LongValue minZ;
    public static LongValue maxZ;
    public static LongValue minZMargin;
    public static LongValue maxZMargin;
    public static DoubleValue maxMagnitude;
    public static DoubleValue starBrightness;
    public static BooleanValue starColors;
    public static DoubleValue starSize;


    public static void init(ForgeConfigSpec.Builder client) {
        lunarOrbitPeriod = client.comment("How long does it take for the Moon to complete its orbit in ticks.").defineInRange("moon.orbit_period",708734L, 1, Long.MAX_VALUE);
        earthOrbitPeriod = client.comment("How long does it take for the Earth to complete its orbit in ticks.").defineInRange("earth.orbit_period",8765812L, 0, Long.MAX_VALUE);
        starsType = client.comment("What stars to use").defineEnum("stars.stars_type", StarsType.CUSTOM, StarsType.values());
        latitudeEffects = client.comment("What should be affected by the camera latitude (the z coordinate)").defineEnum("latitude.latitude_effects", LatitudeEffects.ALL, LatitudeEffects.values());
        worldHeightType = client.comment("Should the northernmost/southernmost points be defined by the world border or by constants here in the config").defineEnum("latitude.world_height_type", WorldHeightType.BORDER, WorldHeightType.values());
        minZ = client.comment("The min Z - basically what Z is considered to be the north pole").defineInRange("latitude.min_z",-300000000L, Long.MIN_VALUE, Long.MAX_VALUE);
        maxZ = client.comment("The max Z - basically what Z is considered to be the south pole").defineInRange("latitude.max_z",300000000L, Long.MIN_VALUE, Long.MAX_VALUE);
        minZMargin = client.comment("Shifts the min Z. The idea is that the day-night cycle breaks near poles, so this can help by moving the poles out of the map").defineInRange("latitude.min_z_margin",0, 0, Long.MAX_VALUE);
        maxZMargin = client.comment("Shifts the max Z. The idea is that the day-night cycle breaks near poles, so this can help by moving the poles out of the map").defineInRange("latitude.max_z_margin",0, 0, Long.MAX_VALUE);
        maxMagnitude = client.comment("Stars with magnitude above this value won't be rendered.").defineInRange("stars.mag_magnitude",5, 0, Double.MAX_VALUE);
        starBrightness = client.comment("Controls the brightness/opacity of the colors.").defineInRange("stars.star_brightness",2, 0, Double.MAX_VALUE);
        starColors = client.comment("Should stars have colors?").define("stars.star_colors",true);
        starSize = client.comment("Controls the size of the (custom) stars").defineInRange("stars.star_size",1, 0, Double.MAX_VALUE);
    }
}
