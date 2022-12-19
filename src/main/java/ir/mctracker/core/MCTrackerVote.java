package ir.mctracker.core;

import com.j256.ormlite.dao.Dao;
import ir.jeykey.megacore.MegaPlugin;
import ir.jeykey.megacore.config.premade.Storage;
import ir.mctracker.core.commands.TrackerCommand;
import ir.mctracker.core.commands.VoteCommand;
import ir.mctracker.core.config.Config;
import ir.mctracker.core.config.Messages;
import ir.mctracker.core.database.DataSource;
import ir.mctracker.core.database.models.Vote;
import ir.mctracker.core.tasks.FetchAPI;
import ir.mctracker.core.tasks.RedeemRewards;
import ir.mctracker.core.utilities.Metrics;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

public final class MCTrackerVote extends JavaPlugin {
    @Getter @Setter private static Dao<Vote,String> votesDao;

    @Override
    public void onEnable() {
        // Setup configuration files
        getServer().getServicesManager().getRegistration(Storage.class);
        getServer().getServicesManager().getRegistration(Config.class);
        getServer().getServicesManager().getRegistration(Messages.class);

        // Setting up datasource
        try {
            if (Storage.LOCATION.equalsIgnoreCase("sqlite")) {
                DataSource.SQLite();
            } else if (Storage.LOCATION.equalsIgnoreCase("mysql")) {
                DataSource.MySQL();
            } else {
                getLogger().severe( "&cStorage type defined in config (" + Storage.LOCATION + ") is not valid!");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            getLogger().severe( "&cPlugin could not work with database! [ Check Stack Trace For More Information ]");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        catch (IOException exception) {
            exception.printStackTrace();
            getLogger().severe("&cPlugin is unable to create database file, Please check directory permissions [ Check Stack Trace For More Information ]");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register commands
        this.getCommand("tracker").setExecutor(new TrackerCommand());
        this.getCommand("vote").setExecutor(new VoteCommand());

        // Booting tasks
        new FetchAPI().runTaskTimerAsynchronously(this, 0, Config.CYCLE);
        new RedeemRewards().runTaskTimer(this, 0, Config.CYCLE / 2);

        // Setting up metrics
        new Metrics(this, 12780);
    }

}
