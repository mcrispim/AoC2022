import kotlin.Exception

enum class Dir(val id: String){
    UP("U"), DOWN("D") , LEFT("L"), RIGHT("L"),
    LEFT_UP("LU"), LEFT_DOWN("LD"), RIGHT_UP("RU"), RIGHT_DOWN("RD")
}
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
        fun neighbours(): Set<Pos> {
            return setOf(
                Pos(x - 1, y - 1), Pos(x, y - 1), Pos(x + 1, y - 1),
                Pos(x - 1,    y),     Pos(x,    y),     Pos(x + 1,    y),
                Pos(x - 1, y + 1), Pos(x, y + 1), Pos(x + 1, y + 1))
        }
        fun around(): MutableMap<Pos, String> {
            val result = mutableMapOf<Pos, String>()
            val directions = listOf(
                "." , "LU", "U" , "RU", "." ,
                "LU", "ST", "ST", "ST", "RU",
                "L" , "ST", "ST", "ST", "R" ,
                "LD", "ST", "ST", "ST", "RD",
                "." , "LD", "D" , "RD", "." ,
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

    fun isNeededHorizontalVerticalMove(tail: Pos, head: Pos): String {
        if (tail.x == head.x)
            return if (tail.y > head.y) "D" else "U"
        else if (tail.y == head.y)
            return if (tail.x > head.x) "L" else "R"
        else return ""
    }

    fun isNeededDiagonalMove(tail: Pos, head: Pos): String {
        if (kotlin.math.abs(tail.x - head.x) >= 2) {
            if (tail.x - head.x > 0) {
                return if (tail.y > head.y) "LD" else "LU"
            } else {
                return if (tail.y > head.y) "RD" else "RU"
            }
        } else if (kotlin.math.abs(tail.y - head.y) >= 2) {
            if (tail.y - head.y > 0) {
                return if (tail.x > head.x) "LD" else "RD"
            } else {
                return if (tail.x > head.x) "LU" else "RU"
            }
        }
        return ""
    }

    fun headAndTailAreTouching(tail: Pos, head: Pos): Boolean {
        return tail in head.neighbours()
    }

    fun moveTailIfNecessary(tail: Pos, head: Pos, tailSet: MutableSet<Pos>) {
        val dir = tail.around()[head]!!
        when (dir) {
            "ST" -> return
            "." -> throw Exception("BIG ERROR")
            else -> {
                tail.move(dir)
                tailSet.add(tail)
            }
        }
        return

        /*
        if (headAndTailAreTouching(tail, head))
            return
        var dir = isNeededHorizontalVerticalMove(tail, head)
        if (dir.isEmpty()) {
            dir = isNeededDiagonalMove(tail, head)
        }
        tail.move(dir)
        tailSet.add(tail)
        return
         */

    }

    fun part1(input: List<String>): Int {
        val head = Pos(0, 0)
        val tail = Pos(0, 0)
        val tailSet = mutableSetOf(tail)
        input.forEachIndexed { index, s ->
//            println("$index [$s]")
            val args = s.split(" ")
            val dir = args[0]
            val units = args[1].toInt()
            repeat(units) {
                head.move(dir)
                moveTailIfNecessary(tail, head, tailSet)
            }
        }
        return tailSet.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 8)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
