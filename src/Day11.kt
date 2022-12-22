fun main() {

    class Monkey(
        val items: MutableList<Long>,
        val op: String,
        val paramStr: String,
        val testDivisor: Int,
        val testTrue: Int,
        val testFalse: Int
    ) {
        var inspectionCounter = 0
    }

    fun getInput(input: List<String>): List<Monkey> {
        val monkeyList = mutableListOf<Monkey>()
        var lineCounter = 0
        while (lineCounter < input.size) {
            val firstLine = input[lineCounter++] // Monkey number
            if (firstLine.isBlank()) continue
            val items = input[lineCounter++]
                .substringAfter(":")
                .split(",")
                .map { it.trim { c -> c == ' ' }.toLong() }
                .toMutableList()
            val operation = input[lineCounter++].substringAfter("old ").split(" ")
            val op = operation.first()
            val paramStr = operation.last()
            val testDivisor = input[lineCounter++].substringAfter("by ").toInt()
            val testTrue = input[lineCounter++].substringAfter("monkey ").toInt()
            val testfalse = input[lineCounter++].substringAfter("monkey ").toInt()
            monkeyList.add(Monkey(items, op, paramStr, testDivisor, testTrue, testfalse))
        }
        return monkeyList
    }

    fun processMonkey(monkeyIndex: Int, monkeyList: List<Monkey>, withRelief: Boolean) {
        val monkey = monkeyList[monkeyIndex]
        while (monkey.items.isNotEmpty()) {
            var item = monkey.items.removeAt(0)
            val par = if (monkey.paramStr == "old") item else monkey.paramStr.toLong()
            item = if (monkey.op == "+") item + par else item * par
            if (withRelief)
                item /= 3
            if (item % monkey.testDivisor == 0L)
                monkeyList[monkey.testTrue].items.add(item)
            else
                monkeyList[monkey.testFalse].items.add(item)
            monkey.inspectionCounter++
        }
    }

    fun part1(input: List<String>): Long {
        val monkeyList = getInput(input)
        for (round in 1..20) {
            for (i in monkeyList.indices) {
                processMonkey(i, monkeyList, withRelief = true)
            }
        }
        val (counter1, counter2) = monkeyList.map { it.inspectionCounter }.sortedDescending().take(2)
        return (counter1 * counter2).toLong()
    }

    fun part2(input: List<String>): Long {
        val monkeyList = getInput(input)
        for (round in 1..20) {
            for (i in monkeyList.indices) {
                processMonkey(i, monkeyList, withRelief = false)
            }
        }
        val countersList = monkeyList.map { it.inspectionCounter }
        val (counter1, counter2) = countersList.sortedDescending().take(2)
        return (counter1 * counter2).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
