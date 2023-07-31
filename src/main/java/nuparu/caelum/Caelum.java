package nuparu.caelum;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import nuparu.caelum.config.ConfigHelper;
import org.slf4j.Logger;

@Mod(Caelum.MODID)
public class Caelum
{
    public static final String MODID = "caelum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Caelum()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHelper.clientConfig);
    }
}
