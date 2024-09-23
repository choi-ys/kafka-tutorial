val springCloudVersion = providers.gradleProperty("SPRING_CLOUD_DEPENDENCIES_VERSION").get()

dependencies {
//    compileOnly(project(":core:message"))
    implementation(project(":core:message"))
    implementation(project(":support:web"))
    implementation(project(":support:swagger"))

    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}
