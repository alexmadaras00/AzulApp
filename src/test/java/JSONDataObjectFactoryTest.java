import org.junit.jupiter.api.Test;

import dataobjects.*;

import model.Color;
import model.PlayerTile;
import model.Tile;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


// One thing to note is that not all length of the lists make sense. E.g. a factory can have up to 8 tiles, since
// we generalized getting random Tiles. However, while it makes it less representative of the final objects,
// it does test it more thoroughly.
public class JSONDataObjectFactoryTest {
    
    @Test
    public void testMoveWithoutNulls() {
        Move move = randomMove(false);
        JSONDataObjectFactory<Move> factory = new JSONDataObjectFactory<Move>();
        assertEquals(move, factory.fromJSON(factory.toJSON(move)));
    }

    @Test
    public void testMoveWithNulls() {
        Move move = randomMove(true);
        JSONDataObjectFactory<Move> factory = new JSONDataObjectFactory<Move>();
        assertEquals(move, factory.fromJSON(factory.toJSON(move)));
    }

    private static Boolean randomBoolean() {
        return new Boolean[] {false,true}[new Random().nextInt(2)];
    }

    private static int randomInt() {
        return new Random().nextInt();
    }

    private static String randomString() {
        return new String[] {"player 1", "steve", "Kirk", "Clark", "RandomPlayer"}[new Random().nextInt(5)];
    }

    private static Color randomColor() {
        return Color.values()[new Random().nextInt(Color.values().length)]; 
    }

    private static Tile randomTile() {
        if (new Random().nextInt(8)==1) {
            return PlayerTile.getInstance();
        } else {
            return randomColor();
        }
    }

    private static List<Tile> randomTileList() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(8); i++) {
            tiles.add(randomTile());
        }
        return tiles;
    }

    private static List<List<Tile>> randomTileTable() {
        List<List<Tile>> tiles = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(8); i++) {
            tiles.add(randomTileList());
        }
        return tiles;
    }

    private static OptionalInt randomOptionalInt(Boolean isNull) {
        if (isNull) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(randomInt());
        }
    }

    private static Optional<String> randomOptionalString(Boolean isNull) {
        if (isNull) {
            return Optional.empty();
        } else {
            return Optional.of(randomString());
        }
    }

    private static Optional<Color> randomOptionalColor(Boolean isNull) {
        if (isNull) {
            return Optional.empty();
        } else {
            return Optional.of(randomColor());
        }
    }

    private static Optional<List<Tile>> randomOptionalTileList(Boolean isNull) {
        if (isNull) {
            return Optional.empty();
        } else {
            return Optional.of(randomTileList());
        }
    }

    private static Optional<List<List<Tile>>> randomOptionalTileTable(Boolean isNull) {
        if (isNull) {
            return Optional.empty();
        } else {
            return Optional.of(randomTileTable());
        }
    }

    private static ScoreChange randomScoreChange(Boolean isNull) {
        ScoreChange scoreChange = new ScoreChange();
        scoreChange.setIsRoundScore(randomBoolean());
        scoreChange.setIsPatternLine(randomBoolean());
        scoreChange.setColor(randomOptionalColor(isNull));
        scoreChange.setRow(randomOptionalInt(isNull));
        scoreChange.setColumn(randomOptionalInt(isNull));
        scoreChange.setScoreDifference(randomInt());
        return scoreChange;
    }

    private static PlayerBoardState randomPlayerBoardState(Boolean isNull) {
        PlayerBoardState playerBoardState = new PlayerBoardState();
        playerBoardState.setScore(randomInt());
        List<ScoreChange> scoreChanges = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(8); i++) {
            scoreChanges.add(randomScoreChange(isNull));
        }
        playerBoardState.setScoreChanges(scoreChanges);
        playerBoardState.setPatternLine(randomTileTable());
        playerBoardState.setFloorLine(randomTileList());
        playerBoardState.setWall(randomTileTable());
        return playerBoardState;
    }

    private static Optional<PlayerBoardState> randomOptionalPlayerBoardState(Boolean isNull) {
        if (isNull) {
            return Optional.empty();
        } else {
            return Optional.of(randomPlayerBoardState(isNull));
        }
    }

    private static PlayerData randomPlayerData(Boolean isNull) {
        PlayerData playerData = new PlayerData();
        playerData.setName(randomOptionalString(isNull));
        playerData.setIdentifier(randomInt());
        playerData.setPlayerBoard(randomOptionalPlayerBoardState(isNull));
        return playerData;
    }

    private static Move randomMove(Boolean isNull) {
        Move move = new Move();
        move.setIsFactory(randomBoolean());
        move.setNumber(randomOptionalInt(isNull));
        move.setType(randomColor());
        move.setPlayer(randomPlayerData(isNull));
        return move;
    }

    private static GameUpdate randomGameUpdate(Boolean isNull) {
        GameUpdate gameUpdate = new GameUpdate();
        List<PlayerData> players = new ArrayList<>();
        for (int i=0; i < new Random().nextInt(3)+1; i++) {
            players.add(randomPlayerData(isNull));
        }
        gameUpdate.setPlayers(players);
        gameUpdate.setFactories(randomOptionalTileTable(isNull));
        gameUpdate.setMiddle(randomOptionalTileList(isNull));
        gameUpdate.setMove(randomMove(isNull));
        gameUpdate.setNextPlayer(randomInt());
        return gameUpdate;
    }

    private static GameState randomGameState(Boolean isNull) {
        GameState gameState = new GameState();
        List<PlayerData> players = new ArrayList<>();
        for (int i=0; i < new Random().nextInt(3)+1; i++) {
            players.add(randomPlayerData(isNull));
        }
        gameState.setPlayers(players);
        gameState.setFactories(randomTileTable());
        gameState.setMiddle(randomTileList());    
        gameState.setCurrentPlayer(randomInt());
        return gameState;
    }
}
