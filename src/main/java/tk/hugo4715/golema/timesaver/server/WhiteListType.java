package tk.hugo4715.golema.timesaver.server;

public enum WhiteListType {

	NONE(0, "None"), 
	VIP(1, "VIP"), 
	FRIEND(2, "Friend"), 
	YOUTUBER(3, "YouTuber"), 
	HOST(4, "Host"),
	STAFF(5, "Staff"), 
	PRIVATE(6, "Private");

	public Integer id;
	public String name;

	private WhiteListType(final Integer id, final String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}
