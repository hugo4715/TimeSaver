package tk.hugo4715.golema.timesaver.request;

import java.util.UUID;


/**
 * This class is meant to be serialized and sent to playpen
 */
public class ServerRequest {
	private String type;
	private String map;
	
	private UUID uuid;

	public ServerRequest(String type, String map, UUID uuid) {
		this.type = type;
		this.map = map;
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
