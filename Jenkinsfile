
node {
    stage("GITCLONE"){
        checkout scm
    }
    post{
        always{
            deleteDir()
        }
    }

}