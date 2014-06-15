package com.scrum

import com.scrum.auth.User
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * This is domain class to hold all projects in database.
 */
@ToString(includeNames = true, includeFields = true, excludes = 'dateCreated,lastUpdated,metaClass')
@EqualsAndHashCode
class Project {

    /* Default (injected) attributes of GORM */
    Long    id
    Long    version

    /* Automatic timestamping of GORM */
    Date    dateCreated
    Date    lastUpdated

    /* Project fields */
    String  name
    String  description
    User    createdBy

    static hasMany = [tasks: Task]

    static constraints = {
        name blank:false
        description blank:false
        createdBy nullable:true
    }
}
