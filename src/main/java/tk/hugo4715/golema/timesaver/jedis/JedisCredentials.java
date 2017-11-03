package tk.hugo4715.golema.timesaver.jedis;

public class JedisCredentials {
	private String host;
	private int port;
	private String password;
	private boolean usePass;
	
	public JedisCredentials(String host, int port, String password, boolean usePass) {
		this.host = host;
		this.port = port;
		this.password = password;
		this.usePass = usePass;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isUsePass() {
		return usePass;
	}
	public void setUsePass(boolean usePass) {
		this.usePass = usePass;
	}
}