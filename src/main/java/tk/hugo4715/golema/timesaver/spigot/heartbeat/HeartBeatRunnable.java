package tk.hugo4715.golema.timesaver.spigot.heartbeat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.spigot.TSSpigot;

/**
 * This is updated each seconds
 */
public class HeartBeatRunnable extends BukkitRunnable {

	public HeartBeatRunnable() {}

	public void run() {
		if (TSSpigot.getCurrentServerInfos() != null) {
			try (Jedis j = TSSpigot.get().getCommon().getJedisAccess().getJedisPool().getResource()) {
				TSSpigot.getCurrentServerInfos().setCurrentPlayers(Bukkit.getOnlinePlayers().size());
				TSSpigot.getCurrentServerInfos().setMaxPlayers(Bukkit.getMaxPlayers());

				String key = "TS:SERVERS:" + TSSpigot.getCurrentServerInfos().getId();
				j.set(key, TSSpigot.get().getCommon().getGson().toJson(TSSpigot.getCurrentServerInfos()));
				j.expire(key, 30);
			}
		}
		System.out.println("Serveur check !");
	}
}