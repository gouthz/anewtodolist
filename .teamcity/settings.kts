import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs

version = "2022.04"

project {

    buildType(Build)
    buildType(Package)

    sequential{
        buildType(Build)
        buildType(Package)
    }
}

object Build : BuildType({
    id("builds")
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean compile"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

})


object Package : BuildType({
    name = "Package"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean package"
            runnerArgs = "-Dmaven.test.failure.ignore=true -DskipTests"
        }
    }

    triggers {
        vcs{

        }
    }
})
