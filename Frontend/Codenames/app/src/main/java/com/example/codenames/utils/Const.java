package com.example.codenames.utils;

public class Const {
	public static final String URL_JSON_REGISTRATION = "/login/register";
	public static final String URL_JSON_LOGIN = "/login";

	public static final String URL_JSON_PLAYERNUM_GET = "http://10.90.75.56:8080/games/3/numPlayers";
	public static final String URL_JSON_PLAYERNUM_POST = "/games/3/addPlayer?player_id=4";
	public static final String URL_JSON_GAMELOBBYNAME_GET = "http://10.90.75.56:8080/games/3";
	public static final String URL_JSON_STATISTICS = "/users";

	public static final String URL_JSON_CARD_GET = "http://10.90.75.56:8080/games/3/words";
	public static final String URL_JSON_CARD_POST = "/games/{id}/words";
}