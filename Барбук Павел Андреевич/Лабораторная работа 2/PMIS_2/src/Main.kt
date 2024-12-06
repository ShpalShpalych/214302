/*Напишите программу, которая создает магический квадрат
 заданного размера (матрица, где суммы чисел в каждой строке, столбце и диагонали равны).
 Программа должна проверять, что введенное число является допустимым для
 создания магического квадрата.*/

fun main() {
    println("Введите размер магического квадрата:")
    val n = readLine()?.toIntOrNull()

    if (n == null || n <= 0) {
        println("Некорректный ввод. Введите положительное число.")
        return
    }

    val magicSquare = when {
        n % 2 == 1 -> generateOddMagicSquare(n)
        n % 4 == 0 -> generateDoublyEvenMagicSquare(n)
        else -> generateSinglyEvenMagicSquare(n)
    }

    println("Магический квадрат размером $n:")
    for (row in magicSquare) {
        println(row.joinToString(" "))
    }
    isMagicSquare(magicSquare)
}

fun generateOddMagicSquare(n: Int): Array<IntArray> {
    val magicSquare = Array(n) { IntArray(n) }

    var number = 1
    var i = 0
    var j = n / 2 

    while (number <= n * n) {
        magicSquare[i][j] = number
        number++

        val newi = (i - 1 + n) % n
        val newj = (j + 1) % n

        if (magicSquare[newi][newj] != 0) {
            i = (i + 1) % n
        } else {
            i = newi
            j = newj
        }
    }

    return magicSquare
}

fun generateDoublyEvenMagicSquare(n: Int): Array<IntArray> {
    val magicSquare = Array(n) { IntArray(n) }

    for (i in 0 until n) {
        for (j in 0 until n) {
            val cellNumber = i * n + j + 1
            magicSquare[i][j] = if (i % 4 == j % 4 || (i % 4 + j % 4) == 3) n * n + 1 - cellNumber else cellNumber
        }
    }

    return magicSquare
}

fun generateSinglyEvenMagicSquare(n: Int): Array<IntArray> {
    val halfN = n / 2
    val subSquareSize = halfN * halfN
    val subSquare = generateOddMagicSquare(halfN)

    val magicSquare = Array(n) { IntArray(n) }

    for (i in 0 until halfN) {
        for (j in 0 until halfN) {
            magicSquare[i][j] = subSquare[i][j]
            magicSquare[i + halfN][j + halfN] = subSquare[i][j] + subSquareSize
            magicSquare[i][j + halfN] = subSquare[i][j] + 2 * subSquareSize
            magicSquare[i + halfN][j] = subSquare[i][j] + 3 * subSquareSize
        }
    }

    val shift = halfN / 2
    for (i in 0 until halfN) {
        for (j in 0 until shift) {
            if (i == shift) {
                if (j == 0) {
                    swap(magicSquare, i, j, i + halfN, j)
                } else {
                    swap(magicSquare, i, n - j - 1, i + halfN, n - j - 1)
                }
            } else {
                swap(magicSquare, i, j, i + halfN, j)
            }
        }
    }

    return magicSquare
}

fun swap(magicSquare: Array<IntArray>, i1: Int, j1: Int, i2: Int, j2: Int) {
    val temp = magicSquare[i1][j1]
    magicSquare[i1][j1] = magicSquare[i2][j2]
    magicSquare[i2][j2] = temp
}

fun isMagicSquare(square: Array<IntArray>): Boolean {
    val n = square.size
    val targetSum = (n * (n * n + 1)) / 2

    for (i in 0 until n) {
        val rowSum = square[i].sum()
        if (rowSum != targetSum) {
            println("Матрица не является магическим квадратом. Строка $i не совпадает с целевым значением.")
            return false
        }
    }

    for (j in 0 until n) {
        var colSum = 0
        for (i in 0 until n) {
            colSum += square[i][j]
        }
        if (colSum != targetSum) {
            println("Матрица не является магическим квадратом. Столбец $j не совпадает с целевым значением.")
            return false
        }
    }

    var mainDiagonalSum = 0
    for (i in 0 until n) {
        mainDiagonalSum += square[i][i]
    }
    if (mainDiagonalSum != targetSum) {
        println("Матрица не является магическим квадратом. Главная диагональ не совпадает с целевым значением.")
        return false
    }

    var secondaryDiagonalSum = 0
    for (i in 0 until n) {
        secondaryDiagonalSum += square[i][n - i - 1]
    }
    if (secondaryDiagonalSum != targetSum) {
        println("Матрица не является магическим квадратом. Побочная диагональ не совпадает с целевым значением.")
        return false
    }

    println("Матрица является магическим квадратом.")
    return true
}
