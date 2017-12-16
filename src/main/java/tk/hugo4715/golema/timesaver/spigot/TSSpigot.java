package tk.hugo4715.golema.timesaver.spigot;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.TimeSaverCommon;
import tk.hugo4715.golema.timesaver.jedis.JedisCredentials;
import tk.hugo4715.golema.timesaver.jedis.JedisIdPool;
import tk.hugo4715.golema.timesaver.server.GameInfos;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerStatus;
import tk.hugo4715.golema.timesaver.server.ServerType;
import tk.hugo4715.golema.timesaver.server.WhiteListType;
import tk.hugo4715.golema.timesaver.spigot.heartbeat.HeartBeatRunnable;

public class TSSpigot extends JavaPlugin {

	private TimeSaverCommon common;
	private static ServerInfo currentServerInfos;
	private JedisIdPool idPool;

	private BukkitTask heartBeatTask;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		JedisCredentials cred = new JedisCredentials(getConfig().getString("redis.host"),
				getConfig().getInt("redis.port"), getConfig().getString("redis.password"),
				getConfig().getBoolean("redis.use-pass"));
		common = new TimeSaverCommon(getLogger(), cred);
		idPool = new JedisIdPool(getCommon().getJedisAccess());

		// launch hearthbeat runnable
		heartBeatTask = new HeartBeatRunnable().runTaskTimerAsynchronously(this, 40L, 20L);
		registerServer();
	}

	public void registerServer() {
		// create basic server infos, they should be modified by game plugins in order
		// to set the server game map and infos
		try {
			UUID uuid = UUID.fromString(getConfig().getString("server-uuid"));
			UUID coordinator = UUID.fromString(getConfig().getString("coordinator-uuid"));
			currentServerInfos = new ServerInfo(ServerType.NONE, idPool.nextId(), Bukkit.getMaxPlayers(),
					Bukkit.getOnlinePlayers().size(), Bukkit.getIp(), Bukkit.getPort(), uuid, coordinator,
					ServerStatus.REBOOT, GameInfos.NONE, null, false, WhiteListType.NONE, null, false);

		} catch (Exception e) {
			System.out.println("[Coordinator] Erreur des UUIDS.");
			currentServerInfos = new ServerInfo(ServerType.NONE, idPool.nextId(), Bukkit.getMaxPlayers(),
					Bukkit.getOnlinePlayers().size(), Bukkit.getIp(), Bukkit.getPort(), UUID.randomUUID(),
					UUID.randomUUID(), ServerStatus.REBOOT, GameInfos.NONE, null, false, WhiteListType.NONE, null, false);
		}
	}

	@Override
	public void onDisable() {
		heartBeatTask.cancel();
		unregisterServer();
		idPool.returnId(getCurrentServerInfos().getId());
	}

	private void unregisterServer() {
		try (Jedis j = getCommon().getJedisAccess().getJedisPool().getResource()) {
			String key = "TS:SERVERS:" + TSSpigot.getCurrentServerInfos().getId();
			j.del(key);
			j.srem(key, String.valueOf(getCurrentServerInfos().getId()));
		}
	}

	public TimeSaverCommon getCommon() {
		return common;
	}

	public static ServerInfo getCurrentServerInfos() {
		return currentServerInfos;
	}

	public static TSSpigot get() {
		return getPlugin(TSSpigot.class);
	}
}