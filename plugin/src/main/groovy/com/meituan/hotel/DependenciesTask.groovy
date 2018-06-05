package com.meituan.hotel

import com.google.gson.Gson
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.apache.http.util.TextUtils

class DependenciesTask extends DefaultTask {

    @Optional
    String task

    String dir = '/depends'

    @TaskAction
    void findTaskDependencies() throws Exception {

        if (TextUtils.isEmpty(task)) {
            println('task is empty')
            return
        }
        Set<Task> tasks = project.getTasksByName(task, true)
        if (tasks == null || tasks.size() == 0) {
            println('task is not exist')
            return
        }

        String desDir = project.rootProject.buildDir.absolutePath + dir
        File dir = new File(desDir)
        deleteFile(dir)
        if (!dir.exists()) {
            dir.mkdir()
        }

        tasks.any { t ->
            DependencieModel dependencieModel = new DependencieModel()
            collection(t, dependencieModel)
            Gson gson = new Gson()
            String json = gson.toJson(dependencieModel)
            println(json)

            String filePath = desDir + '/' + task + "_" + t.project.name + '.json'
            File file = new File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }
            FileOutputStream outputStream
            try {
                outputStream = new FileOutputStream(file)
                outputStream.write(json.bytes)
                outputStream.flush()
            } catch (Exception e) {
                e.printStackTrace()
            } finally {
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        }
    }

    def static deleteFile(File file) {
        if (!file.exists()) {
            return
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles()
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i])
            }
        } else if (file.isFile()) {
            file.delete()
        }
    }

    def static collection(Task task, DependencieModel model) {
        if (task == null) {
            return
        }
        model.task = task.name
        Set<Task> tasks = task.getTaskDependencies().getDependencies(task)
        if (tasks != null && !tasks.isEmpty()) {
            model.dependencies = new ArrayList<>()
            tasks.any { t ->
                DependencieModel modelChild = new DependencieModel()
                modelChild.task = t.name
                model.dependencies.add(modelChild)
                collection(t, modelChild)
            }

        }
    }
}
