package tk.hugo4715.golema.timesaver.server;
public enum GameInfos
{
    NONE("None"), 
    LOBBY("Lobby"), 
    LOBBY_SKYWARS("LobbySkyWars"), 
    LOBBY_SKYRUSH("LobbySkyRush"), 
    SKYRUSH("SkyRush"), 
    SKYWARS("SkyWars"), 
    SKYWARS_SOLO_NORMAL("SkyWarsSoloNormal"), 
    SKYWARS_SOLO_CHEAT("SkyWarsSoloCheat"), 
    SKYWARS_TEAM_NORMAL("SkyWarsTeamNormal"), 
    SKYWARS_TEAM_CHEAT("SkyWarsTeamCheat"), 
    GETOUT("GetOut");
    
    public String name;
    
    private GameInfos(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}