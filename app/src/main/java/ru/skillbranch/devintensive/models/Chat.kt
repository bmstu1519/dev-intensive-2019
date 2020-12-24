package ru.skillbranch.devintensive.models

/**
 * @author iamserj
 * 04.07.2019 23:39
 */

class Chat(
    val id:String,
    val members:MutableList<User> = mutableListOf(),
    val messages:MutableList<BaseMessage> = mutableListOf()
) {
}