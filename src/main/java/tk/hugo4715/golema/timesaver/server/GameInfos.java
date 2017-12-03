package tk.hugo4715.golema.timesaver.server;

public enum GameInfos {
	
    NONE("inconnue"), 
    LOBBY("Lobby"), 
    LOBBYSKYWARS("LobbySkyWars"), 
    LOBBYSKYRUSH("LobbySkyRush"), 
    SKYRUSH("SkyRush"),
    SKYTOWER("SkyTower"), 
    SKYWARSSOLONORMAL("SkyWarsSoloNormal"), 
    SKYWARSSOLOCHEAT("SkyWarsSoloCheat"), 
    SKYWARSTEAMNORMAL("SkyWarsTeamNormal"), 
    SKYWARSTEAMCHEAT("SkyWarsTeamCheat"), 
    GETOUT("GetOut");
    
	public String name;

	private GameInfos(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}