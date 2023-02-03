import java.lang.Integer.max
import java.lang.Integer.min

class VirtualIntSet {
    private var head: Node? = null
    private var tail: Node? = null

    private inner class Node constructor(var element: IntRange, var previous: Node?, var next: Node?)

    private fun together(node1: Node, node2: Node) = node1.element.last + 1 == node2.element.first

    fun add(range: IntRange) {

        var inserted: Node

        if (head == null) {
            val node = Node(element = range, previous = null, next = null)
            head = node
            tail = node
            inserted = node
        } else if (range.first <= head!!.element.first) {
            val node = Node(element = range, previous = null, next = head)
            head = node
            node.next?.previous = head
            inserted = node
        } else {
            var current = head
            while (current != null && range.first > current.element.first) {
                current = current.next
            }
            if (current != null) {
                val node = Node(range, previous = current.previous, next = current)
                current.previous!!.next = node
                current.previous = node
                inserted = node
            } else {
                val node = Node(range, previous = tail, next = null)
                tail = node
                node.previous!!.next = node
                inserted = node
            }
        }
        merge(inserted)
    }

    private fun merge(inserted: Node) {
        var newNode = inserted
        if (head != newNode) {
            val previous = newNode.previous
            if (previous!!.element.first == newNode.element.first || previous.element.last >= newNode.element.first - 1) {
                newNode = join(previous, newNode)
                if (newNode.previous == null)
                    head = newNode
                else
                    newNode.previous!!.next = newNode
                if (newNode.next == null)
                    tail = newNode
                else
                    newNode.next!!.previous = newNode
            }
        }

        if (tail != newNode) {
            val next = newNode.next
            if (newNode.element.first == next!!.element.first || newNode.element.last == next.element.first - 1) {
                newNode = join(newNode, next)
                if (newNode.previous == null)
                    head = newNode
                else
                    newNode.previous!!.next = newNode
                if (newNode.next == null)
                    tail = newNode
                else
                    newNode.next!!.previous = newNode

            }
        }

    }

    private fun join(node1: Node, node2: Node): Node {
        val first = min(node1.element.first, node2.element.first)
        val last = max(node1.element.last, node2.element.last)
        return Node(element = first..last, previous = node1.previous, next = node2.next)
    }

    override fun toString(): String {
        var result = "["
        var current = head
        while (current != null) {
            result += "[${current.element}]"
            current = current.next
        }
        return "$result]"
    }
}

fun main() {
    val a = VirtualIntSet()
    println("a = $a")
    a.add(2..5)
    println("a = $a")
    a.add(2..4)
    println("a = $a")
    a.add(0..1)
    println("a = $a")
}
/*
possibilidades de inclusão (incluindo 4..7):
vazio -> [4..7]
[1..2] -> [1..2][4..7]
[10..12] -> [4..7][10..12]

 */