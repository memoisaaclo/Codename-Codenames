package com.example.codenames.utils;

public class Const {
	public static final String URL_JSON_REGISTRATION = "http://10.90.75.56:8080/users/register/";
	public static final String URL_JSON_LOGIN = "http://10.90.75.56:8080/users/login";

	public static final String URL_JSON_PLAYERNUM_GET = "http://10.90.75.56:8080/games/38/numPlayers";
	public static final String URL_JSON_PLAYERNUM_POST = "/games/38/addPlayer?player_id=4";
	public static final String URL_JSON_GAMELOBBYNAME_GET = "http://10.90.75.56:8080/games/38";

	public static final String URL_JSON_CARD_GET = "http://10.90.75.56:8080/games/38/words";
	public static final String URL_JSON_COLOR_GET = "http://10.90.75.56:8080/games/38/colors";

	public static final String URL_JSON_ALL_CARDS = "http://10.90.75.56:8080/cards/all";
	public static final String URL_JSON_ALL_USERS = "http://10.90.75.56:8080/users/getallusers";

	public static final String URL_JSON_COLOR_REVEAL = "http://10.90.75.56:8080/games/38/getboard";

	public static final String URL_JSON_REVEAL_GET = "http://10.90.75.56:8080/games/38/isrevealed";
	public static final String URL_JSON_CARD_POST = "/games/38/words";

	public static final String URL_JSON_STATISTICS = "http://10.90.75.56:8080/users/";

	public static final String URL_JSON_LOBBY = "http://10.90.75.56:8080/games/lobbyinfo";

	public static final String URL_JSON_CREATE = "http://10.90.75.56:8080/games/add";

	public static final String URL_JSON_WORD_ADD = "/admin/cards/add";
	public static final String URL_JSON_WORD_DELETE = "/admin/cards/remove";

	public static final String URL_JSON_CLUE_GET = "http://10.90.75.56:8080/games/38/clue";
	public static final String URL_JSON_CLUE_PUT = "/games/38/clue/testing/123";



}