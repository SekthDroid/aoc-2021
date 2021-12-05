fun main() {
    fun part1(input: List<String>): Int {
        val vents = input.map { readVent(it) }
            .filterNot { it.isDiagonal() }

        return vents.map { it.trace() }
            .flatten()
            .groupBy { it }
            .count { it.value.size > 1 }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5) {
        "Failed to resolve part1"
    }

    /*check(part2(testInput) == 5) {
        "Failed to resolve part2"
    }*/

    val input = readInput("Day05")
    println(part1(input))
    // println(part2(input))
}

fun Pair<Int, Int>.pathTo(other: Pair<Int, Int>): List<Pair<Int, Int>> {
    val xFrom = first
    val yFrom = second
    val xTo = other.first
    val yTo = other.second

    val isVertical = xFrom != xTo

    return if (isVertical) {
        progression(xFrom, xTo).map { Pair(it, yFrom) }
    } else {
        progression(yFrom, yTo).map { Pair(xFrom, it) }
    }
}

fun progression(start: Int, end: Int): IntProgression {
    val step = if (start < end) 1 else -1
    return IntProgression.fromClosedRange(start, end, step)
}

data class VentDirection(val start: Pair<Int, Int>, val end: Pair<Int, Int>) {

    fun isDiagonal(): Boolean {
        return start.first != end.first && start.second != end.second
    }

    fun trace(): List<Pair<Int, Int>> {
        return start.pathTo(end)
    }
}

private fun readVent(input: String): VentDirection {
    return input.split(" -> ")
        .map {
            it.split(",").mapToInt().run { first() to last() }
        }
        .run {
            VentDirection(first(), last())
        }
}