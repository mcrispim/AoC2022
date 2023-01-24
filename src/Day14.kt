const val HOLE = 500

data class Position(val line: Int, val col: Int)

class Cave(input: List<String>) {
    private val area = mutableMapOf<PositionDay15, Char>()
    private var minCol = Int.MAX_VALUE
    private var maxCol = Int.MIN_VALUE
    private var minLine = 0
    private var maxLine = Int.MIN_VALUE
    var grains = 0
    private val floor: Int

    init {
        for (line in input) {
            for (xys in line.split(" -> ")) {
                val (x, y) = xys.split(",").map { it.toInt() }
                if (x > maxCol) maxCol = x
                if (x < minCol) minCol = x
                if (y > maxLine) maxLine = y
                if (y < minLine) minLine = y
            }
        }
        floor = maxLine + 2

        for (line in input) {
            val columnLine = line.split(" -> ").windowed(2)
            for (fromTo in columnLine) {
                var (col1, line1) = fromTo[0].split(",").map { it.toInt() }
                var (col2, line2) = fromTo[1].split(",").map { it.toInt() }
                if (col1 == col2) {
                    if (line1 > line2) {
                        val tempY = line1
                        line1 = line2
                        line2 = tempY
                    }
                    for (l in line1..line2)
                        area[PositionDay15(l, col1)] = '#'           // rock
                } else {
                    if (col1 > col2) {
                        val tempX = col1
                        col1 = col2
                        col2 = tempX
                    }
                    for (col in col1..col2)
                        area[PositionDay15(line1, col)] = '#'           // rock
                }
            }
        }
    }

    fun dropSand1(): Boolean {
        var col = HOLE
        var line = 1
        var lastLine = line
        var desvio = isPossibleToFall(line, col)
        while (desvio != null && line < maxLine) {
            col += desvio
            lastLine = line
            line++
            desvio = isPossibleToFall(line, col)
        }
        return if (desvio == null) {
            area[PositionDay15(lastLine, col)] = 'o'
            grains++
            true
        } else
            false
    }

    fun dropSandTillFloor(): Boolean {
        var col = HOLE
        var line = 1
        var lastLine = line
        var desvio: Int? = isPossibleToFall(line, col) ?: return false
        while (desvio != null) {
            col += desvio
            if (col < minCol)
                minCol--
            if (col > maxCol)
                maxCol++
            lastLine = line
            line++
            desvio = isPossibleToFall(line, col)
        }
        area[PositionDay15(lastLine, col)] = 'o'
        grains++
        return true
    }

    private fun isPossibleToFall(line: Int, col: Int): Int? {
        if (line == floor)
            return null
        if (area[PositionDay15(line, col)] == null)
            return 0
        if (area[PositionDay15(line, col - 1)] == null)
            return -1
        if (area[PositionDay15(line, col + 1)] == null)
            return 1
        return null
    }

    fun showCave() {
        println("  0 ${".".repeat(HOLE - minCol + 1)}+${".".repeat(maxCol - HOLE + 1)}")
        for (line in 1 until floor) {
            print("%3s ".format(line))
            for (col in minCol - 1..maxCol + 1) {
                print(area[PositionDay15(line, col)] ?: ".")
            }
            println()
        }
        println("$floor ${"#".repeat(maxCol- minCol + 3)}")
    }
}


fun main() {

    fun part1(input: List<String>): Int {
        val cave = Cave(input)
//        cave.showCave()
        while (cave.dropSand1()) {
//            println()
//            cave.showCave()
        }
//        println("${cave.grains}")
        return cave.grains
    }

    fun part2(input: List<String>): Int {
        val cave = Cave(input)
//        cave.showCave()
//        println()
        while (cave.dropSandTillFloor()) {
//            cave.showCave()
//            println()
        }
//        cave.showCave()
        return cave.grains + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
