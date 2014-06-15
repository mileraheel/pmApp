package com.scrum

import com.scrum.auth.User
import grails.converters.JSON
import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*
import static com.scrum.ScrumUtil.EXCLUDE_FIELD_LIST

class CommentController extends RestfulController<Comment>{

    static responseFormats = ['json', 'xml']

    def springSecurityService;

    CommentController() {
        super(Comment)
    }

    @Override
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentInstanceCount: Comment.count()]
    }

    @Transactional
    def save() {
        def instance = new Comment()
        def task = Task.get(request.JSON.task.id)
        instance.task=task
        instance.description = request.JSON.description
        def user = User.findById(1)
        instance.user = user
        instance.save(failOnError: true)
        respond instance, [status: CREATED]
    }

    def list(){
        respond Comment.list(), model:[commentInstanceCount: Comment.count()]
    }

    def show(Comment prj) {
        if(prj){
            respond queryForResource(prj.id), [excludes: EXCLUDE_FIELD_LIST]
            //respond prj
        }

        render status: NOT_FOUND
    }

    def delete(){
        println('delete called ${params}')
        Comment instance = Comment.get(params.id)
        instance.delete(flush: true)
        render status : 200
    }

    def update() {
        println('update method called')
        def instance = new Comment()
        println(request.JSON.id)
        instance.id = request.JSON.id
        def task = Task.get(request.JSON.taskId)
        instance.task=task
        println(instance.id)
        instance.description = request.JSON.description
        def user = User.findByUsername('test@test.com')
        println(instance)
        instance.save(failOnError: true,flush: true)
        respond instance, [status: CREATED]
    }
}
