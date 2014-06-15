package com.scrum

import com.scrum.auth.User


/**
 *
 */
class Comment {
    /* Default (injected) attributes of GORM */
    Long    id
    Long    version

    /* Comment fields */
    String  description
    User    user
    Task task

    /* Automatic timestamping of GORM */
    Date    dateCreated
    Date    lastUpdated

    static constraints = {
        description blank: false
        user blank: false
    }
}
