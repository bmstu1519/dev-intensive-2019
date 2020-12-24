package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

/**
 * @author iamserj
 * 04.07.2019 23:41
 */

class TextMessage(
    id:String,
    from:User?,
    chat:Chat,
    isIncoming:Boolean = false,
    date:Date = Date(),
    var text:String?
):BaseMessage (id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        val formatted:String =
            "id:$id ${from?.firstName}" +
            " ${if(isIncoming) "получил" else "отправил"}" +
            " сообщение \"$text\" ${date.humanizeDiff()}"
        return formatted
    }
}

