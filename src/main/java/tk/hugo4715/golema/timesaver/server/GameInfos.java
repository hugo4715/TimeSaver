package tk.hugo4715.golema.timesaver.server;

public enum GameInfos {
	
    NONE("inconnue", 0), 
    HUB("Hub", 1), 
    SKYRUSH("SkyRush", 2),
    DEFENDER("Defender", 1), 
    SKYWARSSOLONORMAL("SkyWarsSoloNormal", 1), 
    SKYWARSSOLOCHEAT("SkyWarsSoloCheat", 2), 
    SKYWARSTEAMNORMAL("SkyWarsTeamNormal", 1), 
    SKYWARSTEAMCHEAT("SkyWarsTeamCheat", 1),
    BEDWARSDUEL("BedWarsDuel", 3),
    BEDWARSSOLO("BedWarsSolo", 1),
    BEDWARSTEAM2X2("BedWarsTeam2X2", 1),
    BEDWARSTEAM3X3("BedWarsTeam3X3", 1),
    GETOUT("GetOut", 1);
    
	public String name;
	public int needServer;

	private GameInfos(final String name, int needServer) {
		this.name = name;
		this.needServer = needServer;
	}

	public String getName() {
		return this.name;
	}
	
	public int getNeedServer() {
		return needServer;
	}
}