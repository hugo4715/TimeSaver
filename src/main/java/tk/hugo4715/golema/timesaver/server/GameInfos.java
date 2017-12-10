package tk.hugo4715.golema.timesaver.server;

public enum GameInfos {
	
    NONE("inconnue"), 
    HUB("Hub"), 
    SKYRUSH("SkyRush"),
    DEFENDER("Defender"), 
    SKYWARSSOLONORMAL("SkyWarsSoloNormal"), 
    SKYWARSSOLOCHEAT("SkyWarsSoloCheat"), 
    SKYWARSTEAMNORMAL("SkyWarsTeamNormal"), 
    SKYWARSTEAMCHEAT("SkyWarsTeamCheat"),
    BEDWARSDUEL("BedWarsDuel"),
    BEDWARSSOLO("BedWarsSolo"),
    BEDWARSTEAM2X2("BedWarsTeam2X2"),
    BEDWARSTEAM3X3("BedWarsTeam3X3"),
    GETOUT("GetOut");
    
	public String name;

	private GameInfos(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}