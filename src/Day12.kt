import java.security.InvalidParameterException

fun main() {
    data class Pos(val line: Int, val col: Int)
    data class Node(val elevation: Char, val possibleWays: List<Pos>) {
        var distance: Int = Int.MAX_VALUE
        var comeFrom: Pos? = null
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

        fun Pos.isValid() = line in startLine..endLine && col in startCol..endCol

        val possibleWays = listOf(
            Pos(pos.line - 1, pos.col),
            Pos(pos.line, pos.col - 1),
            Pos(pos.line, pos.col + 1),
            Pos(pos.line + 1, pos.col)
        )
        val ways = mutableListOf<Pos>()
        for (p in possibleWays) {
            if (p.isValid() && elevationOk(input, p) in 'a'..elevation + 1) {
                ways.add(p)
            }
        }
        return ways
    }

    fun getGrafo(input: List<String>): Map<Pos, Node> {
        val graph = mutableMapOf<Pos, Node>()
        for ((lineNumber, line) in input.withIndex()) {
            for (colNumber in line.indices) {
                val pos = Pos(lineNumber, colNumber)
                graph[pos] = Node(elevationOk(input, pos), getWays(pos, input))
            }
        }
        return graph
    }

    fun getDistance(graph: Map<Pos, Node>, start: Pos, end: Pos): Int {
        val unvisetedNodes = mutableListOf<Pos>()
        graph[start]?.distance = 0
        unvisetedNodes.add(start)
        while (unvisetedNodes.isNotEmpty()) {
            val current = unvisetedNodes.removeAt(0)
            for (pos in graph[current]!!.possibleWays) {
                if (graph[pos]!!.distance > graph[current]!!.distance + 1) {
                    graph[pos]!!.distance = graph[current]!!.distance + 1
                    graph[pos]!!.comeFrom = current
                    unvisetedNodes.add(pos)
                }
            }
        }
        return graph[end]!!.distance
    }

    fun getStartEnd(input: List<String>): Pair<Pos, Pos> {
        var start: Pos? = null
        var end: Pos? = null
        for ((l, line) in input.withIndex()) {
            for ((c, elevation) in line.withIndex()) {
                if (elevation == 'S')
                    start = Pos(l, c)
                if (elevation == 'E')
                    end = Pos(l, c)
            }
        }
        if (start != null && end != null)
            return Pair(start, end)
        else
            throw InvalidParameterException("Não encontrado o <S>tart ou o <E>nd.")
    }

    fun part1(input: List<String>): Int {
        val graph = getGrafo(input)
        val (start, end) = getStartEnd(input)
        return getDistance(graph, start, end)
    }

    fun getStarts(input: List<String>): List<Pos> {
        val starts = mutableListOf<Pos>()
        for ((l, line) in input.withIndex()) {
            for ((c, elevation) in line.withIndex()) {
                if (elevation == 'S' || elevation == 'a')
                    starts.add(Pos(l, c))
            }
        }
        return starts.toList()
    }

    fun part2(input: List<String>): Int {
        val graph = getGrafo(input)
        val (_, end) = getStartEnd(input)
        val starts = getStarts(input)
        var minDistance = Int.MAX_VALUE
        for (start in starts) {
            val distance = getDistance(graph, start, end)
            if (distance < minDistance) {
                minDistance = distance
            }
        }
        return minDistance
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
