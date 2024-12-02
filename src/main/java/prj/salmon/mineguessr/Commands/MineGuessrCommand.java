package prj.salmon.mineguessr.Commands;

import prj.salmon.mineguessr.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class MineGuessrCommand implements CommandExecutor {
    private final Main plugin;

    public MineGuessrCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is for players only.");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        // ランダムな安全な場所を選択
        Location randomLocation = getRandomSafeLocation(world);
        player.teleport(randomLocation);

        // プレイヤーに通知
        player.sendMessage("You have been teleported to a random location in MineGuessr!");
        return true;
    }

    private Location getRandomSafeLocation(World world) {
        Random random = new Random();
        int x = random.nextInt(10000) - 5000;
        int z = random.nextInt(10000) - 5000;
        int y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }
}
