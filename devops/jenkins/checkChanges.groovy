def getChangedDirectories() {
    def changedDirs = []
    def gitOutput = sh(script: "git diff --name-only HEAD~1..HEAD", returnStdout: true).trim()

    def changedFiles = gitOutput.split('\n')

    changedFiles.each { file ->
        def dir = file.split('/')[0]
        if (dir && !changedDirs.contains(dir) && dir!='devops') {
            changedDirs.add(dir)
        }
    }
    return changedDirs
}

def getDirectoriesWithGradlew(changedDirs) {
    def dirsToBuild = []
    changedDirs.each { dir ->
        if (fileExists("${dir}/gradlew")) {
            dirsToBuild.add(dir)
        }
    }
    return dirsToBuild
}


def getDirectoriesWithDocker(changedDirs) {
    def dirsToBuild = []
    changedDirs.each { dir ->
        if (fileExists("${dir}/deploy.yaml")) {
            dirsToBuild.add(dir)
        }
    }
    return dirsToBuild
}

def getDirectoriesWithNPM(changedDirs) {
    def dirsToBuild = []
    changedDirs.each { dir ->
        if (fileExists("${dir}/package.json")) {
            dirsToBuild.add(dir)
        }
    }
    return dirsToBuild
}

return this
