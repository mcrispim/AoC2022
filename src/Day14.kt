class Cave(input: List<String>) {
    var minX = Int.MAX_VALUE
    var maxX = Int.MIN_VALUE
    var minY = 0
    var maxY = Int.MIN_VALUE
    var grains = 0

    init {
        for (line in input) {
            for (xys in line.split(" -> ")) {
                val (x, y) = xys.split(",").map { it.toInt() }
                if (x > maxX) maxX = x
                if (x < minX) minX = x
                if (y > maxY) maxY = y
//                if (y < minY) minY = y
            }
        }
    }
    val floor = maxY + 1
    val spaceAround = floor
    val area = Array(floor) { Array(maxX - minX + (2 * spaceAround)) { '.' } }
    val xCorrection = 500 - minX + spaceAround

    init {
        for (line in input) {
            val xys = line.split(" -> ").windowed(2)
            for (fromTo in xys) {
                var (x1, y1) = fromTo[0].split(",").map { it.toInt() }
                var (x2, y2) = fromTo[1].split(",").map { it.toInt() }
                if (x1 == x2) {
                    if (y1 > y2) {
                        val tempY = y1
                        y1 = y2
                        y2 = tempY
                    }
                    for (y in y1..y2)
                        area[y - 1][x1 - minX + 1] = '#'           // rock
                } else {
                    if (x1 > x2) {
                        val tempX = x1
                        x1 = x2
                        x2 = tempX
                    }
                    for (x in x1..x2)
                        area[y1 - 1][x - minX + 1] = '#'           // rock
                }
            }
        }
    }

    fun showCave() {
        println("  ${".".repeat(500 - minX + 1)}+${".".repeat(maxX - 500 + 1)}")
        for (y in 0 until maxY) {
            print("${y} ")
            for (x in 0..maxX - minX + 2) {
                print(area[y][x])
            }
            println()
        }
    }

    fun dropSand(toFloor: Boolean = false): Boolean {
        var x = 500 - minX + 1
        val startX = x
        var y = 0
        var lastX = x
        var lastY = -1
        while (isPossibleToFall(y, x, toFloor)) {
            if (area[y][x] == '.') {
                lastY = y
                y++
                continue
            }
            if (area[y][x - 1] == '.') {
                lastX = x - 1
                lastY = y
                y++
                x--
                continue
            }
            lastX = x + 1
            lastY = y
            y++
            x++
        }
        if (!toFloor) {
            if (lastY < maxY - 1) {
                area[lastY][lastX] = 'o'
                grains++
                return true
            } else
                return false
        } else {

                area[lastY][lastX] = 'o'
                grains++
            return lastY != 0
        }
    }

    private fun isPossibleToFall(y: Int, x: Int, toFloor: Boolean = false): Boolean {
        if (y == if (toFloor) floor else maxY)
            return false
        if (area[y][x] == '.')
            return true
        if (x >= 1 && area[y][x - 1] == '.')
            return true
        if (x <= maxX - minX - 1 && area[y][x + 1] == '.')
            return true
        return false
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val cave = Cave(input)
  //      cave.showCave()
        while (cave.dropSand()) {
  //          println()
  //          cave.showCave()
        }
        println(cave.grains)
        return cave.grains
    }

    fun part2(input: List<String>): Int {
        val cave = Cave(input)
        cave.showCave()
        while (cave.dropSand(toFloor = true)) {
            println()
            cave.showCave()
        }
        return cave.grains
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
