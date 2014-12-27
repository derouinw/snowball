package derouinw.snowball.client.Map;

/**
 * The possible types of a map tile
 */
public enum TileType {
    Grass, Dirt, Stone;

    public static final int size = TileType.values().length;
}
