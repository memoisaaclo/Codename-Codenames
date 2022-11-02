package com.example.codenames.utils;

public class Const {

	// Registration Endpoint
	public static final String URL_JSON_REGISTRATION = "/users/register/";
	// 	public static final String URL_JSON_REGISTRATION = "/users/register/";

	// Login Endpoint
	public static final String URL_JSON_LOGIN = "/users/login";
	// public static final String URL_JSON_LOGIN = "/users/login/";

	// Get number players in lobby Endpoint
	public static final String URL_JSON_PLAYERNUM_GET = "http://10.90.75.56:8080/games/3/numPlayers";

	// Add the game id in between parts of URL
	public static final String URL_JSON_PLAYERNUM_GET_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JOSN_PLAYERNUM_GET_SECONd = "/numPlayers";


	// ^ "http://10.90.75.56:8080/games/3/numPlayers";
	// ^ "https://4d9722ab-1bce-484c-8966-0096b24a4e15.mock.pstmn.io/post_test/";


	public static final String URL_JSON_PLAYERNUM_POST = "http://10.90.75.56:8080/games/3/addPlayer/4";

	// Add the game id in between parts of URL and then playerID after second
	public static final String URL_JSON_PLAYERNUM_POST_FIRST = "/games/";
	public static final String URL_JSON_PLAYERNUM_POST_SECOND = "/addplayer/";

	// ^ "http://10.90.75.56:8080/games/3/addPlayer?player_id=3";
	// ^ "https://4d9722ab-1bce-484c-8966-0096b24a4e15.mock.pstmn.io/post_test/";

	public static final String URL_JSON_GAMELOBBYNAME_GET = "http://10.90.75.56:8080/games/";

	public static final String URL_JSON_STATISTICS = "http://10.90.75.56:8080/users/";

	public static final String URL_JSON_LOBBY = "http://10.90.75.56:8080/games/lobbyinfo";

	public static final String URL_JSON_CREATE = "/games/add";

	// Get players in lobby to add to player list
	public static final String URL_JSON_GETPLAYERS_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_GETPLAYERS_SECOND = "/players";

	//Set players team
	public static final String URL_JSON_SETTEAM_FIRST = "/players/";
	public static final String URL_JSON_SETTEAM_SECOND = "/setteam/";

	//Set players role
	public static final String URL_JSON_SETROLE_FIRST = "/players/";
	public static final String URL_JSON_SETROLE_SECOND = "/setrole/";

	//Remove player from lobby
	public static final String URL_JSON_REMOVEPLAYER_FIRST = "/games/";
	public static final String URL_JSON_REMOVEPLAYER_SECOND = "/removeplayer/";

}