fun main() {


    fun part1(input: List<String>): Int {
        var nextCommand = 0
        val measures = listOf(20, 60, 100, 140, 180, 220)
        val strength = mutableListOf<Int>()
        var nextMeasure = 0
        var cycle = 1
        var registerX = 1
        while (nextCommand <= input.lastIndex) {
            val initialRegisterX = registerX
            when (val command = input[nextCommand]) {
                "noop" -> {
                    cycle++
                }
                else -> {
                    cycle += 2
                    registerX += command.substringAfter(" ").toInt()
                }
            }
            nextCommand++
            if (cycle > measures[nextMeasure]) {
                strength.add(measures[nextMeasure] * initialRegisterX)
            } else if (cycle == measures[nextMeasure]) {
                strength.add(measures[nextMeasure] * registerX)
            }
            if (cycle >= measures[nextMeasure]) {
                nextMeasure++
            }
            if (strength.size == measures.size)
                break
        }
        return strength.sum()
    }

    fun part2(input: List<String>): Int {
        var registerX = 1
        var instruction = 0
        var duration = 0
        var increment = 0
        for (line in 0..5) {
            for (pixel in 0..39) {
                if (duration == 0) {
                    val command = input[instruction++]
                    if (command == "noop") {
                        duration = 1
                        increment = 0
                    } else {
                        duration = 2
                        increment = command.substringAfter(" ").toInt()
                    }
                }
                duration--
                val sprite = registerX - 1.. registerX + 1
                print(if (pixel in sprite) "#" else ".")
                if (duration == 0) {
                    registerX += increment
                }
            }
            println()
        }
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(part2(testInput) == 146)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
