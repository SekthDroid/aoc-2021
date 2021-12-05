import java.util.function.BooleanSupplier

val RegexNumber = Regex("\\d{1,2}")
const val InputSeparator = ","
const val CardSeparator = " "

fun main() {
    fun part1(input: List<String>): Int {

        val bingoGame = read(input)
        return bingoGame.firstWinner()
    }

    fun part2(input: List<String>): Int {
        return read(input).lastWinner()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 1924) {
        "Failed to resolve part2"
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun read(input: List<String>): BingoGame {
    var bingoInput = listOf<String>()
    var cardInput = listOf<String>()

    for (i in input) {
        when {
            InputSeparator in i -> bingoInput = bingoInput + RegexNumber.findAll(i).map { it.groupValues }.flatten()
            CardSeparator in i -> cardInput = cardInput + RegexNumber.findAll(i).map { it.groupValues }.flatten()
        }
    }

    return BingoGame(
        bingoInput,
        cardInput.chunked(25).map { BingoCard(it) }
    )
}

class BingoGame(private val inputNumbers: List<String>, private val cards: List<BingoCard>) {
    fun firstWinner(): Int {
        return game().first().score()
    }

    fun lastWinner(): Int {
        return game().last().score()
    }

    private fun game(): List<BingoCard> {
        val winners = mutableListOf<BingoCard>()

        for (i in inputNumbers) {
            for (card in cards.filterNot { it in winners }) {
                if (card.mark(i)) {
                    winners.add(card)
                }
            }
        }
        return winners
    }
}

class BingoCard(private val numbers: List<String>) {
    private val matrix = numbers.chunked(5)
    private val markedValues = mutableSetOf<String>()

    fun mark(value: String): Boolean {
        if (value in numbers) {
            markedValues.add(value)
        }

        return isBingo(markedValues)
    }

    fun score(): Int {
        return numbers.filterNot { it in markedValues }
            .map(String::toInt)
            .sum() * (markedValues.lastOrNull()?.toInt() ?: 0)
    }

    private fun isBingo(marked: Set<String>): Boolean {
        // Lets check rows
        for (i in matrix) {
            if (i.matches(marked)) return true
        }

        // Lets check columns
        for (i in 0 until 5) {
            if (matrix.map { it[i] }.matches(marked)) return true
        }

        return false
    }

    private fun List<String>.matches(marked: Set<String>): Boolean {
        return this.intersect(marked).size == this.size
    }
}