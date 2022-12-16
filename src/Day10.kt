fun main() {


    fun part1(input: List<String>): Int {
        var nextCommand = 0
        val measures = listOf(20, 60, 100, 140, 180, 220)
        val strength = mutableListOf<Int>()
        var nextMeasure = 0
        var cycle = 0
        var signal = 1
        while (nextCommand <= input.lastIndex) {
            val lastSignal = signal
            when (val command = input[nextCommand]) {
                "noop" -> {
                    cycle++
                }
                else -> {
                    cycle += 2
                    signal += command.substringAfter(" ").toInt()
                }
            }
            nextCommand++
            if (cycle > measures[nextMeasure]) {
                strength.add(measures[nextMeasure++] * lastSignal)
            } else if (cycle == measures[nextMeasure]) {
                strength.add(measures[nextMeasure++] * signal)
            }
            if (strength.size == measures.size)
                break
        }
        return strength.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 146)
    check(part2(testInput) == 146)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
