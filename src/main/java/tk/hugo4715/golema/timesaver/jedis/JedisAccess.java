package tk.hugo4715.golema.timesaver.jedis;

import java.io.Closeable;
import java.io.IOException;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Used to access redis
 */
public class JedisAccess implements Closeable {

	private JedisPool jedisPool;

	public JedisAccess(JedisCredentials credentials) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		if (credentials.isUsePass())
			jedisPool = new JedisPool(poolConfig, credentials.getHost(), credentials.getPort(), 3000,
					credentials.getPassword());
		else
			jedisPool = new JedisPool(poolConfig, credentials.getHost(), credentials.getPort(), 3000);
	}

	public void close() throws IOException {
		jedisPool.close();
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}
}
