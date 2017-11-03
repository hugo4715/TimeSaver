package tk.hugo4715.golema.timesaver.bungee.runnable;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.Validate;

import net.md_5.bungee.api.ProxyServer;
import redis.clients.jedis.Jedis;
import tk.hugo4715.golema.timesaver.bungee.TSBungee;
import tk.hugo4715.golema.timesaver.server.ServerInfo;

public class UpdateThread implements Runnable {

	private AtomicBoolean running = new AtomicBoolean(true);
	
	private Set<String> added = new HashSet<>();
	
	@Override
	public void run() {
		while(this.running.get()) {
			try (Jedis j = TSBungee.getInstance().getCommon().getJedisAccess().getJedisPool().getResource()) {
				
				Set<String> ids = j.smembers("TS:IDPOOL");
				if(ids == null)return;
				
				for(String str : added) {
					ProxyServer.getInstance().getServers().remove(str);
				}
				
				for(String str : ids) {
					ServerInfo info = TSBungee.getInstance().getCommon().getServer(Integer.valueOf(str));//TODO return null when servers stopped
					net.md_5.bungee.api.config.ServerInfo i = ProxyServer.getInstance().constructServerInfo(info.getFullName() , new InetSocketAddress(info.getIp(), info.getPort()), "motd", false);
					ProxyServer.getInstance().getServers().put(info.getFullName(),i);
					added.add(info.getFullName());
				}
			}
			
			
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {}
		}
	}
	
	
	public void stop() {
		running.set(false);
		Thread.currentThread().interrupt();
	}

}
