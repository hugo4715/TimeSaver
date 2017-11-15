package tk.hugo4715.golema.timesaver.server;

import java.util.UUID;

/**
 * This object is stored in redis so it can be retrieved on any bungeecord or
 * spigot or anything
 */
public class ServerInfo {

	private ServerType type;
	private int id;
	private int maxPlayers;
	private int currentPlayers;
	private String ip;
	private int port;
	private UUID uuid;
	private UUID coordinatorUUID;
	private ServerStatus status = ServerStatus.NONE;
	private GameInfos gameInfos;
	private String map;
	private boolean isJoinable;
	private WhiteListType whitelistType;
	// hosts
	private String hostName;

	public ServerInfo(ServerType type, int id, int maxPlayers, int currentPlayers, String ip, int port, UUID uuid,
			UUID coordinatorUUID, ServerStatus status, GameInfos gameInfos, String map, boolean isJoinable,
			WhiteListType whitelistType, String hostName) {
		this.type = type;
		this.id = id;
		this.maxPlayers = maxPlayers;
		this.currentPlayers = currentPlayers;
		this.ip = ip;
		this.port = port;
		this.uuid = uuid;
		this.coordinatorUUID = coordinatorUUID;
		this.status = status;
		this.gameInfos = gameInfos;
		this.map = map;
		this.isJoinable = isJoinable;
		this.whitelistType = whitelistType;
		this.hostName = hostName;
	}

	public ServerType getType() {
		return type;
	}

	public void setType(ServerType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getCurrentPlayers() {
		return currentPlayers;
	}

	public void setCurrentPlayers(int currentPlayers) {
		this.currentPlayers = currentPlayers;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getCoordinatorUUID() {
		return coordinatorUUID;
	}

	public void setCoordinatorUUID(UUID coordinatorUUID) {
		this.coordinatorUUID = coordinatorUUID;
	}

	public ServerStatus getStatus() {
		return status;
	}

	public void setStatus(ServerStatus status) {
		this.status = status;
	}

	public GameInfos getGameInfos() {
		return gameInfos;
	}

	public void setGameInfos(GameInfos gameInfos) {
		this.gameInfos = gameInfos;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public boolean isJoinable() {
		return isJoinable;
	}

	public void setJoinable(boolean isJoinable) {
		this.isJoinable = isJoinable;
	}

	public WhiteListType getWhitelistType() {
		return whitelistType;
	}

	public void setWhitelistType(WhiteListType whitelistType) {
		this.whitelistType = whitelistType;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getFullName() {
		return getGameInfos().getName() + "-" + getId();
	}
}
