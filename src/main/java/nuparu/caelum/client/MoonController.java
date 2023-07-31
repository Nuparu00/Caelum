package nuparu.caelum.client;

import nuparu.caelum.config.ClientConfig;

public record MoonController(long initialPosition, long orbitPeriod, int phases){

    public static MoonController MOON = new MoonController(0, ClientConfig.lunarOrbitPeriod.get(), 8);

    public float getMoonOrbitPosition(long gameTime) {
        return ((gameTime+initialPosition) % orbitPeriod)/(float)orbitPeriod;
    }

    public int getMoonPhase(long gameTime) {

        double orbitTime = getMoonOrbitPosition(gameTime);
        double eighth = 1/8d;

        int stage  = (int) Math.ceil(orbitTime*7);
        if(orbitTime < eighth/2d || orbitTime > 1-eighth/2d){
            stage = 0;
        }

        return stage == 0 ? stage : 8 - stage;
    }
}
