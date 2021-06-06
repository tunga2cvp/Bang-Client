package entity.message;
import com.google.gson.Gson;
import controller.*;
import entity.Card;
import entity.Player;
import entity.PlayerPublicInitial;
import entity.Room;
import javafx.application.Platform;
import javafx.stage.Stage;
import utils.Client;
import utils.Configs;
import utils.JsonHandler;
import views.screen.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Listener {
    public static void readReceiveMessage(String message) throws InterruptedException, IOException {
        Gson gson = new Gson();
        String command = JsonHandler.getStringAttribute(message, "command");
        switch (command){
            case "signup":
                SignUpController signUpController = new SignUpController();
                signUpController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("result = " + JsonHandler.getStringAttribute(message, "result"));
                break;
            case "login":
                // get the login result
                LoginController loginController = new LoginController();
                loginController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("login result = " + JsonHandler.getStringAttribute(message, "result"));

                // get the open rooms
                LoginReceive loginReceive = gson.fromJson(message, LoginReceive.class);
                if (loginReceive.msg.equals("succeed")){
                    Set<String> set = loginReceive.getLobby().keySet();
                    OpenRoomsController.roomList = new ArrayList<Room>();
                    for (String key : set) {
                        System.out.println(key + " " + loginReceive.getLobby().get(key));
                        Room room = new Room(key, loginReceive.getLobby().get(key));
                        OpenRoomsController.roomList.add(room);
                    }
                }
                break;
            case "lobbynotify":
                LobbyNotifyReceive lobbyNotifyReceive = gson.fromJson(message, LobbyNotifyReceive.class);
                Set<String> lobbyset = lobbyNotifyReceive.getLobby().keySet();
                OpenRoomsController.roomList = new ArrayList<Room>();
                for (String key : lobbyset) {
                    System.out.println(key + " " + lobbyNotifyReceive.getLobby().get(key));
                    Room room = new Room(key, lobbyNotifyReceive.getLobby().get(key));
                    OpenRoomsController.roomList.add(room);
                }
                HomeScreenHandler.getHomescreenHandler().reload();
                break;

            case "logout":
                System.out.println("logout result = " + JsonHandler.getStringAttribute(message, "msg"));
                break;
                
            case "createroom":
                CreatRoomReceive creatRoomReceive = gson.fromJson(message, CreatRoomReceive.class);
                CreateRoomController.msg = JsonHandler.getStringAttribute(message, "msg");
//                System.out.println(CreateRoomController.msg);

                if (creatRoomReceive.result) {
                    System.out.println(JsonHandler.getStringAttribute(message, "roomid"));
                    LobbyController.roomName = JsonHandler.getStringAttribute(message, "roomid");
                    HomeScreenHandler.getHomescreenHandler().showroom();
                }
                else {
                    Platform.runLater(()-> {
                        PopUpHandler popUpHandler = null;
                        try {
                            popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        popUpHandler.CreateRoomFailed();
                    });
                }
                break;
            case "joinroom":
                JoinRoomReceive joinRoomReceive = gson.fromJson(message, JoinRoomReceive.class);
                if (joinRoomReceive.result){
                    // reset the player list
                    LobbyController.playerList = new ArrayList<Player>();

                    // adding player
                    for (int i = 0; i < joinRoomReceive.roomMembers.size(); i++){
                        Player player = new Player(joinRoomReceive.roomMembers.get(i).getUsername());
                        LobbyController.playerList.add(player);
                    }
                    // setting the room name
                    LobbyController.roomName = joinRoomReceive.roomMembers.get(0).getUsername();

                    // showing the room lobby
                    HomeScreenHandler.getHomescreenHandler().showroom();
                }
                else {
                    Platform.runLater(()-> {
                        PopUpHandler popUpHandler = null;
                        try {
                            popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        popUpHandler.JoinRoomFailed();
                    });
                }
                break;
            case "roomnotify":
                RoomNotifyReceive roomNotifyReceive = gson.fromJson(message, RoomNotifyReceive.class);
                //
                LobbyController.roomName = roomNotifyReceive.room.getName();
                // reset the player list
                LobbyController.playerList = new ArrayList<Player>();

                // adding player
                for (int i = 0; i < roomNotifyReceive.room.getMembers().size(); i++){
                    Player player = new Player(roomNotifyReceive.room.getMembers().get(i).getUsername());
                    LobbyController.playerList.add(player);
                }

                // reload the lobby
                Platform.runLater(()-> {
                    try {
                        LobbyScreenHandler.getLobbyScreenHandler().reload();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "startgame":
                if (JsonHandler.getStringAttribute(message,"result").equals("false")){
                    Platform.runLater(()-> {
                        PopUpHandler popUpHandler = null;
                        try {
                            popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        popUpHandler.StartGameFailed();
                    });
                }
                break;
            case "update_public_initial":
                GamePublicInit gamePublicInit = gson.fromJson(message, GamePublicInit.class);
                // reset player list
                BoardController.playersList = new ArrayList<Player>();

                // set new player list
                for (int i = 0; i <gamePublicInit.getPlayerList().size(); i++){
                    Player player = new Player(gamePublicInit.getPlayerList().get(i).getName(),gamePublicInit.getPlayerList().get(i).getId(), gamePublicInit.getPlayerList().get(i).getNumCards(), gamePublicInit.getPlayerList().get(i).getHealth(), gamePublicInit.getPlayerList().get(i).getEquipCard());
                    BoardController.playersList.add(player);
                }

                // set sheriff
                int sheriffId = gamePublicInit.getIdSheriff();
                BoardController.idSheriff = sheriffId;
                BoardController.playersList.get(sheriffId).setIsSheriff();

                break;
            case "update_private_initial":
                GamePrivateInit gamePrivateInit = gson.fromJson(message, GamePrivateInit.class);

                // set cardsHand
                BoardController.cardList = new ArrayList<Card>();
                for ( int i = 0; i < gamePrivateInit.getCardsHand().size(); i++){
                    Card card = new Card(gamePrivateInit.getCardsHand().get(i));
                    BoardController.cardList.add(card);
                }
                // set role
                BoardController.playerRole = gamePrivateInit.getRole();

                // set player num
                BoardController.playerNum = gamePrivateInit.getId_player();

                // set position
                BoardController.setOpponentPosition();
                // show board screen
                Platform.runLater(()-> {
                    try {
                        LobbyScreenHandler.getLobbyScreenHandler().showBoard();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                BoardController.isActionRequireTurn = false;
                break;
            case "notify_turn":
                int id = Integer.parseInt(JsonHandler.getStringAttribute(message, "id"));
                BoardController.setIsMyTurn(id == BoardController.playerNum);

                // set turn for board screen
                if (!BoardController.isIsMyTurn()){
                    String playerName = BoardController.playersList.get(id).getName();
                    Platform.runLater(()-> {
                        try {
                            BoardScreenHandler.getBoardScreenHandler().setTurn(playerName.concat("'s turn"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                else
                {
                    Platform.runLater(()-> {
                        try {
                            BoardScreenHandler.getBoardScreenHandler().setTurn("your turn");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                //reset the button
                Platform.runLater(()-> {
                    try {
                        BoardScreenHandler.getBoardScreenHandler().reloadButtons();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "update_public_ingame":
                UpdatePublicInGame updatePublicInGame = gson.fromJson(message, UpdatePublicInGame.class);
                ArrayList<PlayerPublicInitial> changedPlayers = (ArrayList<PlayerPublicInitial>) updatePublicInGame.getPlayerList().clone();

                // replace the changed player
                if ( changedPlayers != null){
                    for (int i =0; i < changedPlayers.size(); i++){
                        int idChanged  = changedPlayers.get(i).getId();
                        Player changedPlayer = new Player(changedPlayers.get(i).getName(), changedPlayers.get(i).getId(), changedPlayers.get(i).getNumCards(), changedPlayers.get(i).getHealth(), changedPlayers.get(i).getEquipCard());
                        BoardController.playersList.remove(idChanged);
                        BoardController.playersList.add(idChanged, changedPlayer);
//                        System.out.println(changedPlayer.getCardNum());
                    }
                }
                // reset the position
                BoardController.setOpponentPosition();

                //  reset sheriff incase sheriff changed
                BoardController.playersList.get(BoardController.idSheriff).setIsSheriff();

                // reset the player status
                Platform.runLater(()-> {
                    try {
                        BoardScreenHandler.getBoardScreenHandler().reloadPlayerStatus();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "update_private_ingame":
                UpdatePrivateInGame updatePrivateInGame = gson.fromJson(message, UpdatePrivateInGame.class);
                // reset card list
                BoardController.cardList = new ArrayList<Card>();
                for ( int i = 0; i < updatePrivateInGame.getCardsHand().size(); i++){
                    Card card = new Card(updatePrivateInGame.getCardsHand().get(i));
                    BoardController.cardList.add(card);
                }
                // reload the card list on screen
                Platform.runLater(()-> {
                    try {
                        BoardScreenHandler.getBoardScreenHandler().reloadCardList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                break;
            case "action_required":
                BoardController.isActionRequireTurn = true;
                ActionRequire actionRequire = gson.fromJson(message, ActionRequire.class);
                Platform.runLater(()-> {
                    PopUpHandler popUpHandler = null;
                    try {
                        popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    popUpHandler.ActionRequired(actionRequire.getSourcePlayer(), actionRequire.getCardRequired());
                });
                // reload the card list on screen
                Platform.runLater(()-> {
                    try {
                        BoardScreenHandler.getBoardScreenHandler().reloadCardList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "action_turn":
                break;
            case "action_discard":
                ActionDiscard actionDiscard = gson.fromJson(message, ActionDiscard.class);
                Platform.runLater(()-> {
                    PopUpHandler popUpHandler = null;
                    try {
                        popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    popUpHandler.ActionDiscard(actionDiscard.numDiscard);
                });
                break;
            case "player_death":
                PlayerDeath playerDeath = gson.fromJson(message, PlayerDeath.class);
                BoardController.playersList.get(playerDeath.id).setRole(playerDeath.role);
                // reset the player status
                Platform.runLater(()-> {
                    try {
                        BoardScreenHandler.getBoardScreenHandler().reloadPlayerStatus();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "end_game":
                EndGameMessage endGameMessage = gson.fromJson(message, EndGameMessage.class);
                Platform.runLater(()-> {
                    EndGameHandler endGameHandler = null;
                    try {
                        endGameHandler = new EndGameHandler(Configs.END_GAME_PATH, new Stage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        endGameHandler.EndGame(endGameMessage.victoryRole, BoardScreenHandler.getBoardScreenHandler().stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            default:
                System.out.println("unknown command:" + command);
        }
    }
}
