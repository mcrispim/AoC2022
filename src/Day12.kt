fun main() {
    data class Pos(val line: Int, val col: Int)
    data class Node(val elevation: Char, val possibleWays: List<Pos>) {
        var distance: Int = Int.MAX_VALUE
    }

    fun elevationOk(input: List<String>, pos: Pos): Char {
        val elevation = when (input[pos.line][pos.col]) {
            'S' -> 'a'
            'E' -> 'z'
            else -> input[pos.line][pos.col]
        }
        return elevation
    }

    fun getWays(pos: Pos, input: List<String>): List<Pos> {
        val startLine = 0
        val endLine = input.lastIndex
        val startCol = 0
        val endCol = input[0].lastIndex
        val elevation = elevationOk(input, pos)

        fun Pos.isValid() = this.line in startLine..endLine && this.col in startCol..endCol

        val possibleWays = listOf<Pos>(
            Pos(pos.line - 1, pos.col),
            Pos(pos.line, pos.col - 1),
            Pos(pos.line, pos.col + 1),
            Pos(pos.line + 1, pos.col)
        )
        val ways = mutableListOf<Pos>()
        for (p in possibleWays) {
            if (p.isValid() && elevationOk(input, p) in elevation..elevation + 1) {
                ways.add(p)
            }
        }
        return ways
    }

    fun getGrafo(input: List<String>): Map<Pos, Node> {
        val graph = mutableMapOf<Pos, Node>()
        for ((lineNumber, line) in input.withIndex()) {
            for ((colNumber, elevation) in line.withIndex()) {
                val pos = Pos(lineNumber, colNumber)
                graph[pos] = Node(elevationOk(input, pos), getWays(pos, input))
            }
        }

        return graph
    }

    fun part1(input: List<String>): Int {
        val graph = getGrafo(input)
        val unvisetedNodes = mutableListOf<Pos>()
        for (p in graph.keys) {
            unvisetedNodes.add(p)
        }
        val start = Pos(0, 0)
        graph[start]?.distance = 0
        unvisetedNodes.remove(start)
        val current = start
        for (p in graph[current].)


        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 5)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
