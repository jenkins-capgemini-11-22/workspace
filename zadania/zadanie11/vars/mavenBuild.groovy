def call(String repositoryUrl) {
    sh './mvnw package'
    junit 'target/surefire-reports/*.xml'
    jacoco()
}
