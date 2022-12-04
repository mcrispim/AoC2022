fun main() {

    fun Set<Int>.contains(s: Set<Int>): Boolean = this.intersect(s) == s

    fun part1(input: List<String>): Int {
        return input.count { line ->
            val limits = line.split("-", ",").map { it.toInt() }
            val (aStart, aEnd, bStart, bEnd) = limits
            val s1 = (aStart..aEnd).toSet()
            val s2 = (bStart..bEnd).toSet()
            s1.contains(s2) || s2.contains(s1)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val limits = line.split("-", ",").map { it.toInt() }
            val (aStart, aEnd, bStart, bEnd) = limits
            val s1 = (aStart..aEnd).toSet()
            val s2 = (bStart..bEnd).toSet()
            s1.subtract(s2) != s1
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
