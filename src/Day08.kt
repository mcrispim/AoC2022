fun main() {

    data class Position(val line: Int, val col: Int)
    class Forest2 (input: List<String>) {
        val treeMap = mutableMapOf<Position, Int>()
        val lines = input.size
        val cols = input.first().length
        init {
            for ((line, lineTrees) in input.withIndex()) {
                for (col in lineTrees.indices) {
                    treeMap[Position(line,col)] = lineTrees[col].digitToInt()
                }
            }
        }

        fun getScoreDown(p: Position): Int {
            var score = 0
            val height = treeMap[p]!!
            for (line in p.line + 1 until lines) {
                score++
                if (treeMap[Position(line, p.col)]!! < height) {
                    continue
                } else
                    break
            }
            return score
        }
        fun getScoreUp(p: Position): Int {
            var score = 0
            val height = treeMap[p]!!
            for (line in p.line - 1 downTo 0) {
                score++
                if (treeMap[Position(line, p.col)]!! < height) {
                    continue
                } else
                    break
            }
            return score
        }
        fun getScoreLeft(p: Position): Int {
            var score = 0
            val height = treeMap[p]!!
            for (col in p.col - 1 downTo 0) {
                score++
                if (treeMap[Position(p.line, col)]!! < height) {
                    continue
                } else
                    break
            }
            return score
        }
        fun getScoreRight(p: Position): Int {
            var score = 0
            val height = treeMap[p]!!
            for (col in p.col + 1 until cols) {
                score++
                if (treeMap[Position(p.line, col)]!! < height) {
                    continue
                } else
                    break
            }
            return score
        }
        fun getScenicScore(p: Position): Int {
            val scoreUp = getScoreUp(p)
            val scoreDown = getScoreDown(p)
            val scoreLeft = getScoreLeft(p)
            val scoreRight = getScoreRight(p)
            return scoreUp * scoreDown * scoreLeft * scoreRight
        }
        fun viewTreesFromLeft(treesSeen: MutableSet<Position>) {
            for (line in 0 until lines) {
                var height = treeMap[Position(line, 0)]!! - 1
                for (col in 0 until cols) {
                    val pos = Position(line, col)
                    val treeHeight = treeMap[Position(line, col)]!!
                    if (treeHeight > height) {
                        height = treeHeight
                        treesSeen.add(pos)
                    } else
                        continue
                }
            }
        }
        fun viewTreesFromRight(treesSeen: MutableSet<Position>) {
            for (line in 0 until lines) {
                var height = treeMap[Position(line, cols - 1)]!! - 1
                for (col in cols - 1 downTo 0) {
                    val pos = Position(line, col)
                    val treeHeight = treeMap[Position(line, col)]!!
                    if (treeHeight > height) {
                        height = treeHeight
                        treesSeen.add(pos)
                    } else
                        continue
                }
            }
        }
        fun viewTreesFromBottom(treesSeen: MutableSet<Position>) {
            for (col in 0 until cols) {
                var height = treeMap[Position(lines - 1, col)]!! - 1
                for (line in lines - 1 downTo 0) {
                    val pos = Position(line, col)
                    val treeHeight = treeMap[Position(line, col)]!!
                    if (treeHeight > height) {
                        height = treeHeight
                        treesSeen.add(pos)
                    } else
                        continue
                }
            }
        }
        fun viewTreesFromTop(treesSeen: MutableSet<Position>) {
            for (col in 0 until cols) {
                var height = treeMap[Position(0, col)]!! - 1
                for (line in 0 until lines) {
                    val pos = Position(line, col)
                    val treeHeight = treeMap[Position(line, col)]!!
                    if (treeHeight > height) {
                        height = treeHeight
                        treesSeen.add(pos)
                    } else
                        continue
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val forest = Forest2(input)
        val treesSeen = mutableSetOf<Position>()
        forest.viewTreesFromLeft(treesSeen)
        forest.viewTreesFromRight(treesSeen)
        forest.viewTreesFromTop(treesSeen)
        forest.viewTreesFromBottom(treesSeen)
        return treesSeen.size
    }

    fun part2(input: List<String>): Int {
        val forest = Forest2(input)
        var biggestScenicScore = Int.MIN_VALUE
        for (p in forest.treeMap.keys) {
            val scenicScore = forest.getScenicScore(p)
            if (scenicScore > biggestScenicScore) {
                biggestScenicScore = scenicScore
            }
        }
        return biggestScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
