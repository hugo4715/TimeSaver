package tk.hugo4715.golema.timesaver.playpen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.playpen.core.coordinator.network.LocalCoordinator;
import tk.hugo4715.golema.timesaver.server.ServerInfo;

public class CoordinatorPort {
	public static int getAvailablePort(LocalCoordinator c) {
		Set<Integer> used = new HashSet<>();
		
		for(ServerInfo si : TSPlayen.getInstance().getCommon().getAllServers()) {
			if(si.getCoordinatorUUID().toString().equals(c.getUuid())) {
				used.add(si.getPort());
			}
		}
		
		int i = 1;
		while(used.contains(i)) {
			i++;
		}
		used.add(i);
		return i;
	}
}
