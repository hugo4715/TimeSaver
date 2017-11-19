package tk.hugo4715.golema.timesaver.playpen;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import io.playpen.core.coordinator.CoordinatorMode;
import io.playpen.core.coordinator.PlayPen;
import io.playpen.core.coordinator.network.LocalCoordinator;
import io.playpen.core.coordinator.network.Network;
import io.playpen.core.p3.P3Package;
import io.playpen.core.plugin.AbstractPlugin;
import tk.hugo4715.golema.timesaver.TimeSaverCommon;
import tk.hugo4715.golema.timesaver.jedis.JedisCredentials;
import tk.hugo4715.golema.timesaver.util.SingleLineFormatter;

public class TSPlayen extends AbstractPlugin {
	private static TSPlayen instance;

	private TimeSaverCommon common;

	private Balancer balancer;

	@Override
	public boolean onStart() {
		instance = this;
		if (PlayPen.get().getCoordinatorMode() != CoordinatorMode.NETWORK) {
			System.err.println("Only network coordinators are supported");
			return false;
		}
		Logger log = Logger.getGlobal();
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SingleLineFormatter());
		log.addHandler(handler);

		log.info("Starting timesaver plugin");

		common = new TimeSaverCommon(log,
				new JedisCredentials(getConfig().getString("redis.host"), getConfig().getInt("redis.port"),
						getConfig().getString("redis.password"), getConfig().getBoolean("redis.use-pass")));

		balancer = new Balancer();
		balancer.start();

		return true;
	}

	@Override
	public void onStop() {
		balancer.interrupt();
	}

	public void startServer(String game) {
		try {
			common.getLogger().info("Checking if we can start a server of type " + game);
			P3Package p = PlayPen.get().getPackageManager().resolve(game, "promoted");
			if (p == null)
				return;
			LocalCoordinator choosen = getBestCoordinator();

			if (choosen == null) {
				common.getLogger().severe("No available coordinator found! Can't start any server");
				return;
			}
			UUID id = UUID.randomUUID();
			if (choosen.getChannel() == null)
				throw new RuntimeException("");
			if (choosen.getChannel().remoteAddress() == null)
				throw new RuntimeException("");
			InetAddress ip = ((InetSocketAddress) choosen.getChannel().remoteAddress()).getAddress();
			Map<String, String> prop = new HashMap<>();
			prop.put("port", "" + CoordinatorPort.getAvailablePort(choosen));
			prop.put("ip", ip.getHostAddress());
			prop.put("serveruuid", id.toString());
			prop.put("coordinatoruuid", choosen.getUuid());

			Network.get().provision(p, game + "#" + id.toString(), prop, choosen.getName());

			common.getLogger().info("Requested server " + game + " from coordinator " + choosen.getName());
		} catch (Exception e) {
			System.err.println("Error while trying to start a server! Tracktrace below");
			e.printStackTrace();
		}

	}

	public LocalCoordinator getBestCoordinator() {
		Random r = new Random();
		List<LocalCoordinator> l = Network.get().getCoordinators().values().stream().collect(Collectors.toList());
		return Network.get().getCoordinator(l.get(r.nextInt(l.size())).getKeyName());
	}

	public TimeSaverCommon getCommon() {
		return common;
	}

	public static TSPlayen getInstance() {
		return instance;
	}
}
