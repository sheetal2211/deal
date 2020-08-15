
node {
    stage("GITCLONE"){
        checkout scm
    }

     stage("build"){
        script{
            sh "./gradlew clean build"

        }
     }
//     post{
//         always{
//             script{
//                 deleteDir()
//             }
//
//         }
//     }



}