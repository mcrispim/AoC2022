fun main() {

    fun mountStacks(input: List<String>): MutableList<MutableList<Char>> {
        val lines = mutableListOf<String>()
        var i = 0
        while (true) {
            val line = input[i]
            if (line.isEmpty()) break
            lines.add(line)
            i++
        }
        val nStacks = lines.removeLast().split(Regex("\\s+")).last().toInt()
        val stacks = MutableList<MutableList<Char>>(nStacks) { mutableListOf() }
        for (line in lines.reversed()) {
            for (n in 0 until nStacks) {
                val j = 1 + 4 * n
                if (j <= line.length && line[j] != ' ') stacks[n].add(line[j])
            }
        }
        return stacks
    }

    fun getMoves(input: List<String>): List<Triple<Int, Int, Int>> {
        var i = 0
        while (input[i++].isNotEmpty()) {}

        val moves = mutableListOf<Triple<Int, Int, Int>>()
        while (i <= input.lastIndex) {
            val numbers = input[i++].split("move ", " from ", " to ").drop(1).map { it.toInt() }
            moves.add(Triple(numbers[0], numbers[1], numbers[2]))
        }
        return moves
    }

    fun doMovesCrateMover9000(stacks: List<MutableList<Char>>, moves: List<Triple<Int, Int, Int>>) {
        for (move in moves) {
            val (boxes, from, to) = move
            repeat(boxes) {
                stacks[to - 1].add(stacks[from - 1].removeLast())
            }
        }
    }

    fun doMovesCrateMover9001(stacks: MutableList<MutableList<Char>>, moves: List<Triple<Int, Int, Int>>) {
        for (move in moves) {
            val (boxes, from, to) = move
            for(i in stacks[from - 1].size - boxes until stacks[from - 1].size) {
                stacks[to - 1].add(stacks[from - 1][i])
            }
            stacks[from - 1] = stacks[from - 1].dropLast(boxes).toMutableList()
        }
    }

    fun stacks2String(stacks: List<MutableList<Char>>): String = stacks.map { it.last() }.joinToString("")

    fun part1(input: List<String>): String {
        val stacks = mountStacks(input)
        val moves = getMoves(input)
        doMovesCrateMover9000(stacks, moves)
        return stacks2String(stacks)
    }

    fun part2(input: List<String>): String {
        val stacks = mountStacks(input)
        val moves = getMoves(input)
        doMovesCrateMover9001(stacks, moves)
        return stacks2String(stacks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
