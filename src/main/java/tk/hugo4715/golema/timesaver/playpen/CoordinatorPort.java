package tk.hugo4715.golema.timesaver.playpen;

import io.playpen.core.coordinator.network.LocalCoordinator;

public class CoordinatorPort {
	
	public static int getAvailablePort(LocalCoordinator c) {
		/*List<Integer> used = new ArrayList<Integer>();
		for (ServerInfo si : TSPlayen.getInstance().getCommon().getAllServers()) {
			if ((si != null)) {
				used.add(si.getPort());
			}
		}
		int i = 26001;
		while (used.contains(i)) {
			i++; }
		used.add(i);*/
		return (int) (Math.random()*(54999 - 26001)) + 26001;
		//return i;
	}
}