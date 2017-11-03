package tk.hugo4715.golema.timesaver.server;

public enum ServerStatus
{
    NONE(-1, "None", -1), 
    REBOOT(0, "Reboot", -1), 
    ALLOW(1, "Allow", 0), 
    FULL(2, "Full", -1), 
    VIP(3, "VIP", 5), 
    STAFF(4, "Staff", 50), 
    INGAME(6, "InGame", -1), 
    INGAMEVIP(7, "InGameVIP", -1), 
    INHOST(8, "InHost", 0), 
    WHITELIST(9, "Whitelist", 50), 
    DEVELOPPEMENT(10, "Developpement", 50), 
    PRIVATE(11, "Private", 100), 
    EVENTS(11, "Event", 100), 
    EVENTSVIP(11, "EventVIP", -1);
    
    public Integer id;
    public String name;
    public Integer powerJoin;
    
    private ServerStatus(final Integer id, final String name, final Integer powerJoin) {
        this.id = id;
        this.name = name;
        this.powerJoin = powerJoin;
    }
    
    public Integer getID() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Integer getPowerJoin() {
        return this.powerJoin;
    }
    
    public static ServerStatus getByName(final String serverStatus) {
        for (final ServerStatus serverStatut : values()) {
            if (serverStatut.name.contains(serverStatus)) {
                return serverStatut;
            }
        }
        return null;
    }
    
}
