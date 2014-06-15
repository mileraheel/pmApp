package com.scrum

import com.scrum.auth.User
import grails.converters.JSON
import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*
import static com.scrum.ScrumUtil.EXCLUDE_FIELD_LIST

class TaskController extends RestfulController<Task>{

    static responseFormats = ['json', 'xml']

    def springSecurityService;

    TaskController() {
        super(Task)
    }

    @Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Task.list(params), model:[taskInstanceCount: Task.count()]
    }

    @Transactional
    def save() {
        def instance = new Task()
        instance.title = request.JSON.title
        def project = Project.get(request.JSON.project.id)
        instance.project=project
        instance.description = request.JSON.description
        def user = User.findById(1)
        instance.reportedBy=user
        instance.assignedTo=user
        instance.priority=PriorityEnum.LOW
        instance.status=StatusEnum.OPEN
        instance.save(failOnError: true)
        respond instance, [status: CREATED]
    }

    def list(){
        respond Task.list(), model:[taskInstanceCount: Task.count()]
    }

    def show(Task prj) {
        if(prj){
            respond queryForResource(prj.id), [excludes: EXCLUDE_FIELD_LIST]
            //respond prj
        }

        render status: NOT_FOUND
    }

    def delete(){
        println('delete called ${params}')
        Task instance = Task.get(params.id)
        instance.delete(flush: true)
        render status : 200
    }

    def update() {
        println('update method called')
        def instance = new Task()
        println(request.JSON.id)
        instance.id = request.JSON.id
        def project = Project.get(request.JSON.projectId)
        instance.project=project
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
