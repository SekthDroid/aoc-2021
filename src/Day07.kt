import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").mapToInt()

        return (0..positions.maxOrNull()!!)
            .map {
                positions.map { each -> min(it, each) - max(it, each) }.sumOf(::abs)
            }
            .minOf { it }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 168) {
        "Failed to resolve part2"
    }

    val input = readInput("Day07")
    println(part1(input))
    // println(part2(input))
}