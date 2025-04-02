// Single Line Comment
import kotlin.math.*
import kotlin.random.Random
fun main() { // another single line comment
    println("Hello Kotlin!")
    val num1 = 2
    val num2 = 6
    val string1 = "Hello"
    val string2 = "Everyone"
    val string3 = "2"
    val string4 = "6"
    println(num1 + num2)
    println(num1 * num2)
    println("$string1, $string2")
    println(string3 + string4)
    val num3 = 10.2
    val num4 = 8.5
    println(num3 * num4)
    println("result = %.2f".format(num3 * num4))
    println(String.format("%.2f", num3 * num4))
    println("%.2f".format(num3 * num4))

    println("%.0f".format(28.6 % 10.0))

    val number: Int = 10
    println(number)
    // number is a constant and can NOT be changed: number = 5

    val name: String = "John Doe"
    println(name)
    // a constant can NOT be changed: name = "Jane Doe"

    // ++ and -- do not exist in Kotlin: println(num1++)
    // ++ and -- do not exist in Kotlin: println(num1--)

    // must use
    val num5 = num1 + 1
    println(num5)

    var integer: Int = 100
    var decimal: Double = 12.5
    integer = decimal.toInt()
    println(integer)

    val hourlyRate: Double = 19.5
    val hoursWorked: Int = 10
    val totalCost: Double = hourlyRate * hoursWorked.toDouble()
    println(totalCost)

    val x1 = 2.3
    val y1 = 5.7

    val x2 = 22.6
    val y2 = 17.9

    val distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1))
    println(String.format("%.2f", distance))

    val doesOneEqualTwo = (1 == 2)
    println(doesOneEqualTwo)

    val temperature = 13
    val isRaining = true
    if (temperature < 10)
    {
        println("It's cold outside.")
    }
    else
    {
        println("It's not cold outside.")
    }

    if(temperature < 5 && isRaining == true)
    {
        println("Wear your a waterproof winter coat.")
    }
    else if(temperature < 5 && isRaining == false)
    {
        println("Wear your a winter coat.")
    }
    else if(temperature < 10)
    {
        println("Wear a good coat.")
    }
    else if(temperature < 15)
    {
        println("Wear a jacket.")
    }
    else if(temperature < 20)
    {
        println("Wear a light jacket.")
    }
    else
    {
        println("WhooHoo, no need for a cote or jacket.")
    }

    var sum = 1

    while (sum < 1000) {
        sum = sum + (sum + 1)
        println(sum)
    }

    var dirtyDishes = Random.nextInt(0, 6)
    println(dirtyDishes)

    while(dirtyDishes != 0)
    {
        println("Washing a dish")
        dirtyDishes -= 1
    }

    println("There are no dirty dishes in the sink.")

    var glassSize = 250

    var currentLevel = 0

    do {
        println("Adding water")
        currentLevel += 5
        println("Current level: " + currentLevel)
    } while(currentLevel < glassSize - 10)

    println("Here is your full glass of water.")

    val students = arrayOf("Chris", "Carlito", "Din", "Hina", "Jasvir", "Ryan", "Sameera")

    println("Here is a list of the seven students in the class")

    for(i in 0..6)
    {
        println(students[i])
    }

    var celsius = 10.0

    var fahrenheit = convertCelsiusToFahrenheit(celsius) // function call

    println("The temperature in fahrenheit is " + String.format("%.0f", fahrenheit))




}
// Function Definition
fun convertCelsiusToFahrenheit(celsius: Double): Double {
    var fahrenheit = celsius * 9 / 5 + 32
    return fahrenheit
}