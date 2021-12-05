import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Convert all elements to Int
 */
fun List<String>.mapToInt() = map { it.trim().toInt() }

/**
 * Creates a progression from start to end
 */
fun progression(start: Int, end: Int): IntProgression {
    val step = if (start < end) 1 else -1
    return IntProgression.fromClosedRange(start, end, step)
}

/**
 * Generates a path from this to other
 * (1 to 1).pathTo(1,3) will generate [(1, 1), (1, 2), (1, 3)]
 */
fun Pair<Int, Int>.pathTo(other: Pair<Int, Int>): List<Pair<Int, Int>> {
    val xFrom = first
    val yFrom = second
    val xTo = other.first
    val yTo = other.second

    return when {
        xFrom != xTo && yFrom != yTo -> {
            // Diagonal
            val xProgression = progression(xFrom, xTo).toList()
            val yProgression = progression(yFrom, yTo).toList()

            return (xProgression.indices).map {
                Pair(xProgression[it], yProgression[it])
            }

        }
        xFrom != xTo -> {
            // Horizontal
            progression(xFrom, xTo).map { Pair(it, yFrom) }
        }
        else -> {
            progression(yFrom, yTo).map { Pair(xFrom, it) }
        }
    }
}