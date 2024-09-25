import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<Jar> { enabled = true }
tasks.named<BootJar>("bootJar") { enabled = false }

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
}
