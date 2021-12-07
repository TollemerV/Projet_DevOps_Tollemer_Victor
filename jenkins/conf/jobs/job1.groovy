#!groovy
println('------------------------------------------------------------------Import Job CI/Job1')
def pipelineScript = new File('/var/jenkins_config/jobs/job1-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Job1') {
    description("Job Pipeline 1 : Jenkins")
    parameters {
        stringParam {
            name('PARAM1')
            defaultValue('PARAM1')
            description("PARAM1 Desc")
            trim(false)
        }
        stringParam {
            name('BRANCH')
            defaultValue('master')
            description("Select Branch Master")
            trim(false)
        }
        booleanParam{
            name('SKIP_TESTS')
            defaultValue(true)
            description("SKIP")
        }
        choiceParam('VERSION_TYPE',['SNAPSHOT','RELEASE'], "SNAPSHOT or RELEASE")
        stringParam {
            name('VERSION')
            defaultValue("1.0")
            description("Jar Version")
            trim(false)
        }
        
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}