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

        fun processItems(monkeyList: List<Monkey>, relief: (Long) -> Long) {
            while (items.isNotEmpty()) {
                var item = items.removeAt(0)
                val par = if (paramStr == "old") item else paramStr.toLong()
                item = if (op == "+") item + par else item * par
                item = relief(item)
                if (item % testDivisor == 0L) {
                    monkeyList[testTrue].items.add(item)
                } else {
                    monkeyList[testFalse].items.add(item)
                }
                inspectionCounter++
            }
        }
    }

    fun getCommonDivisor(monkeyList: List<Monkey>): Long {
        var result = 1L
        for (monkey in monkeyList) {
            result *= monkey.testDivisor
        }
        return result
    }

    fun getInput(input: List<String>): List<Monkey> {
        val monkeyList = mutableListOf<Monkey>()
        var lineCounter = 0
        while (lineCounter < input.size) {
            val firstLine = input[lineCounter++] // Monkey number
            if (firstLine.isBlank()) continue
            val items = input[lineCounter++].substringAfter(":").split(",").map { it.trim { c -> c == ' ' }.toLong() }
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

    fun part1(input: List<String>): Long {
        val monkeyList = getInput(input)
        for (round in 1..20) {
            for (monkey in monkeyList) {
                monkey.processItems(monkeyList) { it / 3 }
            }
        }
        val (counter1, counter2) = monkeyList.map { it.inspectionCounter }.sortedDescending().take(2)
        return (counter1 * counter2).toLong()
    }

    fun part2(input: List<String>): Long {
        val monkeyList = getInput(input)
        val cd = getCommonDivisor(monkeyList)
        for (round in 1..10000) {
            for (monkey in monkeyList) {
                monkey.processItems(monkeyList) { (it % cd) + cd }
            }
        }
        val countersList = monkeyList.map { it.inspectionCounter.toLong() }
        val (counter1, counter2) = countersList.sortedDescending().take(2)
        return (counter1 * counter2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10_605L)
    check(part2(testInput) == 2_713_310_158L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
