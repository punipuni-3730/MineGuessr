package prj.salmon.mineguessr.Utils;

public class LocationUtils {
    public static double calculateDistance(int x1, int z1, int x2, int z2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2));
    }
}
