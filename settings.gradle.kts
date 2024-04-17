enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "template_redux"

include(
    "app",
    "core",
    "navigation",
    "uicomponents",
    "common:common-network",
    "common:common-di",
    "common:common-domain",
    "common:common-test"
)
include(
    "features:home:feature-home-domain",
    "features:home:feature-home"
)
include(
    "features:profile:feature-profile",
    "features:profile:feature-profile-domain"
)

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
