package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * @author iamserj
 * 01.07.2019 23:01
 */

fun User.toUserView() : UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if (lastVisit == null) "Ещё ни разу не был" else if (isOnline) "online" else "Последний раз был ${lastVisit.humanizeDiff()}"


    return UserView(
        id = id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        avatar = avatar,
        status = status,
        initials = initials
    )
    // same as
    // return UserView(id, "$firstName $lastName", nickName, avatar, status, initials)
}
