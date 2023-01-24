import kotlin.math.abs

data class PositionDay15(val x: Int, val y: Int)

class CaveDay15(input: List<String>) {
    val area = mutableMapOf<PositionDay15, Char>()
    val covertAreas = mutableMapOf<PositionDay15, Int>()

    init {
        input.forEach {
            // Format = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
            val (sensorPartStr, beaconPartStr) = it.split(": ")
            val (sensorXStr, sensorYStr) = sensorPartStr.split(", ")
            val sensorX = sensorXStr.substringAfter("x=").toInt()
            val sensorY = sensorYStr.substringAfter("y=").toInt()
            val (beaconXStr, beaconYStr) = beaconPartStr.split(", ")
            val beaconX = beaconXStr.substringAfter("x=").toInt()
            val beaconY = beaconYStr.substringAfter("y=").toInt()

            val sensor = PositionDay15(sensorX, sensorY)
            val beacon = PositionDay15(beaconX, beaconY)

            area[sensor] = 'S'
            area[beacon] = 'B'
//            println("  Sensor: $sensorX, $sensorY ==> Beacon: $beaconX, $beaconY")
            val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
            covertAreas[sensor] = distance
        }
    }
}

fun main() {

    fun part1(input: List<String>, line: Int): Int {
        val cave = CaveDay15(input)
        val mainAreas = cave.covertAreas
            .filter { (pos, dist) -> line in (pos.y - dist)..(pos.y + dist) }
            .map { (pos, dist) ->
                val d = dist - abs(pos.y - line)
                pos.x - d..pos.x + d
            }
        var lineSet = setOf<Int>()
        mainAreas.forEach { lineSet = lineSet.union(it) }
        val empties = lineSet.size
        val sensorsAndBeacons = cave.area.filter { (pos, c) -> pos.y == line }.count()
        return empties - sensorsAndBeacons
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput, 10) == 26)
    check(part2(testInput) == 14)

    val input = readInput("Day15")
    println(part1(input, 2_000_000))
    println(part2(input))
}
