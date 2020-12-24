package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") : String{
    val dateFormat = SimpleDateFormat (pattern, Locale("ru"))
    return  dateFormat.format(this)
}
fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}


fun Date.humanizeDiff(date :Date = Date()): String {
    val diff  = ((date.time - this.time) / 1000).toInt()
    if (diff > -1){
        return when(diff){
            in 0..1 -> return "только что"
            in 2..45 -> return "несколько секунд назад"
            in 46..75 -> return "минуту назад"
            in 76..2700 -> return "${diff/60} ${pluralForm(diff/60,"минуту", "минуты", "минут")} назад"
            in 2701..4500 -> return "час назад"
            in 4501..79200 -> return "${diff/3600} ${pluralForm(diff/3600,"час", "часа", "часов")} назад"
            in 79201..93600 -> return "день назад"
            in 93601..31104000 -> return "${diff/86400} ${pluralForm(diff/86400,"день", "дня", "дней")} назад"
            else -> "более года назад"
        }
    } else

        return when(kotlin.math.abs(diff)){
            1 -> return "только что"
            in 2..45 -> return "через несколько секунд"
            in 46..75 -> return "через минуту"
            in 76..2700 -> return "через ${kotlin.math.abs(diff/60)} ${pluralForm(diff/60,"минуту", "минуты", "минут")}"
            in 2701..4500 -> return "через час"
            in 4501..79200 -> return "через ${kotlin.math.abs(diff/3600)} ${pluralForm(diff/3600,"час", "часа", "часов")}"
            in 79201..93600 -> return "через день"
            in 93601..31104000 -> return "через ${kotlin.math.abs(diff/86400)} ${pluralForm(diff/86400,"день", "дня", "дней")}"
            else -> "более чем через год"
        }
}

//0с - 1с "только что"
//1с - 45с "несколько секунд назад"
//45с - 75с "минуту назад"
//75с - 45мин "N минут назад"
//45мин - 75мин "час назад"
//75мин 22ч "N часов назад"
//22ч - 26ч "день назад"
//26ч - 360д "N дней назад"
//>360д "более года назад"

fun pluralForm(value: Int , first_form : String, second_form : String, third_form : String) : String {
    return if(kotlin.math.abs(value % 100) in 10..20) third_form
    else
        when(kotlin.math.abs(value % 10)){
            1 -> first_form
            in 2..4 -> second_form
            in 5..9, 0 -> third_form
        else -> "Передайте корректное число"
        }
}



enum class TimeUnits{
    SECOND, // "секунду", "секунды", "секунд"
    MINUTE, // "минуту", "минуты", "минут"
    HOUR, //   "час", "часа", "часов"
    DAY; //     "день", "дня", "дней"

fun plural (value : Int) : String{
    return when(this){
        SECOND -> "$value ${pluralForm(value, "секунду", "секунды", "секунд")}"
        MINUTE -> "$value ${pluralForm(value, "минуту", "минуты", "минут")}"
        HOUR -> "$value ${pluralForm(value, "час", "часа", "часов")}"
        DAY -> "$value ${pluralForm(value, "день", "дня", "дней")}"
        }
    }
}

