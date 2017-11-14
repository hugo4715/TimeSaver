package tk.hugo4715.golema.timesaver.playpen;

import java.util.List;

import org.json.JSONException;

import tk.hugo4715.golema.timesaver.server.GameInfos;
import tk.hugo4715.golema.timesaver.server.ServerInfo;

public class Balancer extends Thread{
	@Override
	public void run() {
		while(!this.isInterrupted()) {
			
			List<ServerInfo> infos = TSPlayen.getInstance().getCommon().getAllServers();
			
			for(GameInfos gi : GameInfos.values()) {
				
				if(!gi.equals(GameInfos.NONE)) {
					int available = 0;
					
					for(ServerInfo info : infos) {
						if(info != null && info.getGame() != null && info.getGame().equals(gi.name) && info.isJoinable() && info.getCurrentPlayers() < info.getMaxPlayers()){
							available++;
						}
					}
					
					if(available == 0) {
						try {
							TSPlayen.getInstance().startServer(gi.name);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			try {
				sleep(TSPlayen.getInstance().getConfig().getInt("scan-rate")*1000);
			} catch (JSONException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
