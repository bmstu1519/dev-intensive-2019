package ru.skillbranch.devintensive.utils

/**
 * @author iamserj
 * 29.06.2019 1:39
 */

object Utils {
    // Implement method Utils.parseFullName(fullName) with argument containing full user name (null, empty string)
    // and returning a pair of values Pair(firstName, lastName)
    // If full name or a part of it is not parsable, return null null or "firstName" null
    fun parseFullName(fullName:String?):Pair<String?, String?> {
        val parts = fullName?.trim()?.split(" ")
        val firstName = parts?.getOrNull(0)?.ifEmpty { null }
        val lastName = parts?.getOrNull(1)?.ifEmpty { null }
        // return Pair(firstName, lastName)
        // same as:
        return firstName to lastName
    }


    // Implement method Utils.toInitials(firstName lastName) returning first letters in upper case.
    // If one of them is null return the second one, if both null return null
    fun toInitials(firstName: String?, lastName: String?): String? {
        // orEmpty() Returns the string if it is not null, or the empty string otherwise.
        // getOrNull(index) Returns a character at the given index or null if the index is out of bounds of this char sequence.
        val firstChar = firstName.orEmpty().trim().getOrNull(0)?.toUpperCase()
        val lastChar = lastName.orEmpty().trim().getOrNull(0)?.toUpperCase()

        // If the expression to the left of Elvis (?:) is not null, the Elvis operator returns it, otherwise it returns the expression to the right.
        val firstI = firstChar?.toString() ?: ""
        val lastI = lastChar?.toString() ?: ""

        // ifEmpty { expression } returns string if it's not empty or the result of calling expression in curly braces.
        return "$firstI$lastI".ifEmpty { null }
    }



    // Implement Utils.transliteration(payload divider) returning latin symbols string
    fun transliteration(payload: String, divider: String = " "): String {
        val map = fillTranslitMap()
        val builder = StringBuilder()

        for (char in payload.trim())
            builder.append(getTranslChar(char, map))

        return builder.toString().replace(" ", divider)
    }

    private fun getTranslChar(char: Char, map: HashMap<Char, String>): String {
        val transl  = map[char.toLowerCase()] ?: char.toString()
        // capitalize() Returns string copy with uppercased first letter, or the original string, if it's empty or already starts with an upper case.
        return if (char.isUpperCase() && transl.isNotEmpty()) transl.capitalize() else transl
    }

    private fun fillTranslitMap():HashMap<Char,String> {
        val map = hashMapOf<Char,String>()
        map['а'] = "a"
        map['б'] = "b"
        map['в'] = "v"
        map['г'] = "g"
        map['д'] = "d"
        map['е'] = "e"
        map['ё'] = "e"
        map['ж'] = "zh"
        map['з'] = "z"
        map['и'] = "i"
        map['й'] = "i"
        map['к'] = "k"
        map['л'] = "l"
        map['м'] = "m"
        map['н'] = "n"
        map['о'] = "o"
        map['п'] = "p"
        map['р'] = "r"
        map['с'] = "s"
        map['т'] = "t"
        map['у'] = "u"
        map['ф'] = "f"
        map['х'] = "h"
        map['ц'] = "c"
        map['ч'] = "ch"
        map['ш'] = "sh"
        map['щ'] = "sh'"
        map['ъ'] = ""
        map['ы'] = "i"
        map['ь'] = ""
        map['э'] = "e"
        map['ю'] = "yu"
        map['я'] = "ya"
        return map
    }
}