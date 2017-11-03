package tk.hugo4715.golema.timesaver.spigot.heartbeat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.spigot.TSSpigot;

/**
 * This is updated each seconds
 */
public class HeartBeatRunnable extends BukkitRunnable {

	public void run() {
		try (Jedis j = TSSpigot.get().getCommon().getJedisAccess().getJedisPool().getResource()) {
			TSSpigot.get().getCurrentServerInfos().setCurrentPlayers(Bukkit.getOnlinePlayers().size());
			TSSpigot.get().getCurrentServerInfos().setMaxPlayers(Bukkit.getMaxPlayers());
			
			String key = "TS:SERVERS:" + TSSpigot.get().getCurrentServerInfos().getId();
			j.set(key, TSSpigot.get().getCommon().getGson().toJson(TSSpigot.get().getCurrentServerInfos()));
			j.expire(key, 60);
		}
	}

}
