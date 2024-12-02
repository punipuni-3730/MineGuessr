package prj.salmon.mineguessr;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import prj.salmon.mineguessr.Commands.MineGuessrCommand;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main extends JavaPlugin {
    private DynmapAPI dynmapAPI;
    private HttpServer httpServer;

    @Override
    public void onEnable() {
        getLogger().info("MineGuessr has been enabled!");

        // Dynmap接続
        Plugin dynmap = getServer().getPluginManager().getPlugin("dynmap");
        if (dynmap instanceof DynmapAPI) {
            dynmapAPI = (DynmapAPI) dynmap;
            getLogger().info("Dynmap API connected successfully!");
        } else {
            getLogger().warning("Dynmap is not installed or failed to connect!");
        }

        // コマンド登録
        getCommand("mineguessr").setExecutor(new MineGuessrCommand(this));

        // HTTPサーバーの起動
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/mineguessr/click", new ClickHandler());
            httpServer.setExecutor(null);
            httpServer.start();
            getLogger().info("HTTP Server started on port 8080.");
        } catch (IOException e) {
            getLogger().severe("Failed to start HTTP Server: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MineGuessr has been disabled!");

        // HTTPサーバーの停止
        if (httpServer != null) {
            httpServer.stop(0);
            getLogger().info("HTTP Server stopped.");
        }
    }

    public DynmapAPI getDynmapAPI() {
        return dynmapAPI;
    }

    // HTTPリクエストを処理するハンドラー
    static class ClickHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // CORS対応
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // リクエストボディを取得
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                System.out.println("Received POST data: " + requestBody);

                // 必要に応じてリクエストデータを解析して処理
                // 例: 座標情報を検証し、ゲームロジックに適用する

                // レスポンスを返す
                String response = "{\"status\":\"success\"}";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // POST以外のリクエストは405を返す
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }
}
