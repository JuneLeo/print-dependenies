package com.meituan.hotel

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PrintTask extends DefaultTask{

    @TaskAction
    def println() throws Exception{
       println('custom task')
    }
}
