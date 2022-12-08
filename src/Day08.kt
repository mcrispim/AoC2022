fun main() {

    data class Position(val line: Int, val col: Int)
    data class Tree(val position: Position, val height: Int)

    fun buildForest(input: List<String>): MutableMap<Position, Int> {
        val forest = mutableMapOf<Position, Int>()
        for ((line, lineTrees) in input.withIndex()) {
            for (col in lineTrees.indices) {
                forest[Position(line,col)] = lineTrees[col].digitToInt()
            }
        }
        return forest
    }

    fun viewTreesFromLeft(forest: MutableMap<Position, Int>, treesSeen: MutableSet<Position>, lines: Int, cols: Int) {
        for (line in 0 until lines) {
            var height = forest[Position(line, 0)]!! - 1
            for (col in 0 until cols) {
                val pos = Position(line, col)
                val treeHeight = forest[Position(line, col)]!!
                if (treeHeight > height) {
                    height = treeHeight
                    treesSeen.add(pos)
                } else
                    break
            }
        }
    }

    fun viewTreesFromRight(forest: MutableMap<Position, Int>, treesSeen: MutableSet<Position>, lines: Int, cols: Int) {
        for (line in 0 until lines) {
            var height = forest[Position(line, cols - 1)]!! - 1
            for (col in cols - 1..0) {
                val pos = Position(line, col)
                val treeHeight = forest[Position(line, col)]!!
                if (treeHeight > height) {
                    height = treeHeight
                    treesSeen.add(pos)
                } else
                    break
            }
        }
    }

    fun part1(input: List<String>): Int {
        val forest = buildForest(input)
        val treesSeen = mutableSetOf<Position>()
        val lines = input.size
        val cols = input[0].length
        viewTreesFromLeft(forest, treesSeen, lines, cols)
        viewTreesFromRight(forest, treesSeen, lines, cols)
        viewTreesFromTop(treesSeen, lines, cols)
        viewTreesFromBottom(treesSeen, lines, cols)
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 5)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
