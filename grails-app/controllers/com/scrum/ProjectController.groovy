package com.scrum

import com.scrum.auth.User
import grails.converters.JSON
import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*
import static com.scrum.ScrumUtil.EXCLUDE_FIELD_LIST

class ProjectController extends RestfulController<Project>{

    static responseFormats = ['json', 'xml']

    def springSecurityService;

    ProjectController() {
        super(Project)
    }

    @Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Project.list(params), model:[projectInstanceCount: Project.count()]
    }

    @Transactional
    def save() {
        def instance = new Project()
        instance.name = request.JSON.name
        instance.description = request.JSON.description
        def user = User.findByUsername('test@test.com')
        instance.createdBy=user
        instance.save(failOnError: true)
        respond instance, [status: CREATED]
    }

    def list(){
        respond Project.list(), model:[projectInstanceCount: Project.count()]
    }

    def show(Project prj) {
        if(prj){
            respond queryForResource(prj.id), [excludes: EXCLUDE_FIELD_LIST]
            //respond prj
        }

        render status: NOT_FOUND
    }

    def delete(){
        println('delete called ${params}')
        Project instance = Project.get(params.id)
        instance.delete(flush: true)
        render status : 200
    }

    def update() {
        println('update method called')
        def instance = new Project()
        println(request.JSON.id)
        instance.id = request.JSON.id
        println(instance.id)
        instance.name = request.JSON.name
        instance.description = request.JSON.description
        def user = User.findByUsername('test@test.com')
        instance.createdBy=user
        println(instance)
        instance.save(failOnError: true,flush: true)
        respond instance, [status: CREATED]
    }
}
