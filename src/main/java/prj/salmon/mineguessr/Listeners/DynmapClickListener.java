package prj.salmon.mineguessr.Listeners;

import prj.salmon.mineguessr.Main;

public class DynmapClickListener {
    private final Main plugin;

    public DynmapClickListener(Main plugin) {
        this.plugin = plugin;
    }

    public void handleMapClick(String playerName, int x, int z) {
        plugin.getLogger().info(playerName + " clicked at (" + x + ", " + z + ")");
        // 実際の座標との距離を計算
        // ここでランダム地点と比較
    }
}
