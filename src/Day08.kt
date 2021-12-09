import java.util.*

fun main() {
    val digitTable = listOf(
        0 to "cagedb",
        1 to "ab",
        2 to "gcdfa",
        3 to "fbcad",
        4 to "eafb",
        5 to "cdfbe",
        6 to "cdfgeb",
        7 to "dab",
        8 to "acedgfb",
        9 to "cefabd"
    )

    fun part1(input: List<String>): Int {
        val entries = input.map { it.split(" | ").last() }

        var count = 0

        val targetDigits = digitTable.filter { it.first in listOf(1, 4, 7, 8) }
        for (entry in entries) {
            val entryValues = entry.split(" ")
            for (value in entryValues) {
                if (targetDigits.any { it.second.length == value.length }) {
                    count++
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.split(" | ") }
            .map { it.first() to it.last() }
            .map {
                generateDictionary(it.first.split(" ")) to it.second.split(" ")
            }
            .map {
                it.first.translate(it.second)
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 61229) {
        "Failed to resolve part2"
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun List<Pair<Int, String>>.translate(output: List<String>): Int {
    return output.mapNotNull { each -> find { each.matchesContent(it.second) } }
        .map { it.first }
        .joinToString("")
        .toInt()
}

private fun generateDictionary(dictionary: List<String>): List<Pair<Int, String>> {
    var base = listOf(
        1 to dictionary.find { it.length == 2 }!!,
        4 to dictionary.find { it.length == 4 }!!,
        7 to dictionary.find { it.length == 3 }!!,
        8 to dictionary.find { it.length == 7 }!!
    )

    base = find6(dictionary, base)
    base = find0(dictionary, base)
    base = find9(dictionary, base)
    base = find2(dictionary, base)
    base = find5(dictionary, base)
    base = find3(dictionary, base)

    return base
}

fun find6(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    // Could be 0, 6 or 9
    val associate = currentDictionary.find { it.first == 1 }!!.second.toCharArray()

    for (i in candidates.filter { it.length == 6 }) {
        val split = i.toCharArray()
        val nonExistent = associate.filter { it !in split }
        if (nonExistent.isNotEmpty()) {
            return currentDictionary.plus(6 to i)
        }
    }
    return currentDictionary
}

fun find0(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    val associate = currentDictionary.find { it.first == 4 }!!.second.toCharArray()
    val candidate = candidates.filter { it.length == 6 }
    for (i in candidate) {
        if (i in currentDictionary.map { it.second }) continue

        val remainder = associate.filter { it !in i.toCharArray() }
        if (remainder.isNotEmpty()) {
            // println(remainder)
            return currentDictionary.plus(0 to i)
        }
    }
    return currentDictionary
}

fun find9(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    val sameLengthItems = currentDictionary.filter { it.second.length == 6 }.map { it.second }
    return currentDictionary.plus(9 to candidates.find { it.length == 6 && it !in sameLengthItems }!!)
}

fun find2(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    val candidate = candidates.filter { it.length == 5 }
    val equality = currentDictionary.find { it.first == 4 }?.second.orEmpty().toCharArray()
    val equals = currentDictionary.find { it.first == 8 }!!.second

    for (i in candidate) {
        val reminder = i.toCharArray().plus(equality).toSet()
        if (reminder.sorted() == equals.toCharArray().sorted()) {
            return currentDictionary.plus(2 to i)
        }
    }
    return currentDictionary
}

fun find5(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    val candidate = candidates.filter { it.length == 5 }
    val plusItem = currentDictionary.find { it.first == 7 }?.second.orEmpty().toSet().sorted()
    val equals = currentDictionary.find { it.first == 9 }?.second.orEmpty().toSet().sorted()

    for (i in candidate) {
        val merged = i.toSet().plus(plusItem).sorted()
        if (merged == equals) {
            return currentDictionary.plus(5 to i)
        }
    }

    return currentDictionary
}

fun find3(candidates: List<String>, currentDictionary: List<Pair<Int, String>>): List<Pair<Int, String>> {
    val candidate = candidates.filter { it.length == 5 }
    val plusItem = currentDictionary.find { it.first == 5 }?.second.orEmpty().toSet().sorted()
    val equals = currentDictionary.find { it.first == 9 }?.second.orEmpty().toSet().sorted()

    for (i in candidate) {
        val merged = i.toSet().plus(plusItem).sorted()
        if (merged == equals) {
            return currentDictionary.plus(3 to i)
        }
    }

    return currentDictionary
}

private fun String.matchesContent(another: String) = toCharArray().sorted() == another.toCharArray().sorted()