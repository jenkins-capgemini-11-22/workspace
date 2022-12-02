def call(Map<String, String> pipelineConfiguration) {
    assert pipelineConfiguration.zoomWebhookUrlCredentialsId : 'Providing Zoom webhook URL credentials ID is mandatory'
    assert pipelineConfiguration.zoomTokenCredentialsId : 'Providing Zoom authorization token credentials ID is mandatory'

    pipeline {
        agent any
        triggers {
            // pipeline musi zostać uruchomiony przynajmniej raz aby trigger został aktywowany
            pollSCM pipelineConfiguration.scmPollingInterval?:'@daily'
        }
        options {
            timestamps()
        }
        stages {
            stage('zbuduj') {
                steps {
                    mavenBuild()
                }
            }
            stage('przetestuj') {
                steps {
                    echo "$WORKSPACE"
                }
            }
            stage('przeskanuj zależności projektu') {
                steps {
                    sh './mvnw dependency-check:aggregate'
                }
            }
            stage('wdróż') {
                steps {
                    echo 'wdrażam'
                    sendZoomNotification(
                        pipelineConfiguration.zoomWebhookUrlCredentialsId,
                        pipelineConfiguration.zoomTokenCredentialsId,
                        pipelineConfiguration.notificationMessage?:"${currentBuild.projectName} build successful")
                }
            }
        }
    }
}
