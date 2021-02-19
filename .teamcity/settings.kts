import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v10.toExtId
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

version = "2018.2"

project {
    vcsRoot(TeamCityTestVcs)
    //id() ?

    sequence {
        build(Step1)
        build(Step2)
        build(Step3)
    }
}

object Step1 : BuildType({
    name = "Step 1"

    vcs {
        root(TeamCityTestVcs)
    }

    steps {
        script {
            name = "Print As"
            scriptContent = "echo \"AAA\""
        }
    }
})

object Step2 : BuildType({
    name = "Step 2"

    steps {
        script {
            name = "Print Bs"
            scriptContent = "echo \"BBB\""
        }
    }
})

object Step3 : BuildType({
    name = "Step 3"

    steps {
        script {
            name = "Print Cs"
            scriptContent = "echo \"CCC\""
        }
    }
})

object TeamCityTestVcs : GitVcsRoot({
    name = "TeamCityTestVcs"
    url = "git@github.com:ot-andreas/teamcity-test.git"
    branch = "refs/heads/main"

    authMethod = uploadedKey {
        uploadedKey = "guestcenter-tc"
    }
})


fun wrapWithFeature(buildType: BuildType, featureBlock: BuildFeatures.() -> Unit): BuildType {
    buildType.features {
        featureBlock()
    }
    return buildType
}