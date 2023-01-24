import kotlin.math.abs

data class PositionDay15(val x: Int, val y: Int)

class CaveDay15(input: List<String>) {
    val area = mutableMapOf<PositionDay15, Char>()
    /*
        private var minX = Int.MAX_VALUE
        private var maxX = Int.MIN_VALUE
        private var minY = Int.MAX_VALUE
        private var maxY = Int.MIN_VALUE
    */

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
            println("  Sensor: $sensorX, $sensorY ==> Beacon: $beaconX, $beaconY")
            val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)

            for (x in sensor.x - distance..sensor.x + distance) {
                val dy = distance - abs(sensor.x - x)
                for (y in sensor.y - dy..sensor.y + dy) {
                    val pos = PositionDay15(x, y)
                    if (area[pos] == null) {
                        area[pos] = '#'
                    }
                }
            }
        }
    }
}

fun main() {

    fun part1(input: List<String>, line: Int): Int {
        val cave = CaveDay15(input)
        val empties = cave.area.entries.filter { (pos, c) -> pos.y == line && c == '#' }.count()
        return empties
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
