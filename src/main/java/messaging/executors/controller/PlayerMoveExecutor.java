package messaging.executors.controller;

import java.util.Arrays;
import java.util.List;

import messaging.dataobjects.Action;
import messaging.dataobjects.DataObject;
import messaging.dataobjects.Location;
import messaging.dataobjects.LocationType;
import messaging.dataobjects.MoveUpdate;
import messaging.dataobjects.PlayerData;
import messaging.dataobjects.RoundUpdate;
import messaging.executors.Executable;
import messaging.executors.Executor;
import messaging.messages.Message;
import messaging.messages.NotOkJoinGame;
import messaging.messages.NotOkPlayerMove;
import messaging.messages.OkPlayerMove;
import messaging.messages.PlayerMove;
import model.Model;
import model.Player;
import model.Tile;

public class PlayerMoveExecutor implements Executor {
    private PlayerMove message;

    public static final String reasonNotAPlayer = "The player provided does not exist.";
    public static final String reasonNotAnAction = "The action provided is not valid.";
    public static final String reasonNotTheirTurn = "Not their turn.";
    public static final String reasonActionNotAllowed = "The action provided is not allowed.";

    private Location from;
    private Location to;
    private Model model;

    private boolean isInGame(PlayerData playerData) {
        for (Player player : model.getPlayers()) {
            if (player.getIdentifier() == playerData.getIdentifier() && player.getName() == playerData.getName()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidAction(Action action) {
        if (action.getColor() == null || action.getFrom() == null || action.getTo() == null) {
            return false;
        }
        from = action.getFrom();
        to = action.getTo();

        List<LocationType> validFromLocations = Arrays.asList(LocationType.FACTORY, LocationType.MIDDLE);
        List<LocationType> validToLocations = Arrays.asList(LocationType.PATTERN_LINE,
                LocationType.FLOOR_LINE);
        if (!validFromLocations.contains(from.getType()) || !validToLocations.contains(to.getType())) {
            return false;
        }
        if ()
        return true;
    }

    private boolean canPlayAction(Action action) {
        switch (from.getType()) {
            case FACTORY:
                List<Tile> tiles = model.getFactories().get(from.getIndex());
                switch (to.getType()) {
                    case FACTORY:

                        model.isValidMoveFactoryFloorLine(tiles, 0);
                        break;
                    case MIDDLE:
                        break;
                    default:
                        return false;
                }
                break;
            case MIDDLE:
                switch (to.getType()) {
                    case PATTERN_LINE:
                        break;
                    case FLOOR_LINE:
                        break;
                    default:
                        return false;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    private DataObject performMove(PlayerData playerData, Action action) {
        return null;
    }

    @Override
    public Message execute(Executable executable) {
        if (!(executable instanceof Model) || message == null) {
            return null;
        }
        model = (Model) executable;

        PlayerData playerData = message.getPlayer();
        if (!isInGame(playerData)) {
            NotOkPlayerMove response = new NotOkPlayerMove(message.getId());
            response.setReason(reasonNotAPlayer);
            return response;
        }

        if (!model.isCurrentPlayer(playerData)) {
            NotOkPlayerMove response = new NotOkPlayerMove(message.getId());
            response.setReason(reasonNotTheirTurn);
            return response;
        }
        Action action = message.getAction();
        if (!isValidAction(action)) {
            NotOkPlayerMove response = new NotOkPlayerMove(message.getId());
            response.setReason(reasonNotAnAction);
            return response;
        }

        if (!canPlayAction(action)) {
            NotOkPlayerMove response = new NotOkPlayerMove(message.getId());
            response.setReason(reasonActionNotAllowed);
            return response;
        }
        OkPlayerMove response = new OkPlayerMove(message.getId());
        DataObject dataObject = performMove(playerData, action);
        response.setDataObject(dataObject);
        if (dataObject instanceof MoveUpdate) {
            response.setEndOfRound(false);
        } else if (dataObject instanceof RoundUpdate) {
            response.setEndOfRound(true);

        }
        return response;
    }

    @Override
    public void setMessage(Message message) {
        if (message instanceof PlayerMove) {
            this.message = (PlayerMove) message;
        }
    }

}
