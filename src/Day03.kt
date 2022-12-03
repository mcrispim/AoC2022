fun main() {

    fun priority(item: Char): Int {
        return when (item) {
            in 'a'..'z' -> item - 'a' + 1
            in 'A'..'Z' -> item - 'A' + 27
            else -> 0
        }
    }

    fun part1(input: List<String>): Int = input.sumOf {
        val partSize = it.length / 2
        val compartment1 = it.substring(0 until partSize).toSet()
        val compartment2 = it.substring(partSize until it.length).toSet()
        val inBothList = compartment1.intersect(compartment2).toList()
        priority(inBothList.first()) // because we now that there is only one item
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for(i in input.indices step 3) {
            val elf1 = input[i + 0].toSet()
            val elf2 = input[i + 1].toSet()
            val elf3 = input[i + 2].toSet()
            sum += priority(elf1.intersect(elf2).intersect(elf3).toList().first())
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
