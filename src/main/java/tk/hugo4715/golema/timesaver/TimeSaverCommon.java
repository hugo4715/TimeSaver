package tk.hugo4715.golema.timesaver;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import tk.hugo4715.golema.timesaver.jedis.JedisAccess;
import tk.hugo4715.golema.timesaver.jedis.JedisCredentials;
import tk.hugo4715.golema.timesaver.server.ServerInfo;

public class TimeSaverCommon implements Closeable {

	private Logger logger;
	private JedisAccess jedisAccess;
	private Gson gson = new GsonBuilder().create();

	public TimeSaverCommon(Logger log, JedisCredentials c) {
		this.logger = log;
		this.jedisAccess = new JedisAccess(c);
	}

	public void close() throws IOException {
		this.jedisAccess.close();
	}

	public Gson getGson() {
		return gson;
	}

	public JedisAccess getJedisAccess() {
		return jedisAccess;
	}

	public Logger getLogger() {
		return logger;
	}

	public ServerInfo getServer(int id) {
		try (Jedis j = getJedisAccess().getJedisPool().getResource()) {
			String value = j.get("TS:SERVERS:" + id);
			return getGson().fromJson(value, ServerInfo.class);
		}
	}

	public List<ServerInfo> getAllServers() {
		List<ServerInfo> s = new ArrayList<>();
		try (Jedis j = getJedisAccess().getJedisPool().getResource()) {
			List<Integer> ids = j.smembers("TS:IDPOOL").stream().map(str -> Integer.valueOf(str))
					.collect(Collectors.toList());
			int[] i = new int[ids.size()];
			for (int k = 0; k < i.length; k++) {
				i[k] = ids.get(k);
			}
			s.addAll(getServer(i));
		}
		return s;
	}

	public List<ServerInfo> getServer(int... ids) {
		if (ids.length == 0)
			return new ArrayList<>();

		List<ServerInfo> i = new ArrayList<>();

		try (Jedis j = getJedisAccess().getJedisPool().getResource()) {
			Pipeline p = j.pipelined();

			List<Response<String>> r = new ArrayList<>();
			for (int id : ids) {
				r.add(p.get("TS:SERVERS:" + id));
			}
			p.sync();

			for (Response<String> s : r) {
				i.add(getGson().fromJson(s.get(), ServerInfo.class));
			}
		}

		return i;
	}
}