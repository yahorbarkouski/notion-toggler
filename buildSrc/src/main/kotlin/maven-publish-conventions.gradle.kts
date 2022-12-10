plugins {
    `maven-publish`
}

publishing {
    publications.create<MavenPublication>("pluginMaven") {}
    repositories {
        maven {
            url = uri(
                "${System.getenv("CI_API_V4_URL")}/projects/" +
                        "${System.getenv("CI_PROJECT_ID")}/packages/maven"
            )
            System.getenv("CI_JOB_TOKEN")?.let {
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
            } ?: run {
                credentials(HttpHeaderCredentials::class) {
                    name = "Private-Token"
                    value = System.getenv("GITLAB_PRIVATE_TOKEN")
                }
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}
