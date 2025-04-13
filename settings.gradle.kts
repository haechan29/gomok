pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Gomok"
include(":app")
include(":feature")
include(":feature:game")
include(":core")
include(":core:data")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:common")
include(":core:model")
include(":core:database")
