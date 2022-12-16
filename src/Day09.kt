import kotlin.Exception

fun main() {

    data class Pos(var x: Int, var y: Int) {
        fun move(dir: String) {
            when (dir) {
                "L" -> x--
                "R" -> x++
                "U" -> y++
                "D" -> y--
                "LU" -> { x--; y++ }
                "RU" -> { x++; y++ }
                "LD" -> { x--; y-- }
                "RD" -> { x++; y-- }
                else -> throw Exception("Invalid movement: [$dir]")
            }
        }

        fun around(): MutableMap<Pos, String> {
            val result = mutableMapOf<Pos, String>()
            val directions = listOf(
                "LU", "LU", "U" , "RU", "RU" ,
                "LU", "ST", "ST", "ST", "RU",
                "L" , "ST", "ST", "ST", "R" ,
                "LD", "ST", "ST", "ST", "RD",
                "LD" , "LD", "D" , "RD", "RD" ,
            )
            var i = 0
            for (row in 2 downTo -2) {
                for (col in - 2..2) {
                    result[Pos(col + x, row + y)] = directions[i]
                    i++
                }
            }
            return result
        }
    }

    fun moveTailIfNecessary(tail: Pos, head: Pos) {
        val aroundMap = tail.around()
        when (val dir = aroundMap[head]!!) {
            "ST" -> return
            "." -> throw Exception("BIG ERROR")
            else -> tail.move(dir)
        }
        return

    }

    fun parseInput(input: List<String>): String =
        input.joinToString("") { row ->
            val dir = row.substringBefore(" ")
            val units = row.substringAfter(' ').toInt()
            dir.repeat(units)
        }

    fun part1(input: List<String>): Int {
        val head = Pos(0, 0)
        val tail = Pos(0, 0)
        val tailSet = mutableSetOf(Pos(0, 0))
        val headDirections = parseInput(input)
        headDirections.forEach { dir ->
            head.move(dir.toString())
            moveTailIfNecessary(tail, head)
            tailSet.add(Pos(tail.x, tail.y))
        }
        return tailSet.size
    }

    fun moveRope(rope: List<Pos>, dir: Char) {
        rope[0].move(dir.toString())
        for (tail in 1..rope.lastIndex) {
            moveTailIfNecessary(rope[tail], rope[tail - 1])
        }

    }

    fun part2(input: List<String>): Int {
        val rope = List(10) { Pos(0, 0)}
        val tailSet = mutableSetOf(Pos(0, 0))
        val headDirections = parseInput(input)
        headDirections.forEach { dir ->
            moveRope(rope, dir)
            tailSet.add(Pos(rope.last().x, rope.last().y))
        }
        return tailSet.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
