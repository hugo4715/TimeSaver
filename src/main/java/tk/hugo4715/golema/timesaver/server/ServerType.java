package tk.hugo4715.golema.timesaver.server;

public enum ServerType {
	
    NONE(-1, "inconnue"), 
    LIMBO(0, "Limbo"), 
    CONNECTOR(1, "Connector"), 
    DEVELOPPEMENT(2, "Developpement"), 
    BUILD(3, "Build"), 
    PROXY(4, "Proxy"), 
    LOBBY(5, "Lobby"), 
    GAME(6, "Game"), 
    HOST(7, "Host");
    
	public Integer id;
	public String name;

	private ServerType(final Integer id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public static ServerType getByName(final String serverTypes) {
		for (final ServerType serverType : values()) {
			if (serverType.name.contains(serverTypes)) {
				return serverType;
			}
		}
		return null;
	}
}