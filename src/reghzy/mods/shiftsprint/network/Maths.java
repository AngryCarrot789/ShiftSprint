package reghzy.mods.shiftsprint.network;

import com.sun.javafx.geom.Vec3d;
import sun.misc.DoubleConsts;
import sun.misc.FloatConsts;

public class Maths {
    // public static int pow(int base, int exp) {
    //     int result = 1;
    //     while (true) {
    //         if ((exp & 1) != 0)
    //             result *= base;
    //         exp >>= 1;
    //         if (exp == 0)
    //             break;
    //         base *= base;
    //     }
    //     return result;
    // }

    public static int pow(int value, int exponent) {
        while (exponent > 1) {
            exponent--;
            value *= value;
        }

        return value;
    }

    public static double round(double value, int places) {
        if (places <= 0) {
            return Math.round(value);
        }

        long factor = (long) Math.pow(10, places);
        long rounded = Math.round(value * factor);
        return (double) rounded / factor;
    }

    public static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean between(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static boolean between(long value, long min, long max) {
        return value >= min && value <= max;
    }

    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }


    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }

    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }

    public static int min3(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int max3(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    /**
     * Clamps the given value to the highest value of the given multiple (e.g getMaxOfMultiple(37, 16) == 48)
     * @param value    The value
     * @param multiple The multiple
     * @return The value (will never be more than value + multiple, it will always be below)
     */
    public static int maxOfMultiple(int value, int multiple) {
        int div = value % multiple;
        if (div == 0) {
            return value;
        }
        else {
            return value + (multiple - div);
        }
    }

    /**
     * Linear interpolation between 2 values,
     * @param from       Start value
     * @param to         End value
     * @param multiplier Lerp multiplier
     */
    public static double lerp(double from, double to, double multiplier) {
        return from + multiplier * (to - from);
    }

    public static Vec3d lerp(Vec3d from, Vec3d to, double multiplier) {
        return new Vec3d(from.x + multiplier * (to.x - from.x),
                         from.y + multiplier * (to.y - from.y),
                         from.z + multiplier * (to.z - from.z));
    }

    private static final double D_SWS1 = DoubleConsts.SIGNIFICAND_WIDTH - 1;
    private static final float F_SWS1 = FloatConsts.SIGNIFICAND_WIDTH - 1;

    public static long floor(double value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return (int) ((value == 0.0) ? value : ((value < 0.0) ? -1.0 : 0.0));
        }
        else if (exponent >= D_SWS1) { // significant width - 1
            return (int) value;
        }

        long bits = Double.doubleToRawLongBits(value);
        long mask = DoubleConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0L) {
            return (int) value;
        }
        else {
            double result = Double.longBitsToDouble(bits & (~mask));
            if (-1.0 * value > 0.0) {
                result = result + -1.0;
            }

            return (int) result;
        }
    }

    public static int ifloor(double value) {
        return (int) floor(value);
    }

    public static int floor(float value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return (int) ((value == 0.0f) ? value : ((value < 0.0f) ? -1.0 : 0.0));
        }
        else if (exponent >= F_SWS1) { // significant width - 1
            return (int) value;
        }

        int bits = Float.floatToRawIntBits(value);
        int mask = FloatConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0) {
            return (int) value;
        }
        else {
            float result = Float.intBitsToFloat(bits & (~mask));
            if (-1.0f * value > 0.0f) {
                result = result + -1.0f;
            }

            return (int) result;
        }
    }

    public static long ceil(double value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return ((value == 0.0) ? ((int) value) : ((value < 0.0) ? 0 : 1));
        }
        else if (exponent >= D_SWS1) {
            return (int) value;
        }

        long bits = Double.doubleToRawLongBits(value);
        long mask = DoubleConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0L) {
            return (int) value;
        }
        else {
            double result = Double.longBitsToDouble(bits & (~mask));
            return value > 0.0 ? (int) (result + 1.0) : (int) result;
        }
    }

    public static int iceil(double value) {
        return (int) ceil(value);
    }

    public static int ceil(float value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return ((value == 0.0f) ? ((int) value) : ((value < 0.0f) ? 0 : 1));
        }
        else if (exponent >= F_SWS1) {
            return (int) value;
        }

        int bits = Float.floatToRawIntBits(value);
        int mask = FloatConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0) {
            return (int) value;
        }
        else {
            float result = Float.intBitsToFloat(bits & (~mask));
            return value > 0.0f ? (int) (result + 1.0f) : (int) result;
        }
    }

    public static long fastFloor(double value) {
        return value > 0 ? (int) value : (int) value - 1;
    }

    public static int fastFloor(float value) {
        return value > 0 ? (int) value : (int) value - 1;
    }

    public static long fastCeil(double value) {
        int v = (int) value;
        if (value > v) {
            return v + 1;
        }

        return v;
    }

    public static int fastCeil(float value) {
        int v = (int) value;
        if (value > v) {
            return v + 1;
        }

        return v;
    }

    public static long round(double value) {
        return floor(value + 0.5);
    }

    public static int iround(double value) {
        return ifloor(value + 0.5);
    }

    public static int round(float value) {
        return floor(value + 0.5f);
    }
}
