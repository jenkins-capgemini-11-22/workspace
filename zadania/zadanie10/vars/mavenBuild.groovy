def call() {
    sh './mvnw package'
    junit 'target/surefire-reports/*.xml'
    jacoco()
}
