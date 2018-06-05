package com.meituan.hotel

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.apache.http.util.TextUtils

class PrintTask extends DefaultTask {

    private String leo

    @TaskAction
    def println() throws Exception {
        println('custom task')
        if (!TextUtils.isEmpty(leo)){
            println('custom task')
        }
    }

    String getLeo() {
        return leo
    }

    void setLeo(String leo) {
        this.leo = leo
    }
}
