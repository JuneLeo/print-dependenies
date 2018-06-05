package com.meituan.hotel

import com.google.gson.Gson
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph

import java.util.function.Consumer

class PrintPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

//        project.gradle.taskGraph.whenReady(new Action<TaskExecutionGraph>() {
//            @Override
//            void execute(TaskExecutionGraph taskExecutionGraph) {
//                taskExecutionGraph.getAllTasks().forEach(new Consumer<Task>() {
//                    @Override
//                    void accept(Task task) {
//                        DependencieModel dependencieModel = new DependencieModel()
//                        collection(task, dependencieModel)
//                        Gson gson = new Gson()
//                        println(gson.toJson(dependencieModel))
//                    }
//                })
//
//            }
//        })
    }

    def static collection(Task task, DependencieModel model) {
        if (task == null) {
            return
        }
        model.name = task.name
        Set<Task> tasks = task.getTaskDependencies().getDependencies(task)
        if (tasks != null && !tasks.isEmpty()) {
            model.next = new ArrayList<>()
            tasks.forEach(new Consumer<Task>() {
                @Override
                void accept(Task taskChild) {
                    DependencieModel modelChild =new DependencieModel()
                    modelChild.name = taskChild.name
                    model.next.add(modelChild)
                    collection(taskChild,modelChild)
                }
            })
        }
    }
}

