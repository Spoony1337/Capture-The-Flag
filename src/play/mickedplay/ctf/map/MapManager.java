package play.mickedplay.ctf.map;

import org.bukkit.inventory.ItemStack;
import play.mickedplay.ctf.CaptureTheFlag;
import play.mickedplay.ctf.misc.Helper;

import java.io.File;
import java.util.*;

/**
 * Created by mickedplay on 25.04.2016 at 17:21 UTC+1.
 * You are not allowed to remove this comment.
 */
public class MapManager {

    private final CaptureTheFlag ctf;
    private final HashMap<ItemStack, GameMap> voteItems;
    private HashMap<String, GameMap> gameMaps;
    private boolean canVote;

    public MapManager(CaptureTheFlag ctf) {
        this.ctf = ctf;
        this.gameMaps = new HashMap<>();
        this.voteItems = new HashMap<>();
        this.canVote = true;
        this.initializeGameMaps();
    }

    /*
        Ermittelt {anzahlAnMaps} zufällige Maps, die zur Spielabstimmung zur Verfügung stehen
        @param random - Objekt von Random
        @param mapObjects - HashMap, aus der drei zufällige Maps ausgewählt werden, die gespielt werden können
        @return null
     */
    private void initializeGameMaps() {
        Random random = new Random();
        HashMap<String, File> possibleMaps = this.getPossibleMaps();
        List<String> mapKeys = new ArrayList<>(possibleMaps.keySet());
        int amountCount = mapKeys.size() < 3 ? mapKeys.size() : 3;
        for (int i = 0; i < amountCount; i++) {
            String name = mapKeys.get(random.nextInt(mapKeys.size()));
            mapKeys.remove(name);
            this.gameMaps.put(name, new GameMap(this, name, possibleMaps.get(name)));
        }
    }

    /*
        Durchsucht den Serverordner nach Ordnern, die eine info.json-Datei beinhalten und als potenzielle Maps für's Game dienen. Ausgenommen ist der Ordner der Lobby.
        @return HashMap<String, File> - Gibt eine HashMap zurück, die als KEY den Ordnernamen und als VALUE die info.json-Datei beinhaltet
     */
    private HashMap<String, File> getPossibleMaps() {
        String[] availableMaps = new File(Helper.serverDir).list((current, name) -> new File(current, name).isDirectory());

        ArrayList<String> mapList = new ArrayList<>();
        Collections.addAll(mapList, availableMaps);

        HashMap<String, File> validMaps = new HashMap<>();
        for (String folder : mapList) {
            File jsonFile = new File(Helper.serverDir.substring(0, Helper.serverDir.length() - 1) + folder, "info.json");
            if (jsonFile.exists() && !folder.equalsIgnoreCase("lobby")) validMaps.put(folder, jsonFile);
        }
        return validMaps;
    }

    /*
        Ermittelt aus den abstimmbaren Maps die mit den meisten Votes. Um bei gleichen Mapsauswahlen bei Serverstarts immer die selbe Map zu vermeiden, wird die Liste vorher zufällig gemischt.
        @param mapHashMap - HashMap, die die Namen der einzelnen Maps als KEY und deren Map-Objekte als VALUE enthalten
        @return GameMap - Gibt das Map-Objekt zurück, welches die meisten Abstimmungen erhalten hat.
     */
    public GameMap getHighestVotedMap() {
        List<GameMap> gameMaps = new ArrayList<>(this.gameMaps.values());
        Collections.shuffle(gameMaps);
        Collections.sort(gameMaps, (first, second) -> Integer.valueOf(first.getVotes()).compareTo(second.getVotes()));
        return gameMaps.get(gameMaps.size() - 1);
    }

    public GameMap getMapByVoteItem(ItemStack voteItem) {
        return this.voteItems.get(voteItem);
    }

    public void addVoteItem(GameMap gameMap) {
        this.voteItems.put(gameMap.getVoteItemStack(), gameMap);
    }

    public HashMap<ItemStack, GameMap> getVoteItems() {
        return voteItems;
    }

    public HashMap<String, GameMap> getGameMaps() {
        return gameMaps;
    }

    public boolean canVote() {
        return canVote;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public CaptureTheFlag getCTF() {
        return ctf;
    }
}