package ru.skillbranch.devintensive.models

import java.util.*

/**
 * @author iamserj
 * 04.07.2019 23:37
 */

// Create abstract class BaseMessage with properties.
abstract class BaseMessage(
    val id:String,
    val from:User?,
    val chat:Chat,
    val isIncoming:Boolean = false,
    val date:Date = Date()
) {
    // Create abstract method formatMessage returning string with information about:
    // message ID
    // name of sender/receiver
    // message direction (receive/send)
    // message type (text/image)
    abstract fun formatMessage():String

    // Implement AbstractFactory pattern with method
    // makeMessage(from, chat, date, type, payload, isIncoming = false) with arguments:
    // message owner User
    // destination Chat
    // message date
    // message type text/image
    // payload
    companion object AbstractFactory {
        var lastId = -1

        //BaseMessage.makeMessage(user, chat, date, "text", "any text message") // Василий отправил сообщение "any text message" только что
        //BaseMessage.makeMessage(user, chat, date, "image", "https://anyurl.com", true) // Василий получил изображение "https://anyurl.com" 2 часа назад
        fun makeMessage(from:User?, chat:Chat, date:Date = Date(), type:String="text", payload:Any?, isIncoming:Boolean = false):BaseMessage {
            lastId++
            return when(type) {
                "image" -> ImageMessage("$lastId", from, chat, date=date, image = payload as String)
                else -> TextMessage("$lastId", from, chat, date=date, text = payload as String)

            }
        }
    }
}