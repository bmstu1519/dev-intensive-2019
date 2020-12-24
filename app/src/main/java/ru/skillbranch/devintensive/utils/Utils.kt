package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName : String?) : Pair<String?, String?> {
        //TODO FIX ME
        when(fullName){
            null, "", " " -> return null to null
        }
        val parts : List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return  firstName to lastName
    }

    fun transliteration(payload: String, divider : String = " "): String {
        val  result = payload.replace(Regex("[а-яА-я ]")) {
            when(it.value){
            "а" -> "a"
            "б" -> "b"
            "в" -> "v"
            "г" -> "g"
            "д" -> "d"
            "е" -> "e"
            "ё" -> "e"
            "ж" -> "zh"
            "з" -> "z"
            "и" -> "i"
            "й" -> "i"
            "к" -> "k"
            "л" -> "l"
            "м" -> "m"
            "н" -> "n"
            "о" -> "o"
            "п" -> "p"
            "р" -> "r"
            "с" -> "s"
            "т" -> "t"
            "у" -> "u"
            "ф" -> "f"
            "х" -> "h"
            "ц" -> "c"
            "ч" -> "ch"
            "ш" -> "sh"
            "щ" -> "sh'"
            "ъ" -> ""
            "ы" -> "i"
            "ь" -> ""
            "э" -> "e"
            "ю" -> "yu"
            "я" -> "ya"
            "А" -> "A"
            "Б" -> "B"
            "В" -> "V"
            "Г" -> "G"
            "Д" -> "D"
            "Е" -> "E"
            "Ё" -> "E"
            "Ж" -> "Zh"
            "З" -> "Z"
            "И" -> "I"
            "Й" -> "I"
            "К" -> "K"
            "Л" -> "L"
            "М" -> "M"
            "Н" -> "N"
            "О" -> "O"
            "П" -> "P"
            "Р" -> "R"
            "С" -> "S"
            "Т" -> "T"
            "У" -> "U"
            "Ф" -> "F"
            "Х" -> "H"
            "Ц" -> "C"
            "Ч" -> "Ch"
            "Ш" -> "Sh"
            "Щ" -> "Sh"
            "Ъ" -> ""
            "Ы" -> "I"
            "Ь" -> ""
            "Э" -> "E"
            "Ю" -> "Yu"
            "Я" -> "Ya"
            " " -> divider
             else -> ""
            }
        }
            return result
        }
    fun toInitials(firstName: String?, lastName: String?): String? {
        val initFirst = firstName?.replace(Regex("[^a-zA-Zа-яА-Я]"), "")?.take(1)?.capitalize()
        val initLast = lastName?.replace(Regex("[^a-zA-Zа-яА-Я]"), "")?.take(1)?.capitalize()
        return when(Pair(initFirst.isNullOrEmpty(),initLast.isNullOrEmpty())){
            Pair(first = true, second = true) -> null
            Pair(first = false, second = true) -> initFirst
            Pair(first = true, second = false) -> initLast
            else -> initFirst+initLast

        }
    }
    }

