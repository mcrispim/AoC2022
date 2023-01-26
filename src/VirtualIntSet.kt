class VirtualIntSet {
    var size = 0
    private var head: Node? = null

    private inner class Node constructor(var element: IntRange, var next: Node?)

    fun add(range: IntRange) {
        if (range == null) {
            head = Node(range, null)
            size++
            return
        } else {
            val range = head
            if (range.element.last <)
                while (range.)
        }
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