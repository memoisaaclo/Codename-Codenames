package com.example.codenames.utils;

public class Const {

	// Registration Endpoint
	public static final String URL_JSON_REGISTRATION = "/users/register/";

	// Login Endpoint
	public static final String URL_JSON_LOGIN = "/users/login";

	// Add the game id in between parts of URL
	public static final String URL_JSON_PLAYERNUM_GET_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JOSN_PLAYERNUM_GET_SECONd = "/numPlayers";

	// Add the game id in between parts of URL and then playerID after second
	public static final String URL_JSON_PLAYERNUM_POST_FIRST = "/games/";
	public static final String URL_JSON_PLAYERNUM_POST_SECOND = "/addplayer/";

	// Gets lobby name
	public static final String URL_JSON_GAMELOBBYNAME_GET = "http://10.90.75.56:8080/games/";

	// Gets user statistics
	public static final String URL_JSON_STATISTICS = "http://10.90.75.56:8080/users/";

	// Gets Lobby Info
	public static final String URL_JSON_LOBBY = "http://10.90.75.56:8080/games/lobbyinfo";

	// Creates Game
	public static final String URL_JSON_CREATE = "/games/add";

	// Adds Card/Word
	public static final String URL_JSON_WORD_ADD = "/admin/cards/add";

	// Deletes Card/Word
	public static final String URL_JSON_WORD_DELETE = "/admin/cards/remove";

	// Gets Clue
	public static final String URL_JSON_CLUE_GET = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_CLUE_GET_SECOND = "/clue";

	// Sends Clue
	public static final String URL_JSON_CLUE_PUT = "/games/";
	public static final String URL_JSON_CLUE_PUT_SECOND = "/clue/";
	public static final String URL_JSON_CLUE_PUT_THIRD = "/";


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

	//Get Cards for Game
	public static final String URL_JSON_GETALLCARDS_SPECTATOR_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_GETALLCARDS_SPECTATOR_SECOND = "/getboard";

	//Check if User is Admin
	public static final String URL_JSON_ISADMIN = "http://10.90.75.56:8080/admin/get/";

	//Gen cards for game
	public static final String URL_JSON_GENCARDS_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_GENCARDS_SECOND = "/generateboard";


	//Jimmy copied and pasted this

	//Get Cards
	public static final String URL_JSON_CARD_GET = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_CARD_GET_SECOND = "/words";

	//Get Colors
	public static final String URL_JSON_COLOR_GET = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_COLOR_GET_SECOND = "/colors";

	public static final String URL_JSON_ALL_CARDS = "http://10.90.75.56:8080/admin/cards/all";
	public static final String URL_JSON_ALL_USERS = "http://10.90.75.56:8080/users/getallusers";

	public static final String URL_JSON_REVEAL_GET = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_REVEAL_GET_SECOND = "/isrevealed";

	public static final String URL_JSON_CARD_POST = "/games/38/words";

	//Get the number of cards the clue is applied to
	public static final String URL_JSON_NUMGUESSES_FIRST = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_NUMGUESSES_SECOND = "/guessesavailable";

	public static final String URL_JSON_SCORE_GET = "http://10.90.75.56:8080/games/";
	public static final String URL_JSON_SCORE_GET_SECOND = "/getboard";

	public static final String URL_JSON_GUESS_FIRST = "/games/";
	public static final String URL_JSON_GUESS_SECOND = "/guess/";
}