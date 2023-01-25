import java.util.LinkedList
import java.util.TreeMap

class VirtualIntSet {
    private val intRangeList: MutableList<IntRange> = LinkedList()

    fun add(ir: IntRange) {
        if (intRangeList.isEmpty()) {
            intRangeList.add(ir)
            return
        }

        val range = intRangeList.first()
        while ()
    }

    override fun toString(): String {
        return "$intRangeList"
    }
}

fun main() {
    val a = VirtualIntSet()
    a.add(2..5)
    println("a = $a")
}
/*
possibilidades de inclusão (incluindo 4..7):
vazio -> [4..7]
[1..2] -> [1..2][4..7]
[10..12] -> [4..7][10..12]

 */