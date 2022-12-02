def call(String webhookUrlCredentialsId, String authTokenCredentialsId, String notificationMessage) {
    assert webhookUrlCredentialsId : 'Providing Zoom webhook URL credentials ID is mandatory'
    assert authTokenCredentialsId : 'Providing Zoom authorization token credentials ID is mandatory'
    assert notificationMessage : 'Providing notification message is mandatory'

    withCredentials([string(credentialsId: authTokenCredentialsId, variable: 'AUTH_TOKEN'), string(credentialsId: webhookUrlCredentialsId, variable: 'WEBHOOK_URL')]) {
        zoomSend(
            authToken: env.AUTH_TOKEN,
            jenkinsProxyUsed: true,
            message: notificationMessage,
            webhookUrl: env.WEBHOOK_URL)
    }
}
