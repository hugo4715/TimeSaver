package tk.hugo4715.golema.timesaver.playpen;

import org.json.JSONException;

import tk.hugo4715.golema.timesaver.server.GameInfos;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerType;

@SuppressWarnings("static-access")
public class Balancer extends Thread {

	public Balancer() {
	}

	@Override
	public void run() {
		while (!(this.isInterrupted())) {
			int requestServer = 0;
			int maxRequest = 4;

			for (GameInfos gi : GameInfos.values()) {
				if ((!(gi.equals(GameInfos.NONE))) && (requestServer < maxRequest)) {
					int available = 0;
					for (ServerInfo info : TSPlayen.getInstance().getCommon().getAllServers()) {
						if (info == null)
							continue;

						if (info.isJoinable()
								&& ((info.getType().equals(ServerType.LOBBY))
										|| (info.getType().equals(ServerType.GAME)))
								&& info.getGameInfos().getName().equalsIgnoreCase(gi.getName())) {
							available++;
						}
					}

					if ((available < needAvailable(gi)) && (requestServer < maxRequest)) {
						try {
							TSPlayen.getInstance().startServer(gi.name);
							requestServer = requestServer + 1;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			try {
				this.sleep(20 * 1000);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int needAvailable(GameInfos gameInfos) {
		if (gameInfos.getName().equalsIgnoreCase(GameInfos.LOBBY.getName())
				|| (gameInfos.getName().equalsIgnoreCase(GameInfos.LOBBYSKYWARS.getName()))
				|| (gameInfos.getName().equalsIgnoreCase(GameInfos.LOBBYSKYRUSH.getName())))
			return 1;
		return 1;
	}
}