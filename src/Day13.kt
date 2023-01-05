fun isInt(s: String): Boolean {
    return try {
        s.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun isList(s: String) = s.startsWith('[') && s.endsWith(']')

fun unlist(list: String): MutableList<String> {
    val elements = mutableListOf<String>()
    var level = 0
    var start = 0
    var index = 1
    while (index <= list.lastIndex) {
        when (list[index]) {
            '[' -> {
                if (level == 0)
                    start = index
                level++
            }
            ']' -> {
                level--
                if (level == 0)
                    elements.add(list.slice(start..index))
            }
            in '0'..'9' -> {
                if (level == 0) {
                    start = index++
                    while (list[index] in '0'..'9')
                        index++
                    index--
                    elements.add(list.slice((start..index)))
                }
            }
        }
        index++
    }
    return elements
}

fun intsInOrder(left: String, right: String): Boolean? {
    val l = left.toInt()
    val r = right.toInt()
    if (l < r) return true
    if (l > r) return false
    return null
}

fun listsInOrder(left: String, right: String, level: Int): Boolean? {
    val rawLeft = unlist(left)
    val rawRight = unlist(right)
    while (rawLeft.isNotEmpty() && rawRight.isNotEmpty()) {
        val leftElement = rawLeft.removeAt(0)
        val rightElement = rawRight.removeAt(0)
        return areInOrder(leftElement, rightElement, level + 1) ?: continue
    }
    if (rawLeft.isEmpty() && rawRight.isEmpty())
        return null
    if (rawLeft.isEmpty())
        return true
    return false
}

fun areInOrder(left: String, right: String, level: Int): Boolean? {
    if (isList(left) && isList(right)) {
        val inOrder = listsInOrder(left, right, level)
        if (level == 0 && inOrder == null)
            return true
        return inOrder
    }
    if (isInt(left) && isInt(right)) {
        val inOrder = intsInOrder(left, right)
        if (level == 0 && inOrder == null)
            return true
        return inOrder
    }
    return if (isInt(left))
        areInOrder("[$left]", right, level + 1)
    else
        areInOrder(left, "[$right]", level + 1)
}

fun main() {

    fun part1(input: List<String>): Int {
        var line = 0
        var index = 0
        var indexSum = 0
        while (line < input.size) {
            val left = input[line++]
            val right = input[line++]
            line++
            index++
            val inOrder = areInOrder(left, right, level = 0)
            if (inOrder!!)
                indexSum += index
        }
        return indexSum
    }

    fun part2(input: List<String>): Int {
        val packets: MutableList<String> = input.filter { it.isNotEmpty() }.toMutableList()
        packets.add("[[2]]")
        packets.add("[[6]]")

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test2")
    check(part1(testInput) == 13)
    check(part2(testInput) == 23)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
