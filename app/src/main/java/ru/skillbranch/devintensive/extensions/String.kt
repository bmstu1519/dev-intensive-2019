package ru.skillbranch.devintensive.extensions

/**
 * @author iamserj
 * 13.07.2019 0:03
 */


// Implement extension cutting string from 'length' to end and return cutted string with filling "..." (if string was cutted)
// if last char of cutted string is space remove it
fun String.truncate(length:Int = 16):String {
    val trimmedMessage = this.trim()
    return if (trimmedMessage.length <= length) trimmedMessage else trimmedMessage.substring(0, length).trim() + "..."
}

// Implement extension which is:
// 1) removing html tags and html escape sequences ("& < > ' "")
// 2) removing empty spaces between words if there are more than one space.
fun String.stripHtml():String {
    val htmlRegex = Regex("(<.*?>)|(&[^ а-я]{1,4}?;)")
    val spaceRegex = Regex(" {2,}")
    return this.replace(htmlRegex, "").replace(spaceRegex, " ")
}