package princ.anemos.util;

public class UnitValueConverter {
    public static int fromPercent(int i) {
        return i / 100;
    }

    public static int toPercent(int i) {
        return i * 100;
    }

    public static double fromPercent(double d) {
        return d / 100.0;
    }

    public static double toPercent(double d) {
        return d * 100.0;
    }

    public static float fromPercent(float f) {
        return f / 100.0F;
    }

    public static float toPercent(float f) {
        return f * 100.0F;
    }

    public static float fromFloatByte(float f) {
        return f / 255;
    }

    public static float toFloatByte(float f) {
        return f * 255;
    }
}
