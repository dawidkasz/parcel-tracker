def getChangedDirectories() {
    def changedDirs = []
    def gitOutput = sh(script: "git diff --name-only HEAD~1..HEAD", returnStdout: true).trim()

    def changedFiles = gitOutput.split('\n')

    changedFiles.each { file ->
        def dir = file.split('/')[0]
        if (dir && !changedDirs.contains(dir)) {
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

return this
