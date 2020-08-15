
node {
    stage("GITCLONE"){
        checkout scm
    }
    post{
        always{
            script{
                deleteDir()
            }

        }
    }

}