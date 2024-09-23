import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<Jar> { enabled = true }

tasks.named<BootJar>("bootJar") {
    enabled = false
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}
