import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.gothub.dudrie.hamster.importer.data.HamsterData
import de.gothub.dudrie.hamster.importer.data.InitialTerritoryData

const val someJson = "{\n" +
        "  \"territorySize\": {\n" +
        "    \"columnCount\": 5,\n" +
        "    \"rowCount\": 7\n" +
        "  },\n" +
        "  \"initialHamster\": {\n" +
        "    \"location\": {\n" +
        "      \"column\": 1,\n" +
        "      \"row\": 2\n" +
        "    },\n" +
        "    \"direction\": \"East\",\n" +
        "    \"grainCount\": 3\n" +
        "  },\n" +
        "  \"specialTiles\": [\n" +
        "    [\n" +
        "      {\n" +
        "        \"column\": 0,\n" +
        "        \"row\": 1\n" +
        "      },\n" +
        "      {\n" +
        "        \"location\": {\n" +
        "          \"column\": 0,\n" +
        "          \"row\": 1\n" +
        "        },\n" +
        "        \"tileType\": \"Wall\",\n" +
        "        \"grainCount\": 0\n" +
        "      }\n" +
        "    ],\n" +
        "    [\n" +
        "      {\n" +
        "        \"column\": 0,\n" +
        "        \"row\": 4\n" +
        "      },\n" +
        "      {\n" +
        "        \"location\": {\n" +
        "          \"column\": 0,\n" +
        "          \"row\": 4\n" +
        "        },\n" +
        "        \"tileType\": \"Wall\",\n" +
        "        \"grainCount\": 0\n" +
        "      }\n" +
        "    ],\n" +
        "    [\n" +
        "      {\n" +
        "        \"column\": 4,\n" +
        "        \"row\": 6\n" +
        "      },\n" +
        "      {\n" +
        "        \"location\": {\n" +
        "          \"column\": 4,\n" +
        "          \"row\": 6\n" +
        "        },\n" +
        "        \"tileType\": \"Wall\",\n" +
        "        \"grainCount\": 0\n" +
        "      }\n" +
        "    ],\n" +
        "    [\n" +
        "      {\n" +
        "        \"column\": 3,\n" +
        "        \"row\": 2\n" +
        "      },\n" +
        "      {\n" +
        "        \"location\": {\n" +
        "          \"column\": 3,\n" +
        "          \"row\": 2\n" +
        "        },\n" +
        "        \"tileType\": \"Floor\",\n" +
        "        \"grainCount\": 8\n" +
        "      }\n" +
        "    ],\n" +
        "    [\n" +
        "      {\n" +
        "        \"column\": 1,\n" +
        "        \"row\": 2\n" +
        "      },\n" +
        "      {\n" +
        "        \"location\": {\n" +
        "          \"column\": 1,\n" +
        "          \"row\": 2\n" +
        "        },\n" +
        "        \"tileType\": \"Floor\",\n" +
        "        \"grainCount\": 2\n" +
        "      }\n" +
        "    ]\n" +
        "  ]\n" +
        "}"

fun main() {
    val data = InitialTerritoryData(
        territorySize = Size(5, 7),
        initialHamster = HamsterData(location = Location(1, 2), direction = Direction.East, grainCount = 3)
    )

    data.addWallTile(Location(0, 1))
    data.addWallTile(Location(0, 4))
    data.addWallTile(Location(4, 6))

    data.addGrains(8, Location(3, 2))
    data.addGrains(2, Location(1, 2))

    println(data.toJson())

    val parsedData = InitialTerritoryData.fromJson(someJson)
    println(parsedData.getSpecialTiles().size)
}
