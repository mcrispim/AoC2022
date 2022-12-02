fun main() {
    var elvesCalories: List<Int>

    fun calculateElvesCalories(input: List<String>): List<Int> {
        val calories = mutableListOf<Int>()
        var item = 0
        var sum = 0
        while(item < input.size) {
            if (input[item] == "") {
                calories.add(sum)
                sum = 0
            } else {
                sum += input[item].toInt()
            }
            item++
        }
        calories.add(sum)
        return calories
    }

    fun part1(input: List<String>): Int {
        elvesCalories = calculateElvesCalories(input)
        return elvesCalories.max()
    }

    fun part2(input: List<String>): Int {
        elvesCalories = calculateElvesCalories(input)
        return elvesCalories.sorted().reversed().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
