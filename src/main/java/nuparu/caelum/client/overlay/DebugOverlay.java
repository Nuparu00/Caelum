package nuparu.caelum.client.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import nuparu.caelum.client.SkyUtils;
import org.joml.Vector3f;

public class DebugOverlay extends Gui implements IGuiOverlay {
    public DebugOverlay(Minecraft minecraft) {
        super(minecraft,minecraft.getItemRenderer());
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int width, int height) {

        if (!minecraft.options.hideGui && !minecraft.options.renderDebug && minecraft.getCameraEntity() instanceof Player player) {
            int line = 0;
            double z = minecraft.getCameraEntity().position().z();
            graphics.drawString(minecraft.font,"Sun Height " + SkyUtils.sunHeight(player.level(),z,partialTick),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Sunrise Rotation " + SkyUtils.getSunriseColorRotation(player.level(),z,partialTick),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Time of day " + minecraft.level.getTimeOfDay(partialTick),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Sun Vector " + SkyUtils.sunVector(player.level(),z, partialTick),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Latitude Rotation " + SkyUtils.starLatitudeRotation(player.level(),z),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Sun Angle " + (player.level().getSunAngle(partialTick) * Mth.RAD_TO_DEG),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Sun Vector " + SkyUtils.sunVector(player.level(),z, partialTick).angle(new Vector3f(1,0,0)),0,10*(line++),0xffffff, true);
            graphics.drawString(minecraft.font,"Tilt Angle " + (SkyUtils.starLatitudeRotation(player.level(),z) * 180),0,10*(line++),0xffffff, true);
        }
    }
}
