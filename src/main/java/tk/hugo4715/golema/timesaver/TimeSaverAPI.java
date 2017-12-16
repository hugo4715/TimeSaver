package tk.hugo4715.golema.timesaver;

import java.util.List;

import tk.hugo4715.golema.timesaver.server.GameInfos;
import tk.hugo4715.golema.timesaver.server.ServerInfo;
import tk.hugo4715.golema.timesaver.server.ServerStatus;
import tk.hugo4715.golema.timesaver.server.ServerType;
import tk.hugo4715.golema.timesaver.server.WhiteListType;
import tk.hugo4715.golema.timesaver.spigot.TSSpigot;

public class TimeSaverAPI {

	private TimeSaverAPI(){};
	
	/**
	 * Définir le Status du Serveur.
	 * 
	 * @param serverStatus
	 */
	public static void setServerStatus(ServerStatus serverStatus) {
		TSSpigot.getCurrentServerInfos().setStatus(serverStatus);
	}

	/**
	 * Définir le Jeu.
	 * 
	 * @param gameInfos
	 */
	public static void setServerGame(GameInfos gameInfos) {
		TSSpigot.getCurrentServerInfos().setGameInfos(gameInfos);
	}

	/**
	 * Définir le Type de Serveur.
	 */
	public static void setServerType(ServerType serverType) {
		TSSpigot.getCurrentServerInfos().setType(serverType);
	}

	/**
	 * Définir le nom de la Map.
	 * 
	 * @param mapName
	 */
	public static void setServerMap(String mapName) {
		TSSpigot.getCurrentServerInfos().setMap(mapName);
	}

	/**
	 * Définir si le Serveur est joignable.
	 * 
	 * @param value
	 */
	public static void setJoinable(boolean value) {
		TSSpigot.getCurrentServerInfos().setJoinable(value);
	} 

	/**
	 * Définir la Whitelist du Serveur.
	 * 
	 * @param whiteListType
	 */
	public static void setWhitelistType(WhiteListType whiteListType) {
		TSSpigot.getCurrentServerInfos().setWhitelistType(whiteListType);
	}

	/**
	 * Récupérer le ServerInfo.
	 * 
	 * @return
	 */
	public static ServerInfo geCurrentServeurInfo() {
		return TSSpigot.getCurrentServerInfos();
	}

	/**
	 * Récupérer la Liste des serveurs.
	 * 
	 * @return
	 */
	public static List<ServerInfo> getServerInfoList() {
		return TSSpigot.get().getCommon().getAllServers();
	}
}