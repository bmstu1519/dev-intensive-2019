package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView() : UserView {
    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = ""//Utils.toIntitials(firstName, lastName)
    val status =if(lastVisit == null) "Ни разу не был"
    else if (isOnline) "online"
    else "Был онлайн ${lastVisit.humanizeDiff()}"
    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickName ,
        initials = initials ,
        status = status
    )
}


