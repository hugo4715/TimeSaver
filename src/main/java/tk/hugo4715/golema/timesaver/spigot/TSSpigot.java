package tk.hugo4715.golema.timesaver.spigot;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.TimeSaverCommon;
import tk.hugo4715.golema.timesaver.jedis.JedisCredentials;
import tk.hugo4715.golema.timesaver.jedis.JedisIdPool;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerStatus;
import tk.hugo4715.golema.timesaver.server.ServerType;
import tk.hugo4715.golema.timesaver.server.WhiteListType;
import tk.hugo4715.golema.timesaver.spigot.heartbeat.HeartBeatRunnable;

public class TSSpigot extends JavaPlugin {
	
	private TimeSaverCommon common;
	private  ServerInfo currentServerInfos;
	private JedisIdPool idPool;
	
	private BukkitTask heartBeatTask;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		JedisCredentials cred = new JedisCredentials(getConfig().getString("redis.host"),
				getConfig().getInt("redis.port"), getConfig().getString("redis.password"),getConfig().getBoolean("redis.use-pass"));
		common = new TimeSaverCommon(getLogger(), cred);
		
		idPool = new JedisIdPool(getCommon().getJedisAccess());
	}

	public void registerServer(ServerType serverType) {
		// create basic server infos, they should be modified by game plugins in order
		// to set the server game map and infos
		try {
			UUID uuid = UUID.fromString(getConfig().getString("server-uuid"));
			UUID coordinator = UUID.fromString(getConfig().getString("coordinator-uuid"));
			currentServerInfos = new ServerInfo(serverType, idPool.nextId(), Bukkit.getMaxPlayers(), Bukkit.getOnlinePlayers().size(), Bukkit.getIp(), Bukkit.getPort(), uuid, coordinator, ServerStatus.REBOOT, "None", null, true, WhiteListType.NONE, null);

		} catch (Exception e) {
			e.printStackTrace();
			
			currentServerInfos = new ServerInfo(serverType, idPool.nextId(), Bukkit.getMaxPlayers(), Bukkit.getOnlinePlayers().size(), Bukkit.getIp(), Bukkit.getPort(), UUID.randomUUID(), UUID.randomUUID(), ServerStatus.REBOOT, "None", null, true, WhiteListType.NONE, null);
			
		}
		
		// launch hearthbeat runnable
		heartBeatTask = new HeartBeatRunnable().runTaskTimerAsynchronously(this, 20, 20);
	}

	@Override
	public void onDisable() {
		heartBeatTask.cancel();
		idPool.returnId(getCurrentServerInfos().getId());
		unregisterServer();
	}

	private void unregisterServer() {
		try (Jedis j = getCommon().getJedisAccess().getJedisPool().getResource()) {
			String key = "TS:SERVERS:" + TSSpigot.get().getCurrentServerInfos().getId();
			j.del(key);
			j.srem(key, getCurrentServerInfos().getId()+"");
		}
	}

	public TimeSaverCommon getCommon() {
		return common;
	}

	public ServerInfo getCurrentServerInfos() {
		return currentServerInfos;
	}

	public static TSSpigot get() {
		return getPlugin(TSSpigot.class);
	}
}
