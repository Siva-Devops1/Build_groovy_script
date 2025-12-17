def call() {

    stage('Compile') {
        sh 'mvn clean compile'
    }

    stage('Build') {
        sh 'mvn package'
    }

    stage('SonarQube Report') {
        sh 'mvn sonar:sonar'
    }

    stage('Deploy to Nexus') {
        sh 'mvn deploy'
    }

    stage('Deploy to Tomcat') {
        sh """
        curl -u admin:password \
        --upload-file target/maven-web-application.war \
        "http://15.206.148.115:8080/manager/text/deploy?path=/maven-web-application&update=true"
        """
    }
}
