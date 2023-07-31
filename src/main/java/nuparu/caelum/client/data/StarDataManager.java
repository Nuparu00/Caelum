package nuparu.caelum.client.data;

import com.google.gson.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import nuparu.caelum.Caelum;
import nuparu.caelum.config.ClientConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarDataManager extends SimpleJsonResourceReloadListener {


    public static VertexBuffer vanillaStarBuffer = null;

    private static final Gson GSON = (new GsonBuilder()).create();
    public static final StarDataManager INSTANCE = new StarDataManager();

    @Nullable
    private VertexBuffer starBuffer;

    public StarDataManager() {
        super(GSON, "stars");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        List<StellarData> stars = new ArrayList<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            DataResult<StellarDataList> dataResult = StellarDataList.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
            dataResult.get().ifLeft(stellarDataList -> {
                if (stellarDataList.replace()) {
                    stars.clear();
                }
                stars.addAll(stellarDataList.stars());
            }).ifRight((p_248506_) -> {
                Caelum.LOGGER.warn("Failed to read stellar data {}", p_248506_.message());
            });
        }
        createStars(stars);
    }

    private record StellarDataList(boolean replace, List<StellarData> stars) {
        public static final Codec<StellarDataList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("replace", false).forGetter(StellarDataList::replace),
                StellarData.CODEC.listOf().fieldOf("stars").forGetter(StellarDataList::stars)
        ).apply(instance, StellarDataList::new));
    }

    private record StellarData(String name, double ascension, double declination, double magnitude, String color) {
        public static final Codec<StellarData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.optionalFieldOf("name", "").forGetter(StellarData::name),
                Codec.DOUBLE.fieldOf("ascension").forGetter(StellarData::ascension),
                Codec.DOUBLE.fieldOf("declination").forGetter(StellarData::declination),
                Codec.DOUBLE.fieldOf("magnitude").forGetter(StellarData::magnitude),
                Codec.STRING.optionalFieldOf("color", "#ffffff").forGetter(StellarData::color)).apply(instance, StellarData::new));
    }

    private void createStars(List<StellarData> stars) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        if (starBuffer != null) {
            starBuffer.close();
        }

        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder, stars);
        starBuffer.bind();
        starBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.RenderedBuffer drawStars(BufferBuilder bufferBuilder, List<StellarData> stars) {
        RandomSource randomSource = RandomSource.create(10842L);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        double maxMagnitude = stars.stream().mapToDouble(StellarData::magnitude).max().orElse(1.0);
        double minMagnitude = stars.stream().mapToDouble(StellarData::magnitude).min().orElse(0.0);

        double shift = Math.abs(minMagnitude);
        maxMagnitude += shift;

        double logMinMagnitude = 0; // Ensure non-negative
        double logMaxMagnitude = Math.log10(maxMagnitude); // Ensure non-negative and shift max


        for (StellarData star : stars) {
            double magnitude = star.magnitude();
            if (magnitude > ClientConfig.maxMagnitude.get()) continue;

            double d0 = Math.cos(star.declination()) * Math.cos(star.ascension());
            double d1 = Math.cos(star.declination()) * Math.sin(star.ascension());
            double d2 = Math.sin(star.declination());

            double logMagnitude = Math.log10(magnitude + shift);
            double normalizedMagnitude = (logMagnitude - logMinMagnitude) / (logMaxMagnitude - logMinMagnitude);


            double size = (0.6 - normalizedMagnitude * 0.5) * ClientConfig.starSize.get();

            if (size <= 0.0) continue;

            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            d4 = 1.0D / Math.sqrt(d4);
            d0 *= d4;
            d1 *= d4;
            d2 *= d4;
            double d5 = d0 * 100.0D;
            double d6 = d1 * 100.0D;
            double d7 = d2 * 100.0D;
            double d8 = Math.atan2(d0, d2);
            double d9 = Math.sin(d8);
            double d10 = Math.cos(d8);
            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
            double d12 = Math.sin(d11);
            double d13 = Math.cos(d11);
            double d14 = randomSource.nextDouble() * Math.PI * 2.0D;
            double d15 = Math.sin(d14);
            double d16 = Math.cos(d14);


            int red = Integer.parseInt(star.color().substring(0, 2), 16);
            int green = Integer.parseInt(star.color().substring(2, 4), 16);
            int blue = Integer.parseInt(star.color().substring(4, 6), 16);

            if (!ClientConfig.starColors.get()) {
                red = 255;
                green = 255;
                blue = 255;
            }

            for (int j = 0; j < 4; ++j) {
                double d18 = (double) ((j & 2) - 1) * size;
                double d19 = (double) ((j + 1 & 2) - 1) * size;
                double d21 = d18 * d16 - d19 * d15;
                double d22 = d19 * d16 + d18 * d15;
                double d23 = d21 * d12 + 0.0D * d13;
                double d24 = 0.0D * d12 - d21 * d13;
                double d25 = d24 * d9 - d22 * d10;
                double d26 = d22 * d9 + d24 * d10;

                bufferBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).color(red, green, blue, (int) ((1 - normalizedMagnitude) * 255)).endVertex();
            }
        }

        return bufferBuilder.end();
    }

    public VertexBuffer getStarBuffer() {
        return starBuffer;
    }

}
