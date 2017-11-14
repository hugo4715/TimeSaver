package tk.hugo4715.golema.timesaver.playpen;

import java.util.HashSet;
import java.util.Set;

import io.playpen.core.coordinator.network.LocalCoordinator;
import tk.hugo4715.golema.timesaver.server.ServerInfo;

public class CoordinatorPort {
	
	private static int min = 26001;
	
	public static int getAvailablePort(LocalCoordinator c) {
		Set<Integer> used = new HashSet<>();
		
		for(ServerInfo si : TSPlayen.getInstance().getCommon().getAllServers()) {
			if(si != null && si.getCoordinatorUUID() != null && si.getCoordinatorUUID().toString().equals(c.getUuid())) {
				used.add(si.getPort());
			}
		}
		
		int i = min;
		while(used.contains(i)) {
			i++;
		}
		used.add(i);
		return i;
	}
}
