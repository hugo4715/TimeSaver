package tk.hugo4715.golema.timesaver.spigot.heartbeat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.TimeSaverAPI;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerType;
import tk.hugo4715.golema.timesaver.spigot.TSSpigot;

/**
 * This is updated each seconds
 */
public class HeartBeatRunnable extends BukkitRunnable {

	private int inactifTime = 0;

	public HeartBeatRunnable() {}

	public void run() {

		// Détection d'un crash.
		if (TSSpigot.getCurrentServerInfos() != null) {

			// Update du serveur sur Redis.
			try (Jedis j = TSSpigot.get().getCommon().getJedisAccess().getJedisPool().getResource()) {
				TSSpigot.getCurrentServerInfos().setCurrentPlayers(Bukkit.getOnlinePlayers().size());
				TSSpigot.getCurrentServerInfos().setMaxPlayers(Bukkit.getMaxPlayers());

				String key = "TS:SERVERS:" + TSSpigot.getCurrentServerInfos().getId();
				j.set(key, TSSpigot.get().getCommon().getGson().toJson(TSSpigot.getCurrentServerInfos()));
				j.expire(key, 30);
			}

			// Détection de serveur innactif.
			if (!Bukkit.getOnlinePlayers().isEmpty()) {
				inactifTime = 0;
			}

			// Serveur innactif trop longtemps. (timeout)
			if (inactifTime >= 30) {
				int serverTypeCount = 0;
				for (ServerInfo sInfo : TimeSaverAPI.getServerInfoList()) {
					if ((sInfo != null) && (sInfo.isJoinable())
							&& (sInfo.getGameInfos().getName()
									.equalsIgnoreCase(TSSpigot.getCurrentServerInfos().getGameInfos().getName()))
							&& ((sInfo.getType().getName().equalsIgnoreCase(ServerType.LOBBY.getName()))
									|| (sInfo.getType().getName().equalsIgnoreCase(ServerType.GAME.getName())))) {
						serverTypeCount++;
					}
				}
				if (serverTypeCount > 1) {
					Bukkit.getServer().shutdown();
				} else {
					inactifTime = 0;
				} 
			}
			inactifTime++;
		}
	}
}