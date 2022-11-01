package com.example.codenames.utils;

public class Const {
	public static final String URL_JSON_REGISTRATION = "/users/register/";
	// 	public static final String URL_JSON_REGISTRATION = "/users/register/";
	public static final String URL_JSON_LOGIN = "/users/login";
	// public static final String URL_JSON_LOGIN = "/users/login/";

	public static final String URL_JSON_PLAYERNUM_GET = "http://10.90.75.56:8080/games/3/numPlayers";
	public static final String URL_JSON_PLAYERNUM_POST = "/games/3/addPlayer?player_id=4";
	public static final String URL_JSON_GAMELOBBYNAME_GET = "http://10.90.75.56:8080/games/3";
	//public static final String URL_JSON_STATISTICS = "/users";

	public static final String URL_JSON_CARD_GET = "http://10.90.75.56:8080/games/3/words";
	public static final String URL_JSON_COLOR_GET = "http://10.90.75.56:8080/games/3/colors";
	public static final String URL_JSON_REVEAL_GET = "http://10.90.75.56:8080/games/3/isRevealed";
	public static final String URL_JSON_CARD_POST = "/games/{id}/words";

	public static final String URL_JSON_STATISTICS = "http://10.90.75.56:8080/users/";

	public static final String URL_JSON_LOBBY = "http://10.90.75.56:8080/games/lobbyinfo";

	public static final String URL_JSON_CREATE = "/games/add";



}