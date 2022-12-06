fun main() {

    fun part1(input: List<String>): Int {
        val startOfPacketSize = 4
        val line = input.first()
        for (i in 0..line.length - startOfPacketSize) {
            val fourChars = line.slice(i until i + startOfPacketSize).toSet()
            if (fourChars.size == startOfPacketSize) return i + startOfPacketSize
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val startOfMessageSize = 14
        val line = input.first()
        for (i in 0..line.length - startOfMessageSize) {
            val fourChars = line.slice(i until i + startOfMessageSize).toSet()
            if (fourChars.size == startOfMessageSize) return i + startOfMessageSize
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
