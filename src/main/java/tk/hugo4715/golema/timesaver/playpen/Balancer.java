package tk.hugo4715.golema.timesaver.playpen;

import org.json.JSONException;

import tk.hugo4715.golema.timesaver.server.GameInfos;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerType;

public class Balancer extends Thread {

	public Balancer() {}

	@Override
	public void run() {
		while (!(this.isInterrupted())) {
			int requestServer = 0;
			int maxRequest = 4;

			for (GameInfos gi : GameInfos.values()) {
				int available = 0;
				for (ServerInfo info : TSPlayen.getInstance().getCommon().getAllServers()) {
					if (info == null)
						continue;

					// Compteur de serveur disponible.
					if (info.isJoinable()
							&& ((info.getType().getName().equalsIgnoreCase(ServerType.LOBBY.getName()))
									|| (info.getType().getName().equalsIgnoreCase(ServerType.GAME.getName()))
									|| (info.getType().getName().equalsIgnoreCase(ServerType.DEVELOPPEMENT.getName())))
							&& info.getGameInfos().getName().equalsIgnoreCase(gi.getName())) {
						available++;
					}
				}

				// Démarrage de Serveur.
				if ((available != gi.getNeedServer()) && (requestServer < maxRequest)) {
					try {
						TSPlayen.getInstance().startServer(gi.name);
						requestServer++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(15 * 1000);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}