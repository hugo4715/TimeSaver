package tk.hugo4715.golema.timesaver.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import tk.hugo4715.golema.timesaver.TimeSaverCommon;
import tk.hugo4715.golema.timesaver.bungee.runnable.UpdateThread;
import tk.hugo4715.golema.timesaver.jedis.JedisCredentials;

public class TSBungee extends Plugin {
	private static TSBungee instance;
	public static TSBungee getInstance() {
		return instance;
	}
	
	private net.md_5.bungee.config.Configuration config;
	private TimeSaverCommon common;
	private UpdateThread updateThread;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;
		loadConfig();
		
		JedisCredentials cred = new JedisCredentials(config.getString("redis.host"),config.getInt("redis.port"), config.getString("password"),config.getBoolean("redis.use-pass"));
		common = new TimeSaverCommon(getLogger(), cred);
		
		updateThread = new UpdateThread();
		getExecutorService().execute(updateThread);
	}


	private void loadConfig() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();

		File file = new File(getDataFolder(), "config.yml");

		if (!file.exists()) {
			try (InputStream in = getResourceAsStream("config-bungee.yml")) {
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				getLogger().severe("Cannot save default config!");
			}
		}
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().severe("Cannot load config!");
		}
	}
	
	public TimeSaverCommon getCommon() {
		return common;
	}
}
