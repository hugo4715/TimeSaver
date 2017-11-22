package tk.hugo4715.golema.timesaver.jedis;

import java.util.Set;
import java.util.stream.Collectors;

import redis.clients.jedis.Jedis;

public class JedisIdPool {

	private JedisAccess ja;

	public JedisIdPool(JedisAccess ja) {
		this.ja = ja;
	}

	public void returnId(int id) {
		try (Jedis j = ja.getJedisPool().getResource()) {
			j.srem("TS:IDPOOL", id + "");
		}
	}

	public int nextId() {
		try (Jedis j = ja.getJedisPool().getResource()) {
			Set<String> used = j.smembers("TS:IDPOOL");
			if (used == null) {
				return storeId(1);
			}

			Set<Integer> ids = used.stream().map(i -> Integer.valueOf(i)).collect(Collectors.toSet());

			int id = 1;
			do {
				id++;
			} while (ids.contains(id));
			return storeId(id);
		}
	}

	private int storeId(int id) {
		try (Jedis j = ja.getJedisPool().getResource()) {
			j.sadd("TS:IDPOOL", id + "");
		}
		return id;
	}
}
