package org.feather.game.model.region;

import org.feather.game.Configuration;
import org.feather.game.model.EntityList;
import org.feather.game.model.Location;
import org.feather.game.model.item.grounditem.GroundItem;
import org.feather.game.model.npc.Npc;
import org.feather.game.model.object.WorldObject;
import org.feather.game.model.player.Player;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Albert
 * Date: 8/25/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Region {

    private List<Player> players = new EntityList<>(Configuration.MAX_PLAYERS);

    private List<Npc> npcs = new EntityList<>(Configuration.MAX_NPCS);

    private List<WorldObject> objects = new EntityList<>(1000);

    private List<GroundItem> groundItems = new EntityList<>(200);

    private Location location;

    public Region(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<Npc> getNpcs() {
        return Collections.unmodifiableList(npcs);
    }

    public List<WorldObject> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public List<GroundItem> getGroundItems() {
        return Collections.unmodifiableList(groundItems);
    }

}
